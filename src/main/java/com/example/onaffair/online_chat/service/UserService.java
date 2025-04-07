package com.example.onaffair.online_chat.service;

import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.dto.UserLoginRequest;
import com.example.onaffair.online_chat.entity.User;

import java.util.List;


public interface UserService {

    User findByAccount(String account);

    User findByAccountAndPassword(UserLoginRequest user);

    Boolean insertUser(User user);

    Boolean updateUser(String account,User user);

    List<UserInfoResponse> userSearch(String keyword);

    List<UserInfoResponse> getUserInfo (List<String> accountList);

    List<User> getAllUsers();



}
