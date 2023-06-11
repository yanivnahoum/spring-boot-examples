package com.att.training.springboot.examples.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException ex) {
        log.error("#handleUserNotFound - an error occurred", ex);
    }


    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public void handleGenericException(Exception ex) {
        log.error("#handleGenericException - an error occurred", ex);
    }
}
