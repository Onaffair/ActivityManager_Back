package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.ActivityComment;

import java.util.List;

public interface ActivityCommentService {
    boolean addComment(ActivityComment activityComment);

    List<ActivityComment> getCommentByActivityID(Integer activityID);

}
