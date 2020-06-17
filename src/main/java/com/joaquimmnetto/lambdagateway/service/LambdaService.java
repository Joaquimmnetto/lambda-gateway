package com.joaquimmnetto.lambdagateway.service;

import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.model.LambdaPayload;

import java.util.Optional;

public class LambdaService {

    private final LambdaRegistry lambdaRegistry;

    public LambdaService(LambdaRegistry lambdaRegistry) {
        this.lambdaRegistry = lambdaRegistry;
    }

    public Optional<Object> callLambda(LambdaIdentifier lambdaIdentifier, LambdaPayload lambdaPayload) {
        Lambda lambda = lambdaRegistry.get(lambdaIdentifier);
        return lambda.call(lambdaPayload);
    }
}
