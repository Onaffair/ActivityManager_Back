package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_follow")
public class Follow {
    @TableId(value = "follow_id", type = IdType.AUTO)
    private Long followId;

    @TableField("follower_id")
    private String followerId;  // 关注者ID

    @TableField("following_id")
    private String followingId; // 被关注者ID

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; // 关注时间
}
