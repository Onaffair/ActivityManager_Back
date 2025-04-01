package com.example.onaffair.online_chat.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.Follow;
import com.example.onaffair.online_chat.mapper.FollowMapper;
import com.example.onaffair.online_chat.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public boolean follow(Follow follow) {
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id",follow.getFollowerId())
                .eq("following_id",follow.getFollowingId());
        if (followMapper.selectOne(queryWrapper) != null){
            return false;
        }

        return followMapper.insert(follow) > 0;
    }

    @Override
    public boolean unfollow(Follow follow) {
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id",follow.getFollowerId())
                .eq("following_id",follow.getFollowingId());
        return followMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<String> getFollowList(String userId) {
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("following_id",userId);

        return followMapper.selectList(queryWrapper)
                .stream().map(Follow::getFollowerId)
                .toList();
    }

    @Override
    public List<String> getFollowingList(String userId) {
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follower_id",userId);
        return followMapper.selectList(queryWrapper)
                .stream().map(Follow::getFollowingId)
                .toList();
    }
}
