package com.example.onaffair.online_chat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WXLoginDTO {

    private String avatarUrl;
    private String city;
    private String country;
    private Integer gender;
    private String nickName;
    private String province;

}
