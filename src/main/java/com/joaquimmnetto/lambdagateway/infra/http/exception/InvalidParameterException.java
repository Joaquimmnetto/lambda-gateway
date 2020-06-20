package com.joaquimmnetto.lambdagateway.infra.http.exception;

public class InvalidParameterException extends RuntimeException {


    public InvalidParameterException(String exceptionMessage) {
        super(exceptionMessage);
    }
    public InvalidParameterException(String exceptionMessage, Exception e) {
        super(exceptionMessage, e);
    }
}
