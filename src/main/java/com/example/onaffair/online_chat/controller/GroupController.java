package com.example.onaffair.online_chat.controller;


import com.example.onaffair.online_chat.dto.GroupResponse;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.entity.Activity;
import com.example.onaffair.online_chat.entity.Group;
import com.example.onaffair.online_chat.entity.GroupMember;
import com.example.onaffair.online_chat.entity.GroupMessage;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.GroupMemberService;
import com.example.onaffair.online_chat.service.GroupMessageService;
import com.example.onaffair.online_chat.service.GroupService;
import com.example.onaffair.online_chat.service.UserService;
import com.example.onaffair.online_chat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-group")
    public Result<GroupResponse> createGroup(@RequestBody Activity activity) {
        try {
            Group group = new Group() {{
                setActivityId(activity.getId());
                setGroupName(activity.getTitle() + "活动群");
                setOwnerAccount(activity.getOrganizer());
            }};
            if (groupService.createGroup(group)) {
                Group tarGroup = groupService.getGroupByActivityId(activity.getId());
                GroupMember groupMember = new GroupMember() {{
                    setGroupId(tarGroup.getGroupId());
                    setUserAccount(activity.getOrganizer());
                    setRole(0);
                }};
                if (!groupMemberService.addGroupMember(groupMember)) {
                    return Result.error(ResultCode.ERROR, "服务器异常");
                }
                GroupResponse groupResponse = generateGroupResponse(group);
                return Result.success(ResultCode.SUCCESS, groupResponse);
            }
            return Result.error(ResultCode.ERROR, "服务器异常");
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器异常");
        }
    }

    @PostMapping("/join-group")
    public Result<GroupResponse> joinGroup(@RequestBody Integer activityId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            Group group = groupService.getGroupByActivityId(activityId);
            GroupMember groupMember = new GroupMember() {{
                setGroupId(group.getGroupId());
                setUserAccount(account);
            }};
            if (!groupMemberService.addGroupMember(groupMember)) {
                return Result.error(ResultCode.BAD_REQUEST, "加入失败");
            }
            GroupResponse groupResponse = generateGroupResponse(group);
            return Result.success(ResultCode.SUCCESS, groupResponse);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, "服务器异常");
        }
    }
    @GetMapping("/my-group")
    public Result<List<GroupResponse>> myGroup() {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            List<GroupMember> groupMemberListByAccount = groupMemberService.getGroupMemberListByAccount(account);

            if (groupMemberListByAccount == null || groupMemberListByAccount.isEmpty()){
                return Result.success(ResultCode.SUCCESS, Collections.emptyList());
            }

            List<Group> groupList = groupService.getGroupList(groupMemberListByAccount.stream().map(GroupMember::getGroupId).toList());

            List<GroupResponse> groupResponses = new ArrayList<>();
            groupList.forEach(item ->{
                groupResponses.add(generateGroupResponse(item));
            });
            return Result.success(ResultCode.SUCCESS, groupResponses);
        }catch (Exception e){
            return Result.error(ResultCode.ERROR, "服务器异常");
        }
    }
    @PostMapping("/quit-group")
    public Result<String> quitGroup(@RequestBody Integer activityId) {
        try {
            String account = SecurityContextHolder.getContext().getAuthentication().getName();
            Group group = groupService.getGroupByActivityId(activityId);
            if (!groupMemberService.removeGroupMember(new GroupMember(){{
                setGroupId(group.getGroupId());
                setUserAccount(account);
            }})){
                return Result.error(ResultCode.BAD_REQUEST, "退出失败");
            }
            return Result.success(ResultCode.SUCCESS, "退出成功");
        }catch (Exception e){
            return Result.error(ResultCode.ERROR, "服务器异常");
        }
    }

    @PostMapping("/update-group")
    public Result<Group> updateGroup(@RequestBody Group group) {
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!group.getOwnerAccount().equals(account)){
            return Result.error(ResultCode.FORBIDDEN, "权限不足");
        }
        try {
            if (groupService.updateGroup(group)){
                return Result.success(ResultCode.SUCCESS, group);
            }
            return Result.error(ResultCode.ERROR, "服务器异常");
        }catch (Exception e){
            return Result.error(ResultCode.ERROR, "服务器异常");
        }
    }

    public GroupResponse generateGroupResponse(Group group) {
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
