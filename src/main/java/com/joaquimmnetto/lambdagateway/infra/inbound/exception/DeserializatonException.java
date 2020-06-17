package com.joaquimmnetto.lambdagateway.infra.inbound.exception;

public class DeserializatonException extends RuntimeException {

    public DeserializatonException(Exception root) {
        super(root);
    }

}
