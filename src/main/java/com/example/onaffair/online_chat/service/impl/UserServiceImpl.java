package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.onaffair.online_chat.dto.UserInfoResponse;
import com.example.onaffair.online_chat.dto.UserLoginRequest;
import com.example.onaffair.online_chat.dto.WXLoginDTO;
import com.example.onaffair.online_chat.entity.User;
import com.example.onaffair.online_chat.http_res.WxSessionResponse;
import com.example.onaffair.online_chat.mapper.UserMapper;
import com.example.onaffair.online_chat.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wx.mini-app.appid}")
    private static String APP_ID;
    @Value("${wx.mini-app.secret}")
    private static String SECRET;


    @Override
    public User userWxLogin(String code, WXLoginDTO userinfo) throws RuntimeException {

        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                APP_ID,
                SECRET,
                code
        );

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            WxSessionResponse wxSessionResponse = objectMapper.readValue(response.body(), WxSessionResponse.class);
            String account = wxSessionResponse.getOpenid();
            User user = findByAccount(account);

            if (user == null){
                // 用户不存在，则创建新用户
                User target = new User(){{
                    setAccount(account);
                    setUsername(userinfo.getNickName());
                    setPassword(UUID.randomUUID().toString());
                    setAvatar(userinfo.getAvatarUrl());
                    setStatus("online");
                    setRole(0);
                }};

                if (insertUser(target)){
                    return target;
                }
                return null;
            }
            return user;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
