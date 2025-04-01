package com.example.onaffair.online_chat.util;

import com.example.onaffair.online_chat.dto.WebsocketMessage;
import com.example.onaffair.online_chat.entity.FriendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class ChannelManager {
    // 用户账号 -> 对应的Channel集合（支持多设备）

    private static final ConcurrentHashMap<String, Set<Channel>> userChannelMap = new ConcurrentHashMap<>();


    // 添加Channel到映射
    public static void addChannel(String account, Channel channel) {
        userChannelMap.computeIfAbsent(account, k -> ConcurrentHashMap.newKeySet())
                .add(channel);
    }

    // 移除Channel
    public static void removeChannel(String account, Channel channel) {
        Set<Channel> channels = userChannelMap.get(account);
        if (channels != null) {
            channels.remove(channel);
            if (channels.isEmpty()) {
                userChannelMap.remove(account);
            }
        }
    }

    // 根据账号获取所有Channel
    public static Set<Channel> getChannelsByAccount(String account) {
        return userChannelMap.getOrDefault(account, Collections.emptySet());
    }

}