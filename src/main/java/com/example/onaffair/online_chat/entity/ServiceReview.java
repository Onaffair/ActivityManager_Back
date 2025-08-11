package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("service_review")
public class ServiceReview {
    @TableId(type = IdType.AUTO)
    private Long reviewId;
    
    private Long requestId;
    
    private BigDecimal rating; // 评分 0.0-5.0
    
    private String reviewText;
    
    private String imageUrl;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}