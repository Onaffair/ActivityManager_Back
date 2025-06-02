package com.example.onaffair.online_chat.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ai_chat_log")
public class AIChatLog {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("session_id")
    private String sessionId;

    @TableField("parent_id")
    private String parentId;

    @TableField("role")
    private String role;

    @TableField("content")
    private String content;

    @TableField("image_info")
    private List<ImageInfo> imageInfo;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageInfo{
        private String url;
        private String description;
    }

}
