package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.Group;
import com.example.onaffair.online_chat.mapper.GroupMapper;
import com.example.onaffair.online_chat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    private GroupMapper groupMapper;


    @Override
    public List<Group> getGroupList(List<Integer> groupIdList) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("group_id",groupIdList);
        return groupMapper.selectList(queryWrapper);
    }

    @Override
    public boolean createGroup(Group group) {
        return groupMapper.insert(group) > 0;
    }


    @Override
    public Group getGroupByActivityId(Integer id) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id",id);
        return groupMapper.selectOne(queryWrapper);
    }

    @Override
    public Group getGroupById(Integer id) {
        return groupMapper.selectById(id);
    }

    @Override
    public boolean updateGroup(Group group) {
        return groupMapper.updateById(group) > 0;
    }

    @Override
    public List<Group> getAllGroups() {
        return groupMapper.selectList(null);
    }

    @Override
    public boolean deleteGroup(Integer id) {
        return groupMapper.deleteById(id) > 0;
    }
}
