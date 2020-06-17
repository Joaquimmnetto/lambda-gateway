package com.joaquimmnetto.lambdagateway.infra.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;

public class RequestHandlerWrapper {

    private final Serializer serializer;

    public RequestHandlerWrapper(Serializer serializer) {
        this.serializer = serializer;
    }

    public HTTPRequestHandler wrapForHTTP(MessageHandler messageHandler, Class<?> handlerInboundObjClass) {
        return new HTTPRequestHandler(serializer, messageHandler, handlerInboundObjClass);
    }
}
