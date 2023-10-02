package com.ayuconpon.handler;

import com.ayuconpon.common.ApiResponse;
import com.ayuconpon.exception.BaseCustomException;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> handle(IllegalArgumentException e) {
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<String> handle(IllegalStateException e) {
        return ApiResponse.error(e.getMessage());
    }
    
    @ExceptionHandler(BaseCustomException.class)
    public ApiResponse<String> handle(BaseCustomException e) {
        return ApiResponse.error(e);
    }

}
