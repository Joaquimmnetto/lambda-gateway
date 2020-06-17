package com.joaquimmnetto.lambdagateway.fixture;

import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.model.LambdaPayload;

import static org.mockito.Mockito.mock;

public class LambdaFixture {

    public static LambdaPayload payloadFor(Object lambdaMessage) {
        return new LambdaPayload(lambdaMessage);
    }

    public static LambdaIdentifier identifierFor(String lambdaName) {
        return new LambdaIdentifier(lambdaName);
    }

    public static LambdaIdentifier aLambdaIdentifier(){
        return identifierFor("name");
    }

    public static LambdaPayload aLambdaPayload() {
        return payloadFor(mock(Object.class));
    }

}
