package com.ayuconpon.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedCouponException extends BaseCustomException {

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    private static final String message = "쿠폰이 이미 발급되었습니다.";

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
