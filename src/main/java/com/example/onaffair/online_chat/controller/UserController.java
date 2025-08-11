package com.example.onaffair.online_chat.controller;


import com.example.onaffair.online_chat.dto.*;
import com.example.onaffair.online_chat.entity.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.onaffair.online_chat.service.*;
import com.example.onaffair.online_chat.util.ChannelManager;
import com.example.onaffair.online_chat.util.JwtUtil;
import com.example.onaffair.online_chat.util.Result;
import com.example.onaffair.online_chat.enums.ResultCode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final static String UPLOAD_IMAGE_PATH = "D:\\Code\\Database\\images\\";

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendMessageService friendMessageService;

    @Autowired
    private FollowService followService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AIChatSessionService aiChatSessionService;

    @Autowired
    private AIChatLogService aiChatLogService;
    
    @Autowired
    private AnnouncementService announcementService;


    @PostMapping("/public/login")
    public Result<UserLoginRegisterResponse> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        try {
            User target = userService.findByAccountAndPassword(userLoginRequest);

            if (target != null) {

                if (target.getStatus().equals("banned")){
                    return Result.error(ResultCode.ERROR, "用户已被封禁");
                }

                String token = JwtUtil.generateToken(
                        target.getAccount(),
                        target.getRole()
                );
                UserLoginRegisterResponse res = new UserLoginRegisterResponse() {{
                    setUsername(target.getUsername());
                    setToken(token);
                    setAvatar(target.getAvatar());
                    setStatus(target.getStatus());
                    setAccount(target.getAccount());
                    setEmail(target.getEmail());
                    setPhone(target.getPhone());
                    setStatus("online");
                    setRole(target.getRole());
                }};

                //更新登录状态
                target.setStatus("online");
                userService.updateUser(userLoginRequest.getAccount(), target);

                return Result.success(ResultCode.SUCCESS, res);
            }
            return Result.error(ResultCode.ERROR, "用户名或密码错误");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/public/wxlogin")
    public Result<UserLoginRegisterResponse> userWxLogin(@RequestParam("code") @NotBlank String code,
                                                         @RequestBody WXLoginDTO userinfo) {
        try {
            User user = userService.userWxLogin(code, userinfo);
            if (user != null) {
                String token = JwtUtil.generateToken(
                        user.getAccount(),
                        user.getRole()
                );
                UserLoginRegisterResponse res = new UserLoginRegisterResponse() {{
                    setUsername(user.getUsername());
                    setToken(token);
                    setAvatar(user.getAvatar());
                    setAccount(user.getAccount());
                    setEmail(user.getEmail());
                    setPhone(user.getPhone());
                    setRole(user.getRole());
                    setStatus("online");
                }};

                return Result.success(ResultCode.SUCCESS, res);
            }
        }catch (RuntimeException e){
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
        return Result.error(ResultCode.ERROR, "服务器错误");
    }

    @PostMapping("/public/register")
    public Result<UserLoginRegisterResponse> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            User user = userService.findByAccount(userRegisterRequest.getAccount());

            if (user == null) {
                User targetUser = new User() {{
                    setAccount(userRegisterRequest.getAccount());
                    setUsername(userRegisterRequest.getUsername());
                    setPassword(userRegisterRequest.getPassword());
                    setEmail(userRegisterRequest.getEmail());
                    setPhone(userRegisterRequest.getPhone());
                    setAvatar(userRegisterRequest.getAvatar());
                    setRole(0);
                    setStatus("online");
                }};
                userService.insertUser(targetUser);

                String token = JwtUtil.generateToken(
                        targetUser.getAccount(),
                        targetUser.getRole()
                );

                UserLoginRegisterResponse res = new UserLoginRegisterResponse() {{
                    setUsername(userRegisterRequest.getUsername());
                    setToken(token);
                    setAvatar(userRegisterRequest.getAvatar());
                    setAccount(userRegisterRequest.getAccount());
                    setEmail(userRegisterRequest.getEmail());
                    setPhone(userRegisterRequest.getPhone());
                    setRole(targetUser.getRole());
                    setStatus("online");
                }};
                return Result.success(ResultCode.SUCCESS, res);
            } else {
                return Result.error(ResultCode.ERROR, "用户已存在");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/public/avatar")
    public Result<String> userAvatar(@RequestBody MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                System.out.println("文件为空");
                return Result.error(ResultCode.ERROR, "文件为空");
            }

            String originalFilename = file.getOriginalFilename();

            // 允许所有图片格式，这里假设只检查常见的图片格式
            String[] allowedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};
            boolean isAllowed = false;
            for (String ext : allowedExtensions) {
                if (originalFilename.toLowerCase().endsWith(ext)) {
                    isAllowed = true;
                    break;
                }
            }
            if (!isAllowed) {
                return Result.error(ResultCode.ERROR, "文件格式错误");
            }

            String filePrefix = UUID.randomUUID().toString();
            String fileNameSuffix = "." + originalFilename.split("\\.")[1];
            String fileName = filePrefix + fileNameSuffix;

            String basePath = UPLOAD_IMAGE_PATH;
            String path = basePath + fileName;
            try {
                File dir = new File(basePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.transferTo(new File(path));

                String serverUrl = "http://localhost:721/static/avatar/" + fileName;
                System.out.println(serverUrl);

                return Result.success(ResultCode.SUCCESS, fileName);
            } catch (IOException e) {
                return Result.error(ResultCode.ERROR, "上传失败");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/quit")
    public Result<String> userQuit() {

        SecurityContext context = SecurityContextHolder.getContext();

        String account = context.getAuthentication().getName();

        User offlineUser = new User();
        offlineUser.setStatus("offline");
        userService.updateUser(account, offlineUser);
        return Result.success(ResultCode.SUCCESS, "退出成功");
    }


    @GetMapping("/reFlashToken")
    public Result<String> reFlashToken() {
        SecurityContext context = SecurityContextHolder.getContext();

        String account = context.getAuthentication().getName();

        User user = userService.findByAccount(account);

        if (user.getStatus().equals("banned")){
            return Result.error(ResultCode.ERROR, "您的账号已被封禁");
        }

        String token = JwtUtil.generateToken(
                user.getAccount(),
                user.getRole()
        );
        return Result.success(ResultCode.SUCCESS, token);
    }

    @GetMapping("/search")
    public Result<UserSearchResponse> userSearch(@RequestParam("keyword") String keyword) {

        try {
            if (Objects.equals(keyword, "")) {
                return Result.success(null);
            }
            UserSearchResponse res = new UserSearchResponse();
            res.setUserList(userService.userSearch(keyword));

            if (res.getUserList().isEmpty()) {
                return Result.success(null);
            }
            return Result.success(ResultCode.SUCCESS, res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @DeleteMapping("/image/{fileName}")
    public Result<String> deleteAvatar(@PathVariable String fileName) {
        try {
            // 防止路径遍历攻击
            if (fileName.contains("..") || fileName.contains("/")) {
                return Result.error(ResultCode.ERROR, "非法文件名");
            }

            File targetFile = new File(UPLOAD_IMAGE_PATH + fileName);

            if (!targetFile.exists()) {
                return Result.error(ResultCode.ERROR, "文件不存在");
            }

            if (targetFile.delete()) {
                return Result.success(ResultCode.SUCCESS, "删除成功");
            } else {
                return Result.error(ResultCode.ERROR, "删除失败");
            }
        } catch (SecurityException e) {
            return Result.error(ResultCode.ERROR, "无删除权限");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/editInfo")
    public Result<UserLoginRegisterResponse> editInfo(@RequestBody UserEditInfoRequest userEditInfoRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        String account = context.getAuthentication().getName();
        try {
            User target = userService.findByAccount(account);
            target.setPhone(userEditInfoRequest.getPhone());
            target.setUsername(userEditInfoRequest.getUsername());
            target.setEmail(userEditInfoRequest.getEmail());
            target.setAvatar(userEditInfoRequest.getAvatar());


            if (userService.updateUser(account, target)) {
                UserLoginRegisterResponse res = new UserLoginRegisterResponse() {{
                    setUsername(target.getUsername());
                    setAvatar(target.getAvatar());
                    setStatus(target.getStatus());
                    setAccount(target.getAccount());
                    setEmail(target.getEmail());
                    setPhone(target.getPhone());
                }};
                return Result.success(ResultCode.SUCCESS, res);
            } else {
                return Result.error(ResultCode.ERROR, "修改失败");
            }
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/friend/get")
    public Result<List<FriendListResponse>> getFriendList() {
        SecurityContext context = SecurityContextHolder.getContext();
        String account = context.getAuthentication().getName();
        try {
            List<FriendRelationship> friendList = friendService.getFriendList(account);

            List<FriendListResponse> res = new ArrayList<>();

            friendList.forEach(item -> {
                String friendAccount = item.getUserAccount1().equals(account) ? item.getUserAccount2() : item.getUserAccount1();
                User user = userService.findByAccount(friendAccount);
                FriendListResponse friendListResponse = new FriendListResponse() {{
                    setAccount(user.getAccount());
                    setAvatar(user.getAvatar());
                    setEmail(user.getEmail());
                    setPhone(user.getPhone());
                    setUserName(user.getUsername());
                }};

                res.add(friendListResponse);
            });

            res.forEach(item -> {
                List<FriendMessage> messageFriendID = friendMessageService.getMessageFriendID(item.getAccount(), account);
                item.setChat(messageFriendID);
            });

            return Result.success(ResultCode.SUCCESS, res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/friend/add")
    public Result<String> userAddFriend(@RequestBody String friendAccount) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();

            FriendRequest request = new FriendRequest() {{
                setSender(account);
                setReceiver(friendAccount);
            }};
            if (friendRequestService.addFriendRequest(request)) {
                FriendRequest friendRequest = friendRequestService.getFriendRequest(request.getSender(), request.getReceiver());
                Set<Channel> receiver = ChannelManager.getChannelsByAccount(friendAccount);
                if (receiver != null && !receiver.isEmpty()) {
                    //服务器转发请求

                    for (Channel channel : receiver) {
                        channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(friendRequest)));
                    }
                }
                return Result.success(ResultCode.SUCCESS, "发送成功");
            }
            return Result.error(ResultCode.FORBIDDEN, "发送失败");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/friend/getRequest")
    public Result<List<FriendRequest>> getFriendRequest() {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            List<FriendRequest> friendRequestList = friendRequestService.getFriendRequestByReceiver(account);
            return Result.success(ResultCode.SUCCESS, friendRequestList);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/friend/request-accept")
    public Result<String> userRequestAccept(@RequestBody Integer requestId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();

            FriendRequest friendRequest = friendRequestService.getFriendRequestById(requestId);
            friendRequest.setStatus(1);

            String friendAccount = friendRequest.getSender();
            FriendRelationship relationship = new FriendRelationship() {{
                setUserAccount1(account);
                setUserAccount2(friendAccount);
            }};

            if (friendService.addFriend(relationship)) {
                //更新好好友请求状态
                friendRequestService.updateFriendRequest(friendRequest);
                return Result.success(ResultCode.SUCCESS, "添加成功");
            }
            return Result.error(ResultCode.ERROR, "添加失败");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/friend/request-refuse")
    public Result<String> userRequestRefuse(@RequestBody Integer requestId) {
        try {
            FriendRequest friendRequest = friendRequestService.getFriendRequestById(requestId);
            friendRequest.setStatus(2);
            if (friendRequestService.updateFriendRequest(friendRequest)) {
                return Result.success(ResultCode.SUCCESS, "拒绝成功");
            }
            return Result.error(ResultCode.ERROR, "拒绝失败");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/follow")
    public Result<String> userFollow(@RequestBody String followingId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            Follow follow = new Follow() {{
                setFollowerId(account);
                setFollowingId(followingId);
            }};
            if (followService.follow(follow)) {
                return Result.success(ResultCode.SUCCESS, "关注成功");
            }
            return Result.error(ResultCode.BAD_REQUEST, "关注失败");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @PostMapping("/unfollow")
    public Result<String> userUnfollow(@RequestBody String followingId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            Follow follow = new Follow() {{
                setFollowerId(account);
                setFollowingId(followingId);
            }};
            followService.unfollow(follow);
            return Result.success(ResultCode.SUCCESS, "取消关注成功");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/followList")
    public Result<FollowResponse> getFollowList(@RequestParam(value = "userId", defaultValue = "null") String userId) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            String account = context.getAuthentication().getName();
            if (!userId.equals("null")) {
                account = userId;
            }
            List<String> fans = followService.getFollowList(account);
            List<String> following = followService.getFollowingList(account);
            FollowResponse res = new FollowResponse();

            List<UserInfoResponse> fansInfo = userService.getUserInfo(fans);
            List<UserInfoResponse> followingInfo = userService.getUserInfo(following);
            res.setFollower(fansInfo);
            res.setFollowing(followingInfo);

            return Result.success(ResultCode.SUCCESS, res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/info")
    public Result<UserInfoResponse> getUserInfo(@RequestParam(value = "userId", defaultValue = "null") String userId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!userId.equals("null")) {
                account = userId;
            }
            UserInfoResponse res = userService.getUserInfo(List.of(account)).get(0);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/friend/requester-info")
    public Result<List<UserInfoResponse>> getRequesterInfo(@RequestParam(value = "userList", defaultValue = "null") List<String> userList) {
        try {
            List<UserInfoResponse> res = userService.getUserInfo(userList);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器错误");
        }
    }

    @GetMapping("/my-session")
    public Result<List<AIChatSession> > getAISessionHistory() {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            List<AIChatSession> res = aiChatSessionService.getAIChatSessionListByUserAccount(account);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,e.getMessage());
        }
    }

    @GetMapping("/session-chat-history")
    public Result<List<AIChatLog>> getSessionChatHistory(@RequestParam("sessionId") String sessionId) {
        try {
            List<AIChatLog> res = aiChatLogService.getAIChatLogListBySessionId(sessionId);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,e.getMessage());
        }
    }
    
    // ==================== 公告相关接口 ====================
    
    /**
     * 用户分页获取公告列表
     * @param current 当前页
     * @param size 每页大小
     * @return 公告列表
     */
    @GetMapping("/announcements")
    public Result<IPage<Announcement>> getAnnouncements(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Page<Announcement> page = new Page<>(current, size);
            IPage<Announcement> announcements = announcementService.getAnnouncementsForUser(page);
            return Result.success(announcements);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
    
    /**
     * 用户获取公告详情
     * @param announcementId 公告ID
     * @return 公告详情
     */
    @GetMapping("/announcements/{announcementId}")
    public Result<Announcement> getAnnouncementDetail(@PathVariable String announcementId) {
        try {
            Announcement announcement = announcementService.getAnnouncementById(announcementId);
            if (announcement == null) {
                return Result.error(ResultCode.ERROR, "公告不存在");
            }
            return Result.success(announcement);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }
}
