package com.example.onaffair.online_chat.dto;


import com.example.onaffair.online_chat.entity.AIChatLog;
import com.example.onaffair.online_chat.entity.Activity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIChatRequest {

    private String sessionId;

    private String parentId;
    private Integer activityId;
    @NotBlank(message = "内容不能为空")
    private String content;
    @Size(max = 5,message = "图片数量不能超过5张")
    private List<AIChatLog.ImageInfo> imageInfo;

}
