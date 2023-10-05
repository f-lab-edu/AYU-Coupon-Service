package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundUserCouponException extends BaseCustomException{

    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String message = "요청한 쿠폰이 존재하지 않습니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
