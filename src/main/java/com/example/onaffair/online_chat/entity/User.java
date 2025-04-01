package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("user_table")
public class User {
    @TableField("user_account")
    private String account;

    @TableField("user_name")
    private String username;

    @TableField("user_password")
    private String password;

    @TableField("user_email")
    private String email;

    @TableField("user_phone")
    private String phone;

    @TableField("avatar")
    private String avatar;

    @TableField("status")
    private String status;

    @TableField("role")
    private Integer role;

}