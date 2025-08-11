package com.example.onaffair.online_chat.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.onaffair.online_chat.handler.StringListTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("announcement")
public class Announcement {

    @TableId(value = "announcement_id",type = IdType.ASSIGN_UUID)
    private String announcementId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField(value = "images",typeHandler = StringListTypeHandler.class)
    List<String> images;

    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @TableField("admin_account")
    private String adminAccount;
}
