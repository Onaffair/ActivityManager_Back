package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.FriendRequest;
import com.example.onaffair.online_chat.mapper.FriendRequestMapper;
import com.example.onaffair.online_chat.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Override
    public boolean addFriendRequest(FriendRequest friendRequest) {

        FriendRequest isExist = getFriendRequest(friendRequest.getSender(), friendRequest.getReceiver());
        if (isExist != null && (isExist.getStatus() == 0 || isExist.getStatus() == 1)) {
            return false;
        }
        return friendRequestMapper.insert(friendRequest) > 0;
    }

    @Override
    public boolean updateFriendRequest(FriendRequest friendRequest) {
        if (friendRequest == null || friendRequest.getFriendRequestId() == null) {
            return false; // 或者抛出异常
        }
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("request_id",friendRequest.getFriendRequestId());
        return friendRequestMapper.update(friendRequest,queryWrapper) > 0;
    }

    @Override
    public FriendRequest getFriendRequestById(Integer requestId) {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("request_id",requestId);
        return friendRequestMapper.selectOne(queryWrapper);
    }

    @Override
    public FriendRequest getFriendRequest(String sender, String receiver) {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_account",sender)
                .eq("receiver_account",receiver);
        return friendRequestMapper.selectOne(queryWrapper);
    }

    @Override
    public List<FriendRequest> getFriendRequestByReceiver(String receiver) {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_account",receiver)
                .orderByDesc("created_at");
        return friendRequestMapper.selectList(queryWrapper);
    }
}
