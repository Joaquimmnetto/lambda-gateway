package com.joaquimmnetto.lambdagateway.infra.handler;

import com.joaquimmnetto.lambdagateway.infra.inbound.InboundMessage;

import java.util.Optional;

public interface MessageHandler {

    Optional<Object> handle(InboundMessage inboundMessage);

}
