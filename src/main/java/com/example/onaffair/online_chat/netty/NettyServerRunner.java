package com.example.onaffair.online_chat.netty;

import com.example.onaffair.online_chat.util.ChannelManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class NettyServerRunner implements DisposableBean {

    @Autowired
    private ObjectMapper objectMapper;

    private NettyServer nettyServer;

    @Autowired
    private SubprotocolTokenHandler subprotocolTokenHandler;
    @Autowired
    private WebSocketChatServerHandler webSocketChatServerHandler;

    @Async
    @PostConstruct
    public void startServer(){
        nettyServer = new NettyServer(9000, subprotocolTokenHandler, webSocketChatServerHandler);


        try {
            nettyServer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() throws InterruptedException {
        System.out.println("netty关闭中");
        if (nettyServer == null) return;
        nettyServer.stop();
        System.out.println("netty关闭");
    }

    @Override
    public void destroy() throws Exception {
        stopServer();
    }
}
