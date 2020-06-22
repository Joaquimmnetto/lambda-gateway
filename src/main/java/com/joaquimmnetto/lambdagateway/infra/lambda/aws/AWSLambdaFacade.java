package com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameterException;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.ResourceNotFoundException;


public class AWSLambdaFacade {

    private final LambdaClient lambdaClient;

    public AWSLambdaFacade() {
        this.lambdaClient = LambdaClient.create();
    }


    public String invoke(String lambdaName, String payload) {
        try {
            InvokeRequest.Builder builder = InvokeRequest.builder().functionName(lambdaName);
            if (payload != null) {
                SdkBytes payloadBytes = SdkBytes.fromUtf8String(payload);
                builder = builder.payload(payloadBytes);
            }
            InvokeResponse invokeResponse = lambdaClient.invoke(builder.build());
            return invokeResponse.payload().asUtf8String();
        } catch(ResourceNotFoundException e) {
            throw new InvalidParameterException(String.format("Lambda %s not found", lambdaName), e);

        }
    }
}
