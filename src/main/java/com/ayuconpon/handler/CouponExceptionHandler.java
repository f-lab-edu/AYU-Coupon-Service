package com.ayuconpon.handler;

import com.ayuconpon.common.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ayuconpon")
public class CouponExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handle(MethodArgumentNotValidException e) {
        return ApiResponse.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ApiResponse<String> handle(MissingRequestCookieException e) {
        return ApiResponse.error(e.getMessage());
    }

}
