package com.example.onaffair.online_chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.onaffair.online_chat.handler.JsonTypeHandler;
import com.example.onaffair.online_chat.handler.StringListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("activity")
public class Activity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("organizer")
    private String organizer;

    @TableField("title")
    private String title;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("highlight")
    private String highlight;

    @TableField("content")
    private String content;

    @TableField(value = "images",typeHandler = StringListTypeHandler.class)
    private List<String> images;

    @TableField("city_id")
    private Integer cityId;

    @TableField("address")
    private String address;

    @TableField("begin_time")
    private LocalDateTime beginTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("least_join_num")
    private Integer leastJoinNum;

    @TableField("most_join_num")
    private Integer mostJoinNum;

    @TableField("status")
    private Integer status = 1;

    @TableField("join_num")
    private Integer joinNum = 0;

    @TableField("collect_num")
    private Integer collectNum = 0;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("rating")
    private double rating;
}
