package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.FriendRequest;

import java.util.List;

public interface FriendRequestService {


    boolean addFriendRequest(FriendRequest friendRequest);

    boolean updateFriendRequest(FriendRequest friendRequest);

    FriendRequest getFriendRequestById(Integer requestId);

    List<FriendRequest> getFriendRequestByReceiver(String receiver);

    FriendRequest getFriendRequest(String sender,String receiver);
}
