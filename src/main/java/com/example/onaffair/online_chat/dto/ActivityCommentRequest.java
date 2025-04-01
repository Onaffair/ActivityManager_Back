package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCommentRequest {
    private Integer activityId;
    private String userId;
    private String textContent;
    private String imageUrl;
    private Float rating;
    private Integer replyHint;
    private String replyText;


}
