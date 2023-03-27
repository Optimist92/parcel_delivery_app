package com.test.msidentities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
