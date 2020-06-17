package com.joaquimmnetto.lambdagateway.infra.http;

import com.joaquimmnetto.lambdagateway.infra.inbound.InboundMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HTTPInboundMessage implements InboundMessage {

    private Object bodyObj;
    private Map<String, String> headers;
    private Map<String, String> namedParameters;

    public HTTPInboundMessage(Object bodyObj,
                              Map<String, String> headers,
                              Map<String, String> namedParameters, Map<String, String> getParameters) {

        this.bodyObj = bodyObj;
        this.headers = Collections.unmodifiableMap(headers);
        var mergedNamedParameters = new HashMap<>(namedParameters);
        mergedNamedParameters.putAll(getParameters);
        this.namedParameters = Collections.unmodifiableMap(mergedNamedParameters);
    }

    @Override
    public <T> T content() {
        return (T) bodyObj;
    }

    @Override
    public Optional<String> namedParam(String paramName) {
        return Optional.ofNullable(namedParameters.getOrDefault(paramName, null));
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Map<String, String> namedParams() {
        return namedParameters;
    }
}
