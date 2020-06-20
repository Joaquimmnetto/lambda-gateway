package com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.model.LambdaPayload;

import java.util.Map;
import java.util.Optional;

public class AWSLambda implements Lambda {

    private final Serializer serializer;
    private final LambdaIdentifier identifier;
    private final AWSLambdaFacade facade;

    public AWSLambda(Serializer serializer, LambdaIdentifier identifier, AWSLambdaFacade facade) {
        this.serializer = serializer;
        this.identifier = identifier;
        this.facade = facade;
    }

    @Override
    public Optional<Object> call(LambdaPayload lambdaPayload) {
        String rawPayload = lambdaPayload.content().map(serializer::serialize).orElse("");
        String rawResponse = facade.invoke(identifier.lambdaName(), rawPayload);
        if(!rawResponse.isEmpty()) {
            return Optional.of(serializer.deserialize(rawResponse, Map.class));
        } else {
            return Optional.empty();
        }
    }
}
