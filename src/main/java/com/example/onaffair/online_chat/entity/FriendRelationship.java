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
@TableName("friend_relationship")
public class FriendRelationship {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_account1")
    private String userAccount1;

    @TableField("user_account2")
    private String userAccount2;

    @TableField("created_at")
    private LocalDateTime createdAt;

}
