package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.Group;

import java.util.List;

public interface GroupService {

    boolean createGroup(Group group);

    List<Group> getGroupList(List<Integer> groupIdList);

    Group getGroupByActivityId(Integer id);

    Group getGroupById(Integer id);

    boolean updateGroup(Group group);

    boolean deleteGroup(Integer id);

    List<Group> getAllGroups();

}
