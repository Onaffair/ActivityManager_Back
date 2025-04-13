package com.example.onaffair.online_chat.enums;

import lombok.Getter;

@Getter
public enum ActivityCategory {
    ALL(0, "所有分类"),
    VOLUNTEER(1, "志愿服务"),
    FESTIVAL(2, "节日庆典"),
    PUBLIC_WELFARE(3, "公益便民"),
    CULTURAL(4, "文化活动"),
    SPORTS(5, "健康运动"),
    EDUCATION(6, "教育培训"),
    NEIGHBORHOOD(7, "邻里互助"),
    GOVERNMENT(8, "街道政务"),
    OTHER(9, "其它活动");

    private final int id;
    private final String name;

    ActivityCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ActivityCategory getById(int id) {
        for (ActivityCategory category : values()) {
            if (category.id == id) {
                return category;
            }
        }
        return null;
    }

    public static ActivityCategory getByName(String name) {
        for (ActivityCategory category : values()) {
            if (category.name.equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

}

