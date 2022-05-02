package com.example.comradegaming.exceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Kanske ändra namn senare så det känns liiiite mindre kopierat...
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ResponseEntity<Object> buildResponseEntity(ErrorHandler errorHandler) {
        return new ResponseEntity<>(errorHandler, errorHandler.getStatus());
    }

    /*
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(NullPointerException error) {
        logger.info(error.getClass().getName());
        logger.error("Error: ", error);
        String errorMessage = "Looks like the application is referring to something that doesn't exist yet! Ops!";

        return buildResponseEntity(new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage));

    }


     */

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> customCantFindWhatYouAreLookingFor(CustomException error) {
        logger.info(error.getClass().getName());
        String errorMessage = "You're probably trying to access something that doesn't exist yet! OPS!";

        return buildResponseEntity(new ErrorHandler(HttpStatus.NOT_FOUND, errorMessage));
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> customNullPointerException(CustomException error) {
        logger.info(error.getClass().getName());
        String errorMessage = "Looks like something went wrong! Null pointer exception :(";

        return buildResponseEntity(new ErrorHandler(HttpStatus.NOT_FOUND, errorMessage));
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> HMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM(CustomException error) {
        logger.info(error.getClass().getName());
        String errorMessage = "Looks like something went wrong! Null pointer exception :(";

        return buildResponseEntity(new ErrorHandler(HttpStatus.NOT_FOUND, errorMessage));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(e.getClass().getName());
        String errorMessage = "Unacceptable JSON-format.";
        return buildResponseEntity(new ErrorHandler(HttpStatus.NOT_ACCEPTABLE, errorMessage));
    }

}
