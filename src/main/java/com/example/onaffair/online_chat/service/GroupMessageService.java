package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.entity.GroupMessage;

import java.util.List;

public interface GroupMessageService {

    boolean addGroupMessage(GroupMessage groupMessage);

    List<GroupMessage> getGroupMessageList(Integer groupId);

    boolean deleteGroupMessageByGroupId(Integer groupId);

}
