package com.ayuconpon.common.handler;

import com.ayuconpon.common.exception.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ayuconpon")
public class CouponExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handle(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    
    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<String> handle(BaseCustomException e) {
        return ResponseEntity.status(e.getStatus())
                .body(e.getMessage());
    }

}
