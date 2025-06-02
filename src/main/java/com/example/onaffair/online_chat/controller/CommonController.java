package com.example.onaffair.online_chat.controller;


import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.util.Result;
import com.example.onaffair.online_chat.util.STSUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api")
public class CommonController {

    @Value("${oss.access-id}")
    private String accessID;

    @Value("${oss.access-key}")
    private String accessKey;

    @Value("${oss.role-arn}")
    private String roleArn;

    @Value("${oss.region}")
    private String region;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.address}")
    private String endpoint;

    private final String IMAGE_DIR = "/images";

    private final Integer EXPIRED_TIME = 10 * 60 * 1000;

    private final String ALLOWED_EXTENSIONS = "jpg,jpeg,png,gif,bmp";

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/oss/get-sts")
    public Result<Map<String,Object>> uploadImage() {
        Map<String,Object> resultMap = new HashMap<>();
        try {

            AssumeRoleResponse.Credentials credentials = STSUtil.getStsToken(accessID, accessKey, roleArn);

            resultMap.put("stsToken", credentials.getSecurityToken());
            resultMap.put("accessKeyId", credentials.getAccessKeyId());
            resultMap.put("accessKeySecret", credentials.getAccessKeySecret());
            resultMap.put("expiration", credentials.getExpiration());
            resultMap.put("bucket", bucket);
            resultMap.put("region", region);

            return Result.success(resultMap);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,e.getMessage());
        }
    }

    @GetMapping("/oss/get-sign")
    public Result<Map<String,Object>> getSign(String fileName){

        try {
            if (!isValidFileName(fileName)){
                return Result.error(ResultCode.ERROR,"文件名不合法");
            }

            String objectName = IMAGE_DIR + "/" + fileName;
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessID, accessKey);

            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, objectName);
            request.setMethod(HttpMethod.PUT);
            request.setExpiration(new Date(System.currentTimeMillis()+EXPIRED_TIME));
            request.addHeader("x-oss-object-acl", "public-read");
            URL signUrl = ossClient.generatePresignedUrl(request);

            String accessUrl = "https://" + bucket + "." + endpoint.split("//")[1] + "/" + objectName;
            Map<String,Object> res = new HashMap<>();
            res.put("signUrl",signUrl);
            res.put("accessUrl",accessUrl);
            res.put("fileName",fileName);

            return Result.success(res);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR,e.getMessage());
        }
    }
    private boolean isValidFileName(String fileName) {
        // 检查路径遍历
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            return false;
        }
        // 检查文件扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList(ALLOWED_EXTENSIONS.split(",")).contains(ext);
    }
}
