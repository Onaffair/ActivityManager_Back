package com.example.onaffair.online_chat.util;


import com.example.onaffair.online_chat.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private T data;
    private String msg;


    public static <T> Result<T> success(T data){
        return new Result<>(
                ResultCode.SUCCESS.getCode(),
                data,
                ResultCode.SUCCESS.getMsg()
        );
    }
    public static <T> Result<T> success(ResultCode resultCode,T data){
        return new Result<>(
                resultCode.getCode(),
                data,
                resultCode.getMsg()
        );
    }
    public static <T> Result<T> error(ResultCode resultCode){
        return new Result<>(
                resultCode.getCode(),
                null,
                resultCode.getMsg()
        );
    }
    public static <T> Result<T> error(ResultCode resultCode, String msg){
        return new Result<>(
                resultCode.getCode(),
                null,
                msg
        );
    }
}
