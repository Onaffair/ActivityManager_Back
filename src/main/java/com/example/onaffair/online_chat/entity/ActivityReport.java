package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.onaffair.online_chat.handler.StringListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("activity_report")
public class ActivityReport {
    @TableId(type = IdType.AUTO)
    private Long reportId;

    @TableField("activity_id")
    private Integer activityId;

    @TableField("reporter")
    private String reporter;

    @TableField("report_type")
    private String reportType;

    @TableField("description")
    private String description;

    @TableField(value = "evidence_images", typeHandler = StringListTypeHandler.class)
    private List<String> evidenceImages;

    @TableField("report_status")
    private String reportStatus;

    @TableField("handler_account")
    private String handlerAccount;

    @TableField("handle_action")
    private String handleAction;

    @TableField("handle_comment")
    private String handleComment;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("handled_at")
    private LocalDateTime handledAt;
}