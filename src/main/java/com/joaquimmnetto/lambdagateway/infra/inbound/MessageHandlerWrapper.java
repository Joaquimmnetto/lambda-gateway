package com.joaquimmnetto.lambdagateway.infra.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;

public class MessageHandlerWrapper {

    private final Serializer serializer;

    public MessageHandlerWrapper(Serializer serializer) {
        this.serializer = serializer;
    }

    public static MessageHandlerWrapper instance() {
        return new MessageHandlerWrapper(Serializer.instance());
    }

    public HTTPRequestHandler wrapForHTTP(MessageHandler messageHandler, Class<?> handlerInboundObjClass) {
        return new HTTPRequestHandler(serializer, messageHandler, handlerInboundObjClass);
    }
}
