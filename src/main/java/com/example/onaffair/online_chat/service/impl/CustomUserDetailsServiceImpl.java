package com.example.onaffair.online_chat.service.impl;

import com.example.onaffair.online_chat.entity.User;
import com.example.onaffair.online_chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService; // 注入 UserMapper


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 通过 UserMapper 查询用户信息
        User user = userService.findByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + account);
        }

        // 将 User 对象转换为 Spring Security 的 UserDetails 对象
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getAccount())
                .password(user.getPassword())
                .roles("USER") // 可以根据需要设置用户角色
                .build();
    }
}