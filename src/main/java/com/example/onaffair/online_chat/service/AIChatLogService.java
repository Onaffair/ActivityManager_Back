package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.AIChatLog;


import java.util.List;

public interface AIChatLogService {

    boolean insert(AIChatLog aiChatLog);

    List<AIChatLog> getAIChatLogListBySessionId(String sessionId);

}
