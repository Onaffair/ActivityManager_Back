package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.dto.GroupResponse;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.entity.Group;
import com.example.onaffair.online_chat.entity.GroupMember;
import com.example.onaffair.online_chat.entity.GroupMessage;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.mapper.GroupMapper;
import com.example.onaffair.online_chat.service.GroupMemberService;
import com.example.onaffair.online_chat.service.GroupMessageService;
import com.example.onaffair.online_chat.service.GroupService;
import com.example.onaffair.online_chat.service.UserService;
import com.example.onaffair.online_chat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private UserService userService;


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
    public GroupResponse createGroupByActivity(Activity activity) throws Exception{
        Group group = new Group() {{
            setActivityId(activity.getId());
            setGroupName(activity.getTitle() + "活动群");
            setOwnerAccount(activity.getOrganizer());
        }};
        if (createGroup(group)) {
            Group tarGroup = getGroupByActivityId(activity.getId());
            GroupMember groupMember = new GroupMember() {{
                setGroupId(tarGroup.getGroupId());
                setUserAccount(activity.getOrganizer());
                setRole(0);
            }};
            if (!groupMemberService.addGroupMember(groupMember)) {
                throw new Exception("服务器异常");
            }
            GroupResponse groupResponse = generateGroupResponse(group);
            return groupResponse;
        }else{
            Group existedGroup = getGroupByActivityId(activity.getId());
            if (existedGroup != null) {
                return generateGroupResponse(existedGroup);
            }
        }
        throw new Exception("服务器异常");
    }

    @Override
    public boolean deleteGroup(Integer id) {
        return groupMapper.deleteById(id) > 0;
    }


    public  GroupResponse generateGroupResponse(Group group) {
        List<GroupMember> groupMemberList = groupMemberService.getGroupMemberList(group.getGroupId());
        List<UserInfoResponse> members = userService.getUserInfo(groupMemberList.stream().map(GroupMember::getUserAccount).toList());
        List<GroupMessage> groupMessageList = groupMessageService.getGroupMessageList(group.getGroupId());

        GroupResponse groupResponse = new GroupResponse() {{
            setGroup(group);
            setMembers(members);
            setChat(groupMessageList);
        }};
        return groupResponse;
    }







}
