package com.att.training.springboot.examples.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ErrorDto handleProductNotFound(ProductNotFoundException ex) {
        log.error("#handleProductNotFound - An error occurred", ex);
        String message = "Product not found: " + ex.getMessage();
        return new ErrorDto(ErrorCode.NOT_FOUND, message);
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorDto handleGenericException(Exception ex) {
        log.error("#handleGenericException - An error occurred", ex);
        return new ErrorDto(ErrorCode.GENERIC, ex.getMessage());
    }

    // ...

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body, @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        log.error("#handleExceptionInternal - ", ex);
        ErrorDto errorDto = new ErrorDto(ErrorCode.GENERIC, ex.getMessage());
        return new ResponseEntity<>(errorDto, statusCode);
    }
}



