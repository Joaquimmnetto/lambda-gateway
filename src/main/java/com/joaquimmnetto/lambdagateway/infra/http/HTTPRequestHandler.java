package com.joaquimmnetto.lambdagateway.infra.http;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameter;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.DeserializatonException;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;

import java.util.Map;
import java.util.Optional;

public class HTTPRequestHandler {
    private Serializer serializer;
    private MessageHandler innerMessageHandler;
    private Class<?> handlerInboundObjectClass;

    public HTTPRequestHandler(Serializer serializer, MessageHandler innerMessageHandler,
                              Class<?> handlerInboundObjectClass) {
        this.serializer = serializer;
        this.innerMessageHandler = innerMessageHandler;
        this.handlerInboundObjectClass = handlerInboundObjectClass;
    }

    public HTTPResponse handle(HTTPBody httpBody, Map<String, String> headers,
                               Map<String, String> namedParameters, Map<String, String> getParameters) {
        try {
            var httpInbound = new HTTPInboundMessage(serializer.deserialize(httpBody.body(), handlerInboundObjectClass),
                    headers, namedParameters, getParameters);
            Optional<Object> maybeResponse = innerMessageHandler.handle(httpInbound);
            return maybeResponse
                    .map(response -> HTTPResponse.success(serializer.serialize(response)))
                    .orElse(HTTPResponse.success());
        } catch (InvalidParameter | DeserializatonException e) {
            return HTTPResponse.requestError(e);
        } catch (Exception e) {
            return HTTPResponse.serverError(e);
        }
    }
}
