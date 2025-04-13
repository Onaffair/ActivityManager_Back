package com.example.onaffair.online_chat.controller;

import com.example.onaffair.online_chat.dto.GroupResponse;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.entity.*;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.example.onaffair.online_chat.service.*;

import com.example.onaffair.online_chat.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")


public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ActivityCommentService activityCommentService;

    @GetMapping("/test")
    public String test(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前用户权限: " + auth.getAuthorities());
        return "admin test";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("/updateUser")
    private Boolean updateUser(@RequestBody  User user){
        return userService.updateUser(user.getAccount(),user);
    }

    @GetMapping("/getAllActivities")
    public List<Activity> getAllActivities(){
        return activityService.getAllActivities();
    }
    @PostMapping("/updateActivity")
    public Boolean updateActivity(@RequestBody  Activity activity){
        return activityService.updateActivity(activity);
    }


    @GetMapping("/getAllGroups")
    public List<GroupResponse> getAllGroups(){
        try {
            List<Group> allGroups = groupService.getAllGroups();
            List<GroupResponse> groupResponses = new ArrayList<>();

            allGroups.forEach(group ->{
                List<GroupMember> groupMemberList = groupMemberService.getGroupMemberList(group.getGroupId());
                GroupResponse groupResponse = new GroupResponse() {{
                    setGroup(group);
                    List<String> userList = groupMemberService.getGroupMemberList(group.getGroupId()).stream().map(GroupMember::getUserAccount).toList();
                    List<UserInfoResponse> userInfo = userService.getUserInfo(userList);
                    setMembers(userInfo);
                }};
                groupResponses.add(groupResponse);
            });
            return groupResponses;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    @DeleteMapping("/delete-group")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteGroup(@RequestParam("groupId") Integer groupId) {
        try {
            Group group = groupService.getGroupById(groupId);
            if (group == null) {
                throw new RuntimeException("Group not found");
            }

            Integer activityId = group.getActivityId();

            if (!groupMemberService.deleteGroupMemberByGroupId(groupId)) {
                throw new RuntimeException("Failed to delete group members");
            }

            if (!groupMessageService.deleteGroupMessageByGroupId(groupId)) {
                throw new RuntimeException("Failed to delete group messages");
            }

            if (!activityCommentService.deleteCommentByActivityID(activityId)) {
                throw new RuntimeException("Failed to delete activity comments");
            }

            if (!activityService.deleteActivityParticipationByActivityId(activityId)) {
                throw new RuntimeException("Failed to delete activity participation");
            }

            if (!groupService.deleteGroup(groupId)) {
                throw new RuntimeException("Failed to delete group");
            }

            if (!activityService.deleteActivity(activityId)) {
                throw new RuntimeException("Failed to delete activity");
            }

            return Result.success(true);
        } catch (Exception e) {
            // Transaction will automatically rollback due to @Transactional
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/delete-group-member")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteGroupMember(@RequestParam("groupId") Integer groupId, 
                                           @RequestParam("userAccount") String userAccount) {
        try {
            Group group = groupService.getGroupById(groupId);
            if (group == null) {
                throw new RuntimeException("群组不存在");
            }

            // 从群组中移除成员
            GroupMember groupMember = new GroupMember() {{
                setGroupId(groupId);
                setUserAccount(userAccount);
            }};
            
            if (!groupMemberService.removeGroupMember(groupMember)) {
                throw new RuntimeException("移除群组成员失败");
            }

            // 取消该成员对相关活动的报名
            UserParticipation userParticipation = new UserParticipation() {{
                setActivityId(group.getActivityId());
                setUserAccount(userAccount);
            }};
            
            if (!activityService.cancelActivityJoin(userParticipation)) {
                throw new RuntimeException("取消活动报名失败");
            }

            return Result.success(true);
        } catch (Exception e) {
            return Result.error(ResultCode.ERROR, e.getMessage());
        }
    }

}
