package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.dto.FriendListResponse;
import com.example.onaffair.online_chat.entity.FriendRelationship;

import java.util.List;


public interface FriendService {

    boolean addFriend(FriendRelationship relationship);

    List<FriendRelationship> getFriendList(String account);

}
