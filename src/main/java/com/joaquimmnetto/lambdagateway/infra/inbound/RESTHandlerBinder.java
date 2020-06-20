package com.joaquimmnetto.lambdagateway.infra.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.http.RESTOperationsFacade;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.UnsuportedHTTPMethodException;

import java.util.function.BiConsumer;

public class RESTHandlerBinder {

    private final MessageHandlerWrapper wrapper;
    private final RESTOperationsFacade restFacade;


    public RESTHandlerBinder(MessageHandlerWrapper wrapper, RESTOperationsFacade restFacade) {
        this.wrapper = wrapper;
        this.restFacade = restFacade;
    }

    public void bind(HTTPEndpoint HTTPEndpoint, MessageHandler handler, Class<?> handlerInboundObjClass) {
        var wrappedRequestHandler = wrapper.wrapForHTTP(handler, handlerInboundObjClass);
        var httpListenerRegisterer = chooseHTTPMethodCallable(HTTPEndpoint.method());
        httpListenerRegisterer.accept(HTTPEndpoint.path(), wrappedRequestHandler);
    }

    public BiConsumer<String, HTTPRequestHandler> chooseHTTPMethodCallable(String method) {
        return switch(method) {
            case "POST" -> restFacade::listenPOST;
            default -> throw new UnsuportedHTTPMethodException();
        };
    }
}
