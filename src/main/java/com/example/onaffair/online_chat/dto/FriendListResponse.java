package com.example.onaffair.online_chat.dto;


import com.example.onaffair.online_chat.entity.FriendMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendListResponse {
    //好友基本信息
    String userName;
    String account;
    String email;
    String avatar;
    String phone;
    //聊天信息
    List<FriendMessage> chat;
}
