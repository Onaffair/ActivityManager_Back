package com.example.onaffair.online_chat.enums;


import lombok.Getter;

@Getter
public enum ActivityStatus {
    REJECTED(-1, "被拒绝"),
    TO_BE_AUDITED(0, "待审核"),
    WAITING_FOR_REGISTRATION(1, "等待报名"),
    REGISTRATION_OPEN(2, "报名中"),
    REGISTRATION_FULL(3, "报名人数已满"),
    ACTIVITY_ONGOING(4, "活动进行中"),
    ACTIVITY_ENDED(5, "活动已结束"),
    ACTIVITY_CANCELLED(6, "活动已取消");

    // 获取状态ID
    private final int id;       // 状态ID
    // 获取状态名称
    private final String name;  // 状态名称

    // 构造函数
    ActivityStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 根据ID查找对应的枚举值
    public static ActivityStatus getById(int id) {
        for (ActivityStatus status : ActivityStatus.values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的状态ID: " + id);
    }

    // 根据名称查找对应的枚举值
    public static ActivityStatus getByName(String name) {
        for (ActivityStatus status : ActivityStatus.values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的状态名称: " + name);
    }


}
