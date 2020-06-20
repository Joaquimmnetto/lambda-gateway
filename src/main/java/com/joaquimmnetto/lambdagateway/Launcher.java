package com.joaquimmnetto.lambdagateway;

import com.joaquimmnetto.lambdagateway.ioc.RESTDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.LambdaHandlerDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.ToolsDependencyModule;
import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;

import java.util.Map;

import static com.joaquimmnetto.lambdagateway.ServiceBuilder.service;

public class Launcher {

    public static void main(String[] args) {
        service(IoCInjector::createGuice)
            .withDependencies(new ToolsDependencyModule())
            .withDependencies(new RESTDependencyModule())
            .withDependencies(new LambdaHandlerDependencyModule())
            .withRESTBinding("POST", "/invoke/:lambda_name", LambdaMessageHandler.class, Map.class)
            .launch();
    }

}
