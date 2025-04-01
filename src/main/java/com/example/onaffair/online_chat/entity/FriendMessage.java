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
@TableName("friend_message")
public class FriendMessage {
    // 主键ID（自增）
    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer friendMessageId;

    // 发送方账号（外键关联用户表）
    @TableField("sender_account")
    private String sender;

    // 接收方账号（外键关联用户表）
    @TableField("receiver_account")
    private String receiver;

    // 文字内容
    @TableField("text_content")
    private String textContent;

    // 图片链接（单张）
    @TableField("image_url")
    private String imageUrl;

    // 创建时间（自动填充）
    @TableField(value = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // --------------------- 可选扩展字段 ---------------------
    // 是否撤回（默认未撤回）
//    @TableField("is_deleted")
//    private Boolean isDeleted = false;

    // 消息类型（预留扩展）
    // @TableField("message_type")
    // private Integer messageType;
}