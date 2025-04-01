package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class UserInfoResponse {
    private String account;
    private String name;
    private String avatar;
    private String status;
    private Integer role;
}
