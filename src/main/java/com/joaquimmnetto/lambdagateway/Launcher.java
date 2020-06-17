package com.joaquimmnetto.lambdagateway;

import com.joaquimmnetto.lambdagateway.beans.SomeDependencies;
import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;

import java.util.Map;

import static com.joaquimmnetto.lambdagateway.ServiceBuilder.service;

public class Launcher {

    public static void main(String[] args) {
        service()
            .withDependencies(new SomeDependencies())
            .withRESTBinding("POST", "/invoke/:lambda_name", LambdaMessageHandler.class, Map.class)
            .launch();
    }

}
