package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.onaffair.online_chat.handler.StringListTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("repair_request")
public class RepairRequest {
    @TableId(type = IdType.AUTO)
    private Long requestId;
    
    private String userAccount;
    
    private String applianceType;
    
    private String applianceModel;
    
    private String problemDescription;
    
    @TableField(value = "evidence_images", typeHandler = StringListTypeHandler.class)
    private List<String> evidenceImages;
    
    private String requestStatus; // pending, assigned, in_progress, completed, rejected
    
    private String technicianAccount;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;
}