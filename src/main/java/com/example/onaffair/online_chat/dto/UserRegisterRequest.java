package com.example.onaffair.online_chat.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegisterRequest {
    @NotBlank(message = "账号不能为空")
    private String account;
    
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 3, max = 255, message = "密码长度在3-255之间")
    private String password;
    
    private String email;
    private String phone;
    private String avatar;
}
