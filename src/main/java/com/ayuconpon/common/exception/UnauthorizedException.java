package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseCustomException {

    private static final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static final String message = "인증되지 않은 사용자입니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
