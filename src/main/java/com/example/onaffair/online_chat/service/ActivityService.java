package com.example.onaffair.online_chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    
    /**
     * 获取待审核的活动列表
     * @param page 页码
     * @param pageSize 页大小
     * @return 待审核活动分页列表
     */
    IPage<Activity> getPendingActivities(Integer page, Integer pageSize);
    
    /**
     * 审核通过活动
     * @param activityId 活动ID
     * @return 是否成功
     */
    boolean approveActivity(Integer activityId);
    
    /**
     * 审核拒绝活动
     * @param activityId 活动ID
     * @return 是否成功
     */
    boolean rejectActivity(Integer activityId);
}
