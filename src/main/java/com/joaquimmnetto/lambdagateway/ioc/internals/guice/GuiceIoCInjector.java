package com.joaquimmnetto.lambdagateway.ioc.internals.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;

import java.util.List;

public class GuiceIoCInjector implements IoCInjector {

    private final Injector injector;

    public GuiceIoCInjector(List<DependencyModule> dependencyModules) {
        AbstractModule[] guiceModules = dependencyModules.stream()
                .map(GuiceAbstractModule::new).toArray(AbstractModule[]::new);
        this.injector = Guice.createInjector(guiceModules);
    }

    @Override
    public <T> T instance(Class<T> instanceClass) {
        return injector.getInstance(instanceClass);
    }

}
