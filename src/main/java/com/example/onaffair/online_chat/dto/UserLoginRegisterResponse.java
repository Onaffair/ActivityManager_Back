package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserLoginRegisterResponse {
    private  String token;
    private  String username;
    private  String avatar;
    private  String status;
    private  String account;
    private  String email;
    private  String phone;
    private  Integer role;
}
