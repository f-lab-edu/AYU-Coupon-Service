package com.ayuconpon.exception;

import org.springframework.http.HttpStatus;


public abstract class BaseCustomException extends RuntimeException {

    public abstract HttpStatus getStatus();

    public abstract String getMessage();

}
