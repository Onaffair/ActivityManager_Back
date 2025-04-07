package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.entity.UserParticipation;

import java.util.List;

public interface ActivityService {


    boolean createActivity(Activity activity);

    boolean deleteActivity(Integer id);

    boolean updateActivity(Activity activity);

    List<Activity> getActivityList(Integer categoryId, Integer page, Integer pageSize, String keyword);

    Activity getActivityById(Integer id);

    boolean joinActivity(UserParticipation userParticipation);

    List<Activity> getActivityJoined(String account);

    boolean cancelActivityJoin(UserParticipation userParticipation);

    List<Activity> getActivityList(String account);

    boolean cancelActivity(Integer id,String account);

    List<Activity> getTopActivity(Integer num);

    List<Activity> getAllActivities();

    boolean deleteActivityParticipationByActivityId(Integer activityId);
}
