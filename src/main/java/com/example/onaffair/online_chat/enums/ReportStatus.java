package com.example.onaffair.online_chat.enums;

import lombok.Getter;

@Getter
public enum ReportStatus {
    PENDING("pending", "待处理"),
    PROCESSING("processing", "处理中"),
    RESOLVED("resolved", "已处理"),
    REJECTED("rejected", "已驳回");

    private final String value;  // 数据库存储值
    private final String name;   // 显示名称

    // 构造函数
    ReportStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

    // 根据值查找对应的枚举
    public static ReportStatus getByValue(String value) {
        for (ReportStatus status : ReportStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的举报状态: " + value);
    }

    // 根据名称查找对应的枚举
    public static ReportStatus getByName(String name) {
        for (ReportStatus status : ReportStatus.values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的举报状态名称: " + name);
    }
}