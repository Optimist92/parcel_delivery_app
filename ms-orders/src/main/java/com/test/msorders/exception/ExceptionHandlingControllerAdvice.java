package com.test.msorders.exception;

import exception.EntityNotFoundException;
import exception.ViolationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ViolationResponse onNotReadableException(HttpMessageNotReadableException e) {
        if (e.getMessage().contains("Required request body is missing")) {
            return new ViolationResponse(LocalDateTime.now(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), "Отсутствует требуемый текст запроса");
        }
        return new ViolationResponse(LocalDateTime.now(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getLocalizedMessage());
    }

    @ExceptionHandler(UserAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ViolationResponse onNotFoundException(UserAccessException e) {
        return new ViolationResponse(LocalDateTime.now(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getLocalizedMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ViolationResponse onNotFoundException(EntityNotFoundException e) {
        return new ViolationResponse(LocalDateTime.now(), String.valueOf(HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND.getReasonPhrase(), e.getLocalizedMessage());
    }

    @ExceptionHandler(ChangeStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ViolationResponse onChangeStatusException(ChangeStatusException e) {
        return new ViolationResponse(LocalDateTime.now(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getLocalizedMessage());
    }

}
