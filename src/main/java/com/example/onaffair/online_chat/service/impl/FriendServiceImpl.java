package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.FriendRelationship;
import com.example.onaffair.online_chat.mapper.FriendMapper;
import com.example.onaffair.online_chat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public boolean addFriend(FriendRelationship relationship) {
        QueryWrapper<FriendRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account1", relationship.getUserAccount1())
                .eq("user_account2", relationship.getUserAccount2())
                .or()
                .eq("user_account1", relationship.getUserAccount2())
                .eq("user_account2", relationship.getUserAccount1());
        if (friendMapper.selectOne(queryWrapper) != null) {
            return false;
        }
        return friendMapper.insert(relationship) > 0;
    }

    @Override
    public List<FriendRelationship> getFriendList(String account) {
        QueryWrapper<FriendRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account1", account)
                .or()
                .eq("user_account2", account);
        return friendMapper.selectList(queryWrapper);
    }
}
