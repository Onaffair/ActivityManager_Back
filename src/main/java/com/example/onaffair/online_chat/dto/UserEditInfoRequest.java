package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditInfoRequest {
    private String avatar;
    private String username;
    private String email;
    private String phone;
}
