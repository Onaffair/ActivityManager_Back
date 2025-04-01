package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("`group`") // 需转义表名
public class Group {
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    @TableField("group_name")
    private String groupName;

    @TableField("owner_account")
    private String ownerAccount;

    @TableField("announcement")
    private String announcement;

    @TableField("avatar")
    private String avatar;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField("status")
    private Integer status = 1; // 默认正常

    @TableField("activity_id")
    private Integer activityId;

}