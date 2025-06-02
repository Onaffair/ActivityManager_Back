package com.example.onaffair.online_chat.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.BasicCredentials;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import java.util.UUID;

public class STSUtil {
    public static AssumeRoleResponse.Credentials getStsToken(
            String accessKeyID,
            String accessKeySecret,
            String roleArn
    ) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou");
        BasicCredentials basicCredentials = new BasicCredentials(accessKeyID, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile, basicCredentials);

        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysRegionId("cn-hangzhou");

        request.setRoleArn(roleArn);
        request.setRoleSessionName("session-" + UUID.randomUUID().toString());
        request.setDurationSeconds(900L);

        String policy = "{\n" +
                "  \"Version\": \"1\", \n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\", \n" +
                "      \"Action\": [\n" +
                "        \"oss:PutObject\",\n" +
                "        \"oss:GetObject\",\n" +
                "        \"oss:DeleteObject\"\n" +
                "      ], \n" +
                "      \"Resource\": [\n" +
                "        \"acs:oss:*:*:onaffair/images/*\",\n" +
                "        \"acs:oss:*:*:onaffair\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Action\": [\"oss:ListBuckets\"],\n" +
                "      \"Resource\": [\"acs:oss:*:*:*\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        request.setPolicy(policy);

        AssumeRoleResponse response = client.getAcsResponse(request);

        return response.getCredentials();
    }


}
