package com.example.onaffair.online_chat.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


import java.util.Arrays;
import java.util.List;



public enum CityENUM {

    HANGZHOU(11, "杭州"),
    NINGBO(21, "宁波"),
    WENZHOU(31, "温州"),
    JIAXING(41, "嘉兴"),
    HUZHOU(51, "湖州"),
    SHAOXING(61, "绍兴"),
    JINHUA(71, "金华"),
    QUZHOU(81, "衢州"),
    ZHOUSHAN(91, "舟山"),
    TAIZHOU(101, "台州"),
    LISHUI(111, "丽水");

    private final int id;
    private final String name;

    CityENUM(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // 获取浙江省所有城市
    public static List<CityENUM> zhejiangCities() {
        return Arrays.asList(values());
    }

    // 根据ID获取城市
    public static CityENUM getById(int id) {
        for (CityENUM cityENUM : values()) {
            if (cityENUM.id == id) {
                return cityENUM;
            }
        }
        return null;
    }

    @JsonCreator
    public static CityENUM forJson(@JsonProperty("id") int id){
        return Arrays.stream(values())
                .filter(e -> e.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无效的城市ID: " + id));
    }
}
