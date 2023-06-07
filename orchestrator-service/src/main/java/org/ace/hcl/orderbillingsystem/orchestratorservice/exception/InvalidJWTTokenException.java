package org.ace.hcl.orderbillingsystem.orchestratorservice.exception;


import lombok.AllArgsConstructor;


public class InvalidJWTTokenException extends RuntimeException {
    String message;
    public InvalidJWTTokenException(String message){
        super(message);
        this.message=message;
    }

}
