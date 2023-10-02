package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class RequireRegistrationException extends BaseCustomException{

    private static final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static final String message = "회원 가입이 필요한 사용자입니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
