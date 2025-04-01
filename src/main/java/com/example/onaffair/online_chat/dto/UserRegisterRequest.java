package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegisterRequest {
    private String account;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
}
