package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.ActivityComment;
import com.example.onaffair.online_chat.mapper.ActivityCommentMapper;
import com.example.onaffair.online_chat.service.ActivityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityCommentServiceImpl implements ActivityCommentService {

    @Autowired
    private ActivityCommentMapper activityCommentMapper;

    @Override
    public boolean addComment(ActivityComment activityComment) {
        return activityCommentMapper.insert(activityComment) > 0;
    }

    @Override
    public List<ActivityComment> getCommentByActivityID(Integer activityID) {
        QueryWrapper<ActivityComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id",activityID);
        return activityCommentMapper.selectList(queryWrapper);
    }
}
