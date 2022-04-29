package com.example.comradegaming.exceptionHandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorHandler {

    private HttpStatus status;
    private String errorMessage;
    private LocalDateTime timeStamp;

    public ErrorHandler() {
    }

    public ErrorHandler(HttpStatus status, String errorMessage) {
        this();
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
