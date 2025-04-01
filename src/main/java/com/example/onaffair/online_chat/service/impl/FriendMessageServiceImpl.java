package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.FriendMessage;
import com.example.onaffair.online_chat.mapper.FriendMessageMapper;
import com.example.onaffair.online_chat.service.FriendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendMessageServiceImpl implements FriendMessageService {
    @Autowired
    private FriendMessageMapper friendMessageMapper;
    @Override
    public boolean addMessage(FriendMessage friendMessage) {
        return friendMessageMapper.insert(friendMessage) > 0;
    }

    @Override
    public List<FriendMessage> getMessageFriendID(String f1, String f2) {
        QueryWrapper<FriendMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_account",f1)
                .eq("receiver_account",f2)
                .or()
                .eq("sender_account",f2)
                .eq("receiver_account",f1)
                .orderByAsc("created_at");
        return friendMessageMapper.selectList(queryWrapper);
    }
}
