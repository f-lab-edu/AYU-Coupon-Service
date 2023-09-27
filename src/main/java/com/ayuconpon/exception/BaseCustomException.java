package com.ayuconpon.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseCustomException extends Exception {

    protected HttpStatus status;
    protected String message;

    public abstract HttpStatus getStatus();

    public abstract String getMessage();

}
