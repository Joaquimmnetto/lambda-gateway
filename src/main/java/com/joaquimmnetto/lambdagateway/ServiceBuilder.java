package com.joaquimmnetto.lambdagateway;

import com.joaquimmnetto.lambdagateway.inbound.HandlerData;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;
import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServiceBuilder {

    private final Function<List<DependencyModule>, IoCInjector> iocInjectorFactory;
    private final List<DependencyModule> dependencyModules;
    private Map<HTTPEndpoint, HandlerData<?>> handlerRESTBindings;

    public static ServiceBuilder service(Function<List<DependencyModule>, IoCInjector> iocInjectorFactory) {
        return new ServiceBuilder(iocInjectorFactory);
    }

    private ServiceBuilder(Function<List<DependencyModule>, IoCInjector> iocInjectorFactory) {
        this.iocInjectorFactory = iocInjectorFactory;
        this.dependencyModules = new ArrayList<>();
        this.handlerRESTBindings = new HashMap<>();
    }

    public <T> ServiceBuilder withDependencies(DependencyModule module) {
        dependencyModules.add(module);
        return this;
    }

    public <T> ServiceBuilder withRESTBinding(String method, String path, Class<? extends MessageHandler> handlerClass,
                                                                    Class<T> requestClass) {
        handlerRESTBindings.put(new HTTPEndpoint(method, path), new HandlerData<>(handlerClass, requestClass));
        return this;
    }

    public void launch() {
        var injector = iocInjectorFactory.apply(dependencyModules);
        var restHandlerBinder = injector.instance(RESTHandlerBinder.class);
        handlerRESTBindings.forEach((endpoint, handlerData) ->
                    restHandlerBinder.bind(endpoint,
                            injector.instance(handlerData.handlerClass()),
                            handlerData.requestClass())
        );
    }
}
