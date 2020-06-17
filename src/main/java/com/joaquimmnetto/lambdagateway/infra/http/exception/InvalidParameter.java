package com.joaquimmnetto.lambdagateway.infra.http.exception;

public class InvalidParameter extends RuntimeException {


    public InvalidParameter(String exceptionMessage) {
        super(exceptionMessage);
    }
}
