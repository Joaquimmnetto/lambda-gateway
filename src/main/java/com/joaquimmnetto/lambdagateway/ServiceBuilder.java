package com.joaquimmnetto.lambdagateway;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.joaquimmnetto.lambdagateway.inbound.HandlerData;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;
import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceBuilder {

    private final List<AbstractModule> dependencyGuiceModules;
    private Map<HTTPEndpoint, HandlerData<?>> handlerRESTBindings;

    public static ServiceBuilder service() {
        return new ServiceBuilder();
    }

    private ServiceBuilder() {
        this.dependencyGuiceModules = new ArrayList<>();
        this.handlerRESTBindings = new HashMap<>();
    }

    public <T> ServiceBuilder withDependencies(AbstractModule guiceModule) {
        dependencyGuiceModules.add(guiceModule);
        return this;
    }

    public <T> ServiceBuilder withRESTBinding(String method, String path, Class<? extends MessageHandler> handlerClass,
                                                                    Class<T> requestClass) {
        handlerRESTBindings.put(new HTTPEndpoint(method, path), new HandlerData<>(handlerClass, requestClass));
        return this;
    }

    public void launch() {
        var injector = Guice.createInjector(dependencyGuiceModules);
        var restHandlerBinder = injector.getInstance(RESTHandlerBinder.class);
        handlerRESTBindings.forEach((endpoint, handlerData) ->
                    restHandlerBinder.bind(endpoint,
                            injector.getInstance(handlerData.handlerClass()),
                            handlerData.requestClass())
        );
    }
}
