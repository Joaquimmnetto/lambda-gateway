package com.joaquimmnetto.lambdagateway.launch;

import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;

import java.util.*;
import java.util.function.Consumer;

public class ServiceBuilder {

    private final List<Consumer<RESTHandlerBinder>> bindOperations;
    private RESTHandlerBinder restBinder;

    public static ServiceBuilder service() {
        return new ServiceBuilder();
    }

    private ServiceBuilder() {
        this.bindOperations = new ArrayList<>();
    }

    public IoCContainerEndpointBuilder usingIoCInjector(IoCInjector injector) {
        this.restBinder = injector.instance(RESTHandlerBinder.class);
        return new IoCContainerEndpointBuilder(this, injector);
    }


    public SimpleEndpointBuilder withRESTHandlerBinder(RESTHandlerBinder binder) {
        this.restBinder = binder;
        return new SimpleEndpointBuilder(this);
    }

    public void bindOnLaunch(Consumer<RESTHandlerBinder> bindOperation) {
        if(restBinder == null) {
            throw new IllegalStateException("Should indicate a binder source " +
                    " (through withIoCInjector or withRESTHandlerBinder) before scheduling binding operations");
        }
        bindOperations.add(bindOperation);
    }
    
    public void launch() {
        bindOperations.forEach(bindOp -> bindOp.accept(restBinder));
    }

}
