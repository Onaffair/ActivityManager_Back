package com.example.onaffair.online_chat.config;


import com.example.onaffair.online_chat.property.AIProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AIConfig {


    @Value("${ai.api.deepseek.key}")
    private String Dskey;

    @Value("${ai.api.deepseek.url}")
    private String DsUrl;

    @Value("${ai.api.deepseek.model}")
    private String DsModel;

    @Value("${ai.api.VL.model}")
    private String QwenModel;

    @Value("${ai.api.VL.key}")
    private String QwenKey;
    @Value("${ai.api.VL.url}")
    private String QwenUrl;

    @Bean(name="deepseek")
    public AIProperty DsProperty(){
        return new AIProperty(DsUrl, Dskey,DsModel);
    }

    @Bean(name="VL")
    public AIProperty QwenProperty(){
        return new AIProperty(QwenUrl, QwenKey,QwenModel);
    }

}
