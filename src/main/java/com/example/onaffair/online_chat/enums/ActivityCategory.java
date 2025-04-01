package com.example.onaffair.online_chat.enums;

import lombok.Getter;

@Getter
public enum ActivityCategory {
    ALL(0, "所有分类"),
    STUDY(1, "学习"),
    TRAVEL(2, "旅游"),
    SPORTS(3, "运动"),
    ENTERTAINMENT(4, "休闲娱乐"),
    GATHERING(5, "聚会"),
    OTHER(9, "其它");

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

