package com.example.comradegaming.exceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Kanske ändra namn senare så det känns liiiite mindre kopierat...
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<Object> buildResponseEntity(ErrorHandler errorHandler) {
        return new ResponseEntity<>(errorHandler, errorHandler.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception error) {
        logger.info(error.getClass().getName());
        logger.error("Error: ", error);
        String errorMessage = "Something went wrong, lol";

        return buildResponseEntity(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage));

    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> customException(CustomException error) {
        logger.info(error.getClass().getName());
        String errorMessage = "Custom exception happened lel";

        return buildResponseEntity(new ErrorHandler(HttpStatus.I_AM_A_TEAPOT, errorMessage));
    }

}
