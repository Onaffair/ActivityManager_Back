package com.example.onaffair.online_chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    @NotNull(message = "活动标题不能为空")
    @Size(max = 32, message = "标题最长32个字符")
    private String title;

    @NotNull(message = "活动分类不能为空")
    private Integer categoryId;

    @NotBlank(message = "活动亮点不能为空")
    @Size(max = 150, message = "亮点最多150字")
    private String highlight;

    @NotBlank(message = "活动内容不能为空")
    @Size(min = 50, message = "内容至少需要50字")
    private String content;

    @Size(max = 4, message = "最多上传4张图片")
    private List<String> images;

    @NotNull(message = "请选择城市")
    private int city;

    @NotBlank(message = "详细地址不能为空")
    @Size(min = 5, message = "地址至少需要5个字")
    private String address;

    @NotNull(message = "请选择开始时间")
    @Future(message = "开始时间需在未来")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime beginTime;

    @NotNull(message = "请选择结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Future(message = "结束时间需在未来")
    private LocalDateTime endTime;

    @NotNull(message = "请填写最少人数")
    @Min(value = 1, message = "最少人数至少1人")
    private Integer leastJoinNum;

    @NotNull(message = "请填写最多人数")
    @Min(value = 1, message = "最多人数至少1人")
    private Integer mostJoinNum;

    // 自定义验证方法
    @AssertTrue(message = "结束时间必须晚于开始时间")
    public boolean isEndTimeValid() {
        return endTime.isAfter(beginTime);
    }

    @AssertTrue(message = "最多人数必须大于最少人数")
    public boolean isParticipantValid() {
        return mostJoinNum > leastJoinNum;
    }
}