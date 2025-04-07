package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.GroupMessage;
import com.example.onaffair.online_chat.mapper.GroupMessageMapper;
import com.example.onaffair.online_chat.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMessageServiceImpl implements GroupMessageService {

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Override
    public boolean deleteGroupMessageByGroupId(Integer groupId) {
        QueryWrapper<GroupMessage> queryWrapper = new QueryWrapper<GroupMessage>().eq("group_id", groupId);
        if (groupMessageMapper.selectCount(queryWrapper) == 0){
            return true;
        }
        return groupMessageMapper.delete(queryWrapper) > 0;
    }

    @Override
    public boolean addGroupMessage(GroupMessage groupMessage) {
        return groupMessageMapper.insert(groupMessage) > 0;
    }

    @Override
    public List<GroupMessage> getGroupMessageList(Integer groupId) {
        QueryWrapper<GroupMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId)
                .orderByAsc("created_at");
        return groupMessageMapper.selectList(queryWrapper);
    }
}
