package com.example.onaffair.online_chat.enums;

import lombok.Getter;

@Getter
public enum HandleAction {
    ACCEPT("accept", "接受举报"),
    REJECT("reject", "驳回举报"),
    ACTIVITY_REMOVED("activity_removed", "删除活动"),
    WARNING_SENT("warning_sent", "发送警告"),
    USER_BANNED("user_banned", "封禁用户");

    private final String value;  // 数据库存储值
    private final String name;   // 显示名称

    // 构造函数
    HandleAction(String value, String name) {
        this.value = value;
        this.name = name;
    }

    // 根据值查找对应的枚举
    public static HandleAction getByValue(String value) {
        for (HandleAction action : HandleAction.values()) {
            if (action.value.equals(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("无效的处理措施: " + value);
    }

    // 根据名称查找对应的枚举
    public static HandleAction getByName(String name) {
        for (HandleAction action : HandleAction.values()) {
            if (action.name.equals(name)) {
                return action;
            }
        }
        throw new IllegalArgumentException("无效的处理措施名称: " + name);
    }
}