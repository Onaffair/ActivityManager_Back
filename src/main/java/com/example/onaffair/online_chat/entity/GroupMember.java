package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("group_member")
public class GroupMember {
    @TableId(value = "member_id", type = IdType.AUTO)
    private Integer memberId;

    @TableField("group_id")
    private Integer groupId;

    @TableField("user_account")
    private String userAccount;

    @TableField("role")
    private Integer role = 2; // 默认普通成员

    @TableField(value = "joined_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinedAt;

    @TableField("last_active")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastActive;
}