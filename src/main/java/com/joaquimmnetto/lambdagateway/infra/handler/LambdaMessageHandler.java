package com.joaquimmnetto.lambdagateway.infra.handler;

import com.joaquimmnetto.lambdagateway.infra.inbound.InboundMessage;
import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameterException;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.model.LambdaPayload;
import com.joaquimmnetto.lambdagateway.service.LambdaService;

import java.util.Optional;

public class LambdaMessageHandler implements MessageHandler {

    private final LambdaService lambdaService;

    public LambdaMessageHandler(LambdaService lambdaService) {
        this.lambdaService = lambdaService;
    }

    @Override
    public Optional<Object> handle(InboundMessage inboundMessage) {
        Optional<String> maybeLambdaName = inboundMessage.namedParam(":lambda_name");
        return maybeLambdaName
                .map(lambdaName -> callLambda(lambdaName, inboundMessage.content()))
                .orElseThrow(() -> new InvalidParameterException("Target lambda name not found"));
    }

    public Optional<Object> callLambda(String lambdaName, Object payload) {
        return lambdaService.callLambda(
                new LambdaIdentifier(lambdaName), new LambdaPayload(payload));
    }

}
