package org.ace.hcl.orderbillingsystem.orchestratorservice.exception;

import org.ace.hcl.orderbillingsystem.orchestratorservice.filter.AuthenticationFilter;
import org.ace.hcl.orderbillingsystem.orchestratorservice.model.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(RuntimeException e){
        log.info("Runtime exception handler:"+ e.getMessage());
       return ResponseWrapper.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Something went wrong!");

    }

    @ExceptionHandler(InvalidJWTTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(InvalidJWTTokenException e){
        log.info("InvalidJWTTokenException handler:"+ e.getMessage());
        return ResponseWrapper.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Invalid Token");

    }
}
