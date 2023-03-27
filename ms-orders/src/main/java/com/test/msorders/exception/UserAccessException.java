package com.test.msorders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAccessException extends RuntimeException{
    public UserAccessException(String message) {
        super(message);
    }
}
