package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.entity.UserParticipation;
import com.example.onaffair.online_chat.enums.ActivityStatus;
import com.example.onaffair.online_chat.mapper.ActivityMapper;
import com.example.onaffair.online_chat.mapper.UserParticipationMapper;
import com.example.onaffair.online_chat.service.ActivityService;
import com.example.onaffair.online_chat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserParticipationMapper userParticipationMapper;
    
    @Autowired
    private GroupService groupService;

    @Override
    public boolean createActivity(Activity activity) {
        return activityMapper.insert(activity) > 0;
    }

    @Override
    public Activity getActivityById(Integer id) {
        return activityMapper.selectById(id);
    }


    @Override
    public boolean deleteActivity(Integer id) {
        return activityMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return activityMapper.updateById(activity) > 0;
    }

    @Override
    public List<Activity> getActivityList(Integer categoryId, Integer page, Integer pageSize, String keyword) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();

        if (categoryId != 0){
            queryWrapper.eq("category_id",categoryId);
        }
        queryWrapper
                .and(wrapper ->
                        wrapper.like("title",keyword)
                            .or()
                            .like("address",keyword)
                            .or()
                            .like("organizer",keyword)
                            .or()
                            .like("highlight",keyword))
                .ne("status",ActivityStatus.REJECTED.getId())
                .ne("status",ActivityStatus.TO_BE_AUDITED.getId())
                .orderByDesc("created_at");

        Page<Activity> res = new Page<>();
        res.setCurrent(page);
        res.setSize(pageSize);
        Page<Activity> activityPage = activityMapper.selectPage(res, queryWrapper);

        return activityPage.getRecords();
    }

    @Override
    public List<Activity> getActivityJoined(String account) {
        QueryWrapper<UserParticipation> participationQueryWrapper = new QueryWrapper<>();
        participationQueryWrapper.eq("user_account",account);
        List<UserParticipation> participationList = userParticipationMapper.selectList(participationQueryWrapper);
        if (participationList.isEmpty()){
            return Collections.emptyList();
        }

        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.in("id",participationList.stream().map(UserParticipation::getActivityId).toList());
        return activityMapper.selectList(activityQueryWrapper);
    }

    @Override
    public boolean deleteActivityParticipationByActivityId(Integer activityId) {
        QueryWrapper<UserParticipation> queryWrapper = new QueryWrapper<UserParticipation>()
                .eq("activity_id", activityId);

        if (userParticipationMapper.selectCount(queryWrapper) == 0){
            return true;
        }

        return userParticipationMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<Activity> getAllActivities() {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        return activityMapper.selectList(queryWrapper);
    }

    @Override
    public List<Activity> getTopActivity(Integer num) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",ActivityStatus.REGISTRATION_OPEN.getId())
                .orderByDesc("rating")
                .last("limit " + num);

        return activityMapper.selectList(queryWrapper);
    }

    @Override
    public boolean cancelActivity(Integer id, String account) {
        Activity activity = activityMapper.selectById(id);
        if (!activity.getOrganizer().equals(account)){
            throw new RuntimeException("您不是活动创建者，无法取消活动");
        }

        activity.setStatus(ActivityStatus.ACTIVITY_CANCELLED.getId());

        return activityMapper.updateById(activity) > 0;

    }

    @Override
    public List<Activity> getActivityList(String account) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("organizer",account);
        return activityMapper.selectList(queryWrapper);
    }

    @Override
    public boolean cancelActivityJoin(UserParticipation userParticipation) {
        Activity activity = activityMapper.selectById(userParticipation.getActivityId());
        if (activity == null){
            throw new RuntimeException("活动不存在");
        }
        if (Objects.equals(activity.getOrganizer(), userParticipation.getUserAccount())){
            throw new RuntimeException("活动创建者只能取消活动");
        }
        QueryWrapper<UserParticipation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userParticipation.getUserAccount());
        queryWrapper.eq("activity_id",userParticipation.getActivityId());
        int count = Math.toIntExact(userParticipationMapper.selectCount(queryWrapper));
        if (count == 0){
            throw new RuntimeException("您没有报名该活动");
        }
        return userParticipationMapper.delete(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean joinActivity(UserParticipation userParticipation) {

//        存在数据库同步问题
//        Activity activity = activityMapper.selectById(userParticipation.getActivityId());


        /*悲观锁*/
        Activity activity = activityMapper.selectOne(
                new QueryWrapper<Activity>()
                        .eq("id", userParticipation.getActivityId())
                        .last("for update")
        );


        if (activity == null){
            throw new RuntimeException("活动不存在");
        }
        QueryWrapper<UserParticipation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userParticipation.getUserAccount());
        queryWrapper.eq("activity_id",userParticipation.getActivityId());
        int count = Math.toIntExact(userParticipationMapper.selectCount(queryWrapper));
        if (count > 0) {
            throw new RuntimeException("您已经报名该活动");
        }
        if (activity.getJoinNum() >= activity.getMostJoinNum()){
            throw new RuntimeException("活动人数已满");
        }
        return userParticipationMapper.insert(userParticipation) > 0;
    }
    
    @Override
    public IPage<Activity> getPendingActivities(Integer page, Integer pageSize) {
        Page<Activity> pageObj = new Page<>(page, pageSize);
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", ActivityStatus.TO_BE_AUDITED.getId());
        queryWrapper.orderByDesc("created_at");
        return activityMapper.selectPage(pageObj, queryWrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveActivity(Integer activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }
        if (activity.getStatus() != ActivityStatus.TO_BE_AUDITED.getId()) {
            throw new RuntimeException("活动状态不正确");
        }
        
        // 更新活动状态为已通过
        activity.setStatus(ActivityStatus.WAITING_FOR_REGISTRATION.getId());
        boolean updateSuccess = activityMapper.updateById(activity) > 0;
        
        if (updateSuccess) {
            // 创建群组
            try {
                groupService.createGroupByActivity(activity);
                
                // 自动让发起人加入活动
                UserParticipation userParticipation = new UserParticipation();
                userParticipation.setUserAccount(activity.getOrganizer());
                userParticipation.setActivityId(activity.getId());
                
                QueryWrapper<UserParticipation> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_account", activity.getOrganizer());
                queryWrapper.eq("activity_id", activity.getId());
                if (userParticipationMapper.selectCount(queryWrapper) == 0) {
                    userParticipationMapper.insert(userParticipation);
                }
            } catch (Exception e) {
                throw new RuntimeException("创建群组失败: " + e.getMessage());
            }
        }
        
        return updateSuccess;
    }
    
    @Override
    public boolean rejectActivity(Integer activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }
        if (activity.getStatus() != ActivityStatus.TO_BE_AUDITED.getId()) {
            throw new RuntimeException("活动状态不正确");
        }
        
        // 更新活动状态为已拒绝
        activity.setStatus(ActivityStatus.REJECTED.getId());
        return activityMapper.updateById(activity) > 0;
    }

}
