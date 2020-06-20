package com.joaquimmnetto.lambdagateway.ioc.internals.guice;

import com.google.inject.AbstractModule;
import com.joaquimmnetto.lambdagateway.ioc.internals.BindingTarget;
import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;

import java.lang.reflect.Constructor;

public class GuiceAbstractModule extends AbstractModule {

    private DependencyModule module;

    public GuiceAbstractModule(DependencyModule module) {
        this.module = module;
    }

    @Override
    protected void configure() {
        module.bindings().forEach(this::resolveBinding);
    }

    private void resolveBinding(Class keyClass, BindingTarget bindingTarget) {
        if (bindingTarget.isInstanceTarget()) {
            bind(keyClass).toInstance(bindingTarget.instance());
        } else {
            bind(keyClass).toConstructor(constructorOf(bindingTarget.targetClass()));
        }
    }

    public <T> Constructor<T> constructorOf(Class<T> clazz) {
        return (Constructor<T>) clazz.getConstructors()[0];
    }

}
