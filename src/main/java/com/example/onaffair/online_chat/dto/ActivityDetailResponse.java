package com.example.onaffair.online_chat.dto;


import com.example.onaffair.online_chat.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailResponse {
    Activity activity;
    UserInfoResponse organizerInfo;
    List<UserCommentResponse> comments;
}
