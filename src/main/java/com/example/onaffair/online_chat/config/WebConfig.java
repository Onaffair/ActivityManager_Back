package com.example.onaffair.online_chat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class WebConfig implements WebMvcConfigurer {

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setAwaitTerminationSeconds(60); // 等待异步任务完成的最长时间
        executor.setWaitForTasksToCompleteOnShutdown(true); // 关闭时等待任务完成
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
    //前后端一个有配就行
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置全局 CORS
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的 HTTP 方法
                .allowedHeaders("*")  // 允许所有请求头
                .allowCredentials(true);  // 允许携带认证信息（如 cookies）
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射运行时路径
        registry.addResourceHandler("/static/avatar/**")
                .addResourceLocations("file:D:\\Code\\Database\\avatar\\");
    }
}

