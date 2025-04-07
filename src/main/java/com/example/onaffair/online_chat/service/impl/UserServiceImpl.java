package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.dto.UserLoginRequest;
import com.example.onaffair.online_chat.entity.User;
import com.example.onaffair.online_chat.mapper.UserMapper;
import com.example.onaffair.online_chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByAccount(String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",account);

        return userMapper.selectOne(queryWrapper);

    }
    @Override
    public User findByAccountAndPassword(UserLoginRequest user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",user.getAccount())
                .eq("user_password",user.getPassword());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean insertUser(User user) {
        int res = userMapper.insert(user);
        return res > 0;
    }

    @Override
    public Boolean updateUser(String account,User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_account",account);

        int res = userMapper.update(user,queryWrapper);

        return res > 0;
    }

    @Override
    public List<UserInfoResponse> userSearch(String keyword) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("user_account",keyword)
                .or()
                .like("user_name",keyword);

        return userMapper.selectList(queryWrapper)
                .stream()
                .map(user -> new UserInfoResponse(
                        user.getAccount(),
                        user.getUsername(),
                        user.getAvatar(),
                        user.getStatus(),
                        user.getRole()
                )).toList() ;
    }


    @Override
    public List<User> getAllUsers() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserInfoResponse> getUserInfo(List<String> accountList) {
        if(accountList == null || accountList.isEmpty()){
            return Collections.emptyList();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("user_account",accountList);


        return userMapper.selectList(queryWrapper)
                .stream()
                .map(user -> new UserInfoResponse(
                        user.getAccount(),
                        user.getUsername(),
                        user.getAvatar(),
                        user.getStatus(),
                        user.getRole()
                )).toList() ;
    }
}
