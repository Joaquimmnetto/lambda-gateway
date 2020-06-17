package com.joaquimmnetto.lambdagateway.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;

public class HandlerData<T> {
    private Class<? extends MessageHandler> handlerClass;
    private Class<T> requestClass;

    public HandlerData(Class<? extends MessageHandler> handlerClass, Class<T> requestClass) {
        this.handlerClass = handlerClass;
        this.requestClass = requestClass;
    }

    public Class<? extends MessageHandler> handlerClass() {
        return handlerClass;
    }

    public Class<T> requestClass() {
        return requestClass;
    }
}
