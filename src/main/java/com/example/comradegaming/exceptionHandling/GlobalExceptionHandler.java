package com.example.comradegaming.exceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<Object> buildResponseEntity(ErrorHandler errorHandler) {
        return new ResponseEntity<>(errorHandler, errorHandler.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(NullPointerException error) {
        logger.info(error.getClass().getName());
        logger.error("Error: ", error);
        String errorMessage = "Looks like the application is referring to something that doesn't exist yet! Ops!";

        return buildResponseEntity(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, error));

    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> customCantFindWhatYouAreLookingFor(CustomException error) {
        logger.info(error.getClass().getName());
        String errorMessage = "Something went wrong!";

        return buildResponseEntity(new ErrorHandler(error.getStatus(), errorMessage, error));
    }

}
