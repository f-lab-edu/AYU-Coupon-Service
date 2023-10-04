package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class ExpiredUserCouponException extends BaseCustomException{

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String message = "만료된 쿠폰입니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
