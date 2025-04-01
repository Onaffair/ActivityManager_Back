package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.FriendMessage;

import java.util.List;

public interface FriendMessageService {

    boolean addMessage(FriendMessage friendMessage);

    List<FriendMessage> getMessageFriendID(String f1, String f2);
}
