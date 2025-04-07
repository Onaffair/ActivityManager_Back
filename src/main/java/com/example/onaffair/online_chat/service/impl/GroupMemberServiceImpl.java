package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.entity.GroupMember;
import com.example.onaffair.online_chat.mapper.GroupMemberMapper;
import com.example.onaffair.online_chat.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    @Override
    public boolean addGroupMember(GroupMember groupMember) {
        return groupMemberMapper.insert(groupMember) > 0;
    }

    @Override
    public boolean removeGroupMember(GroupMember groupMember) {

        QueryWrapper<GroupMember> queryWrapper =new QueryWrapper<>();

        queryWrapper.eq("group_id",groupMember.getGroupId())
                .eq("user_account",groupMember.getUserAccount());

        return groupMemberMapper.delete(queryWrapper) > 0;
    }

    @Override
    public boolean deleteGroupMemberByGroupId(Integer groupId) {
        QueryWrapper<GroupMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);

        if (groupMemberMapper.selectCount(queryWrapper) == 0){
            return true;
        }
        return groupMemberMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<GroupMember> getGroupMemberListByAccount(String account) {
        QueryWrapper<GroupMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",account);
        return groupMemberMapper.selectList(queryWrapper);
    }

    @Override
    public List<GroupMember> getGroupMemberList(Integer groupId) {
        QueryWrapper<GroupMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);

        return groupMemberMapper.selectList(queryWrapper);
    }
}
