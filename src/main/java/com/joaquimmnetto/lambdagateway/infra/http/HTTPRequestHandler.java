package com.joaquimmnetto.lambdagateway.infra.http;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameterException;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.DeserializatonException;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class HTTPRequestHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Serializer serializer;
    private final MessageHandler innerMessageHandler;
    private final Class<?> handlerInboundObjectClass;

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
        } catch (InvalidParameterException | DeserializatonException e) {
            logger.warn("Invalid request -> {}: {}", e.getClass().getSimpleName(), e.getMessage());
            return HTTPResponse.requestError(e);
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return HTTPResponse.serverError(e);
        }
    }
}
