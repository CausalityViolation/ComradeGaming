package com.example.comradegaming.exceptionHandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorHandler {

    private HttpStatus status;
    private String errorMessage;
    private String details;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    public ErrorHandler() {
    }

    public ErrorHandler(HttpStatus status, String errorMessage, Exception e) {
        this();
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.errorMessage = errorMessage;
        this.details = e.getMessage();
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

    public String getDetails() {
        return details;
    }
}
