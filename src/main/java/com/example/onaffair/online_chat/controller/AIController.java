package com.example.onaffair.online_chat.controller;


import com.example.onaffair.online_chat.dto.AIChatRequest;
import com.example.onaffair.online_chat.entity.AIChatLog;
import com.example.onaffair.online_chat.entity.AIChatSession;
import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.property.AIProperty;
import com.example.onaffair.online_chat.service.AIChatLogService;
import com.example.onaffair.online_chat.service.AIChatSessionService;
import com.example.onaffair.online_chat.service.ActivityService;
import com.example.onaffair.online_chat.service.UserService;
import com.example.onaffair.online_chat.util.WebUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executor;

@Slf4j
@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Qualifier("deepseek")
    @Autowired
    private AIProperty dsProperty;

    @Qualifier("VL")
    @Autowired
    private AIProperty VLProperty;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private AIChatSessionService aiChatSessionService;
    @Autowired
    private AIChatLogService aiChatLogService;


    private final String IMG_PROMPT = "以下是图片的url以及图片内容的描述，你需要通过描述，将图片添加到输出的合理位置";

    @Qualifier("myExecutor")
    @Autowired
    private Executor taskExecutor;

    @PostMapping(value = "/chat",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@Validated @RequestBody AIChatRequest request){


        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Activity activity = activityService.getActivityById(request.getActivityId());
        String content = request.getContent();
        List<AIChatLog.ImageInfo> imageInfo = request.getImageInfo();


        /*AIChatSession session = null;

        if (request.getSessionId() == null){
            session = new AIChatSession(){{
                setActivityId(request.getActivityId());
                setTitle("活动"+activity.getTitle()+"的会话");
                setUserAccount(account);
            }};

            aiChatSessionService.insert(session);
        }else{
            session = aiChatSessionService.getAIChatSessionById(request.getSessionId());
        }

        String sessionId = session.getId();
        AIChatLog userLog = new AIChatLog(){{
            setSessionId(sessionId);
            setContent(text);
            setImageInfo(imageInfo);
            setRole("user");
            setParentId(null);
        }};*/


        SseEmitter emitter = new SseEmitter(5*60*1000L);
        emitter.onTimeout(() ->{
            System.out.println("请求超时");
        });
        emitter.onError((e) ->{
            emitter.completeWithError(e);
        });

        //异步执行
        taskExecutor.execute(() ->{
            try {
                //构建DS请求体
                Map<String,Object> body = new HashMap<>();
                body.put("model",dsProperty.getModel()); //deepseek-reasoner | deepseek-chat
                body.put("stream",true);
                //消息
                List<Map<String,Object>> messages = new ArrayList<>();


                // 图片信息
                List<String> b64Images = new ArrayList<>();
                //用户上传的图片
                imageInfo.forEach(img ->{
                    b64Images.add(img.toString());
                });
                if (b64Images.size() > 0){
                    messages.add(Map.of("role","user","content",IMG_PROMPT+b64Images.toString()));
                }

                //活动信息
                StringBuilder activityInfo = new StringBuilder();
                activityInfo.append(
                        String.format(
                                "以下是活动信息：" +
                                "标题: %s" +
                                "时间: %s" +
                                "地点: %s" +
                                "简介: %s" +
                                "亮点: %s",
                                activity.getTitle(),
                                activity.getBeginTime(),
                                activity.getAddress(),
                                activity.getContent(),
                                activity.getHighlight()
                        ));
                messages.add(Map.of("role", "system", "content", activityInfo.toString()));

                // 提示词与角色
                messages.add(Map.of("role","user","content",content));

                body.put("messages",messages);

                //请求构建与发送
                String jsonBody = objectMapper.writeValueAsString(body);
                HttpClient httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(dsProperty.getUrl()))
                        .header("Content-Type", "application/json;charset=utf-8")
                        .header("Authorization", "Bearer " + dsProperty.getKey())
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                // 获取流式响应
                HttpResponse<InputStream> response = httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofInputStream()
                );

                //发送信息
                sendMessage(emitter,response);
                emitter.complete();
            }catch (Exception ex){
                emitter.completeWithError(ex);
            }finally {
            }
        });

        return emitter;
    }

    //图片分析
    @GetMapping(value = "/image-analise",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter imageAnalise(@RequestParam("imageUrl") String imageUrl){
        SseEmitter emitter = new SseEmitter(5*60*1000L);

        emitter.onTimeout(() ->{
            System.out.println("请求超时");
        });
        emitter.onError((e) ->{
            emitter.completeWithError(e);
        });

        taskExecutor.execute(() ->{
            try {
                //构建请求体
                Map<String,Object> body = buildQwenRequestBody(imageUrl);
                String jsonBody = objectMapper.writeValueAsString(body);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(VLProperty.getUrl()))
                        .header("Content-Type", "application/json;charset=utf-8")
                        .header("Authorization", "Bearer " + VLProperty.getKey())
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();
                HttpResponse<InputStream> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofInputStream()
                );
                System.out.println("本次请求状态码"+response.statusCode());
                sendMessage(emitter,response);
                emitter.complete();

            }catch (Exception e){
                emitter.completeWithError(e);
            }finally {
            }
        });
        return emitter;
    }
    private void sendMessage( SseEmitter emitter,HttpResponse<InputStream> response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), StandardCharsets.UTF_8));
        String line;
        String msg = "";
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) continue;
            if (line.startsWith("data: ")) {

                String json = line.substring(6);

                //请求结束 ，退出任务
                if ("[DONE]".equals(json)){
                    emitter.send(Map.of("content","","done",true));
                    break;
                }
                // 解析 content 内容
                JsonNode node = new ObjectMapper().readTree(json);
                JsonNode contentNode = node.at("/choices/0/delta/content");
                if (!contentNode.isMissingNode()) {
                    if (contentNode.asText().isEmpty()) continue;
                    msg+= contentNode.asText();
                    emitter.send(Map.of("content",contentNode.asText(),"done",false));
                }
            }else{
                System.out.println(line);
            }
        }
        System.out.println(msg);
    }
    private Map<String,Object> buildQwenRequestBody(String imageUrl) throws IOException {
        Map<String,Object> body = new HashMap<>();
        body.put("model", VLProperty.getModel());
        body.put("stream",true);

        List<Object> messages = new ArrayList<>();
        Map<String,Object> message = new HashMap<>();
        List<Object> content = new ArrayList<>();

        content.add(Map.of( "type","image_url","image_url",Map.of("url",imageUrl)));
        content.add(Map.of("type","text","text","请你分析链接中的图片内容,给出中文描述"));

        message.put("role","user");
        message.put("content",content);

        messages.add(message);
        body.put("messages",messages);

        System.out.println(body.toString());

        return body;
    }
}
