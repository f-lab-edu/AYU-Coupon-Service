package com.ayuconpon.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseCustomException {

    public UnauthorizedException() {
        status = HttpStatus.UNAUTHORIZED;
        message = "인증되지 않은 사용자입니다.";
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
