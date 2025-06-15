package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.AIChatLog;
import com.example.onaffair.online_chat.mapper.AIChatLogMapper;
import com.example.onaffair.online_chat.service.AIChatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AIChatLogServiceImpl implements AIChatLogService {

    @Autowired
    private AIChatLogMapper aiChatLogMapper;

    @Override
    public boolean insert(AIChatLog aiChatLog) {
        return aiChatLogMapper.insert(aiChatLog) > 0;
    }

    @Override
    public List<AIChatLog> getAIChatLogListBySessionId(String sessionId) {
        QueryWrapper<AIChatLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("session_id",sessionId);

        List<AIChatLog> res = aiChatLogMapper.selectList(queryWrapper);
        res.sort((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()));
        return res;
    }
}
