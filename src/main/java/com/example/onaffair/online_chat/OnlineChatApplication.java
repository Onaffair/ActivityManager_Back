package com.example.onaffair.online_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
public class OnlineChatApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(OnlineChatApplication.class, args);

    }

}
