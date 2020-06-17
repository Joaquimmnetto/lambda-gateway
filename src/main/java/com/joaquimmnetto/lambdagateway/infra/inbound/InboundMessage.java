package com.joaquimmnetto.lambdagateway.infra.inbound;

import java.util.Map;
import java.util.Optional;

public interface InboundMessage {

    <T> T content();

    Optional<String> namedParam(String paramName);

    Map<String, String> headers();

    Map<String, String> namedParams();
}
