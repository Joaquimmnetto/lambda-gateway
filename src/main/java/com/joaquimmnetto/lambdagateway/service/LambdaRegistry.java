package com.joaquimmnetto.lambdagateway.service;

import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaFacade;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaRegistry;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;

public interface LambdaRegistry {

    static LambdaRegistry awsRegistry() {
        return new AWSLambdaRegistry(Serializer.instance(), new AWSLambdaFacade());
    }

    Lambda get(LambdaIdentifier lambdaIdentifier);
}
