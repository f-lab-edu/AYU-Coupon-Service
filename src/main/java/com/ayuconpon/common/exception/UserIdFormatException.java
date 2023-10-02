package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class UserIdFormatException extends BaseCustomException{

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    private static final String message = "잘못된 형식의 사용자 아이디입니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
