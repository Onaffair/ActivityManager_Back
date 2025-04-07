package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.GroupMember;

import java.util.List;

public interface GroupMemberService {

    boolean addGroupMember(GroupMember groupMember);

    boolean removeGroupMember(GroupMember groupMember);

    List<GroupMember> getGroupMemberList(Integer groupId);

    List<GroupMember> getGroupMemberListByAccount(String account);

    boolean deleteGroupMemberByGroupId(Integer groupId);

}
