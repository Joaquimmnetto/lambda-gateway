package com.joaquimmnetto.lambdagateway.ioc;

import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaFacade;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaRegistry;
import com.joaquimmnetto.lambdagateway.service.LambdaRegistry;
import com.joaquimmnetto.lambdagateway.service.LambdaService;

public class LambdaHandlerDependencyModule extends DependencyModule {

    public LambdaHandlerDependencyModule() {
        bindToSelf(AWSLambdaFacade.class);
        bind(LambdaRegistry.class, AWSLambdaRegistry.class);
        bindToSelf(LambdaService.class);
        bindToSelf(LambdaMessageHandler.class);

    }
}
