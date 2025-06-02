package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.AIChatSession;
import com.example.onaffair.online_chat.mapper.AIChatSessionMapper;
import com.example.onaffair.online_chat.service.AIChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class AIChatSessionServiceImpl implements AIChatSessionService {


    @Override
    public AIChatSession getAIChatSessionById(String id) {
        if (id != null){
            return aiChatSessionMapper.selectById(id);
        }
        return null;
    }

    @Autowired
    private AIChatSessionMapper aiChatSessionMapper;

    @Override
    public boolean insert(AIChatSession aiChatSession) {
        return aiChatSessionMapper.insert(aiChatSession) > 0;
    }

    @Override
    public List<AIChatSession> getAIChatSessionListByUserAccount(String account) {
        QueryWrapper<AIChatSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",account);
        return aiChatSessionMapper.selectList(queryWrapper);
    }
}
