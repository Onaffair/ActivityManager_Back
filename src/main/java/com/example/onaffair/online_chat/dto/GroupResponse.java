package com.example.onaffair.online_chat.dto;


import com.example.onaffair.online_chat.entity.Group;
import com.example.onaffair.online_chat.entity.GroupMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {

    Group group;
    List<UserInfoResponse> members;
    List<GroupMessage> chat;
}
