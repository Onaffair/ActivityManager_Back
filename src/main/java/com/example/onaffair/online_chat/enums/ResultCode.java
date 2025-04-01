package com.example.onaffair.online_chat.enums;

import lombok.Getter;


@Getter
public enum ResultCode {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    ERROR(500, "Internal Server Error"),
    VALIDATION_ERROR(400, "Validation Failed"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found");

    private final Integer code;
    private final String msg;

     ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
