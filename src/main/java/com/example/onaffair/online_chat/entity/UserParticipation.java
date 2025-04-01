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


@TableName("activity_enrollment")
public class UserParticipation {
    @TableId(type = IdType.AUTO)
    private Integer enrollmentId; // 报名ID

    @TableField("user_account")
    private String userAccount; // 用户账号，关联到user_table的user_account

    @TableField("activity_id")
    private Integer activityId; // 活动ID，关联到activity的id

    @TableField("enrollment_time")
    private LocalDateTime enrollmentTime; // 报名时间

    @TableField("is_confirmed")
    private Boolean isConfirmed; // 是否确认参加，默认为false

}
