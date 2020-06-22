package com.joaquimmnetto.lambdagateway;

import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;
import com.joaquimmnetto.lambdagateway.ioc.RESTDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.LambdaHandlerDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.ToolsDependencyModule;
import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;
import com.joaquimmnetto.lambdagateway.launch.ServiceBuilder;

import java.util.Map;

import static java.util.Arrays.asList;


public class Launcher {

    public static void main(String[] args) {
        Launcher.launchWithBinder();
    }

    public static void launchWithIoCInjector() {
        IoCInjector injector = IoCInjector.createGuice(asList(new ToolsDependencyModule(),
                new RESTDependencyModule(), new LambdaHandlerDependencyModule()));
        ServiceBuilder.service()
            .usingIoCInjector(injector)
            .withRESTBinding("POST", "/invoke/:lambda_name", LambdaMessageHandler.class, Map.class)
            .launch();
    }

    public static void launchWithBinder() {
        RESTHandlerBinder binder = RESTHandlerBinder.sparkJavaBinder();
        ServiceBuilder.service()
                .withRESTHandlerBinder(binder)
                .withRESTBinding("POST", "/invoke/:lambda_name", LambdaMessageHandler.forAWS(), Map.class)
                .launch();
    }

}
