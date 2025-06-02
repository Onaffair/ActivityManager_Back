package com.example.onaffair.online_chat.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ai_chat_session")
public class AIChatSession {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_account")
    private String userAccount;

    @TableField("activity_id")
    private Integer activityId;

    @TableField("title")
    private String title;

    @TableField("message_count")
    private String messageCount;

    @TableField("created_at")
    private LocalDateTime  createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
