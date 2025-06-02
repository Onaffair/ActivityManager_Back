package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.AIChatSession;

import java.util.List;

public interface AIChatSessionService {

    boolean insert(AIChatSession aiChatSession);

    List<AIChatSession> getAIChatSessionListByUserAccount(String account);

    AIChatSession getAIChatSessionById(String id);

}
