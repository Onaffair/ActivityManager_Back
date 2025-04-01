package com.example.onaffair.online_chat.dto;

import com.example.onaffair.online_chat.entity.ActivityComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentResponse {
    UserInfoResponse userinfo;
    ActivityComment comment;
}
