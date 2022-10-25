package com.example.demo.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value= HttpStatus.CONFLICT, reason="An Internal Error ocurred")
    @ExceptionHandler(ResourceAccessException.class)
    public void handleException() {
        System.out.println("Test");
    }
}
