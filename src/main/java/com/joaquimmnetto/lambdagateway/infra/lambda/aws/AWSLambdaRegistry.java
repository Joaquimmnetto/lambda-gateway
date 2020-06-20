package com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.service.LambdaRegistry;

public class AWSLambdaRegistry implements LambdaRegistry {

    private final Serializer serializer;
    private final AWSLambdaFacade facade;

    public AWSLambdaRegistry(Serializer serializer, AWSLambdaFacade facade) {
        this.serializer = serializer;
        this.facade = facade;
    }

    @Override
    public Lambda get(LambdaIdentifier lambdaIdentifier) {
        return new AWSLambda(serializer, lambdaIdentifier, facade);
    }

}
