package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("activity_comment")
public class ActivityComment {
    // 主键ID（自增）
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    // 活动ID（外键关联活动表）
    @TableField("activity_id")
    private Integer activityId;

    // 用户ID（外键关联用户表）
    @TableField("user_id")
    private String userId;

    // 评论内容
    @TableField("text_content")
    private String textContent;

    // 图片链接（单张）
    @TableField("image_url")
    private String imageUrl;

    // 评分（0.0-5.0）
    @TableField("rating")
    private Float rating;

    // 评论引用提示
    @TableField("reply_hint")
    private Integer replyHint;

    @TableField("reply_text")
    private String replyText;

    // 创建时间（自动填充）
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
}