package com.example.onaffair.online_chat.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("group_message")
public class GroupMessage {

    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer groupMessageId;

    @TableField("sender_account")
    private String sender;

    @TableField("group_id")
    private Integer groupId;

    @TableField("text_content")
    private String textContent;

    @TableField("image_url")
    private String imageUrl;

    @TableField(value = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;



}
