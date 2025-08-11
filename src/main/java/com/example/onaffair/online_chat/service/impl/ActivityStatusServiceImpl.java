package com.example.onaffair.online_chat.service.impl;


import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.enums.ActivityStatus;
import com.example.onaffair.online_chat.mapper.ActivityMapper;
import com.example.onaffair.online_chat.service.ActivityStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ActivityStatusServiceImpl implements ActivityStatusService {
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public void updateActivityStatus() {

        List<Activity> activities = activityMapper.selectList(null);

        LocalDateTime now = LocalDateTime.now();
        activities.forEach(activity -> {
            int newStatus = calculateStatus(activity,now);
            if (newStatus != activity.getStatus()){
                activity.setStatus(newStatus);
                activityMapper.updateById(activity);
            }
        });

    }

    private int calculateStatus(Activity activity, LocalDateTime now) {

        if (activity.getStatus() == ActivityStatus.TO_BE_AUDITED.getId() || activity.getStatus() == ActivityStatus.REJECTED.getId()){
            return activity.getStatus();
        }

        // 已取消的活动不再更新
        if (activity.getStatus() == ActivityStatus.ACTIVITY_CANCELLED.getId()) {
            return activity.getStatus();
        }

        // 活动已结束
        if (now.isAfter(activity.getEndTime())) {
            return ActivityStatus.ACTIVITY_ENDED.getId();
        }

        // 活动进行中
        if (now.isAfter(activity.getBeginTime()) && now.isBefore(activity.getEndTime())) {
            return ActivityStatus.ACTIVITY_ONGOING.getId();
        }

        // 报名人数已满
        if (activity.getJoinNum() >= activity.getMostJoinNum()) {
            return ActivityStatus.REGISTRATION_FULL.getId();
        }

        // 默认返回报名中
        return ActivityStatus.REGISTRATION_OPEN.getId();
    }


}
