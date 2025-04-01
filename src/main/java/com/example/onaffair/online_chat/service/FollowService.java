package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.dto.FollowResponse;
import com.example.onaffair.online_chat.entity.Follow;

import java.util.List;

public interface FollowService {

    boolean follow(Follow follow);

    boolean unfollow(Follow follow);

    List<String> getFollowList(String userId);

    List<String> getFollowingList(String userId);
}
