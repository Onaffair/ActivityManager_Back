package com.example.onaffair.online_chat.enums;

import lombok.Getter;

@Getter
public enum ReportType {
    ILLEGAL_CONTENT("illegal_content", "违法违规内容"),
    FALSE_INFORMATION("false_information", "虚假信息"),
    FRAUD("fraud", "诈骗行为"),
    INAPPROPRIATE("inappropriate", "不当内容"),
    OTHER("other", "其他");

    private final String value;  // 数据库存储值
    private final String name;   // 显示名称

    // 构造函数
    ReportType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    // 根据值查找对应的枚举
    public static ReportType getByValue(String value) {
        for (ReportType type : ReportType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的举报类型: " + value);
    }

    // 根据名称查找对应的枚举
    public static ReportType getByName(String name) {
        for (ReportType type : ReportType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的举报类型名称: " + name);
    }
}