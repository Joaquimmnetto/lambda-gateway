package com.joaquimmnetto.lambdagateway.ioc.internals;

import java.util.HashMap;
import java.util.Map;

public abstract class DependencyModule {

    private final Map<Class<?>, BindingTarget<?>> bindings = new HashMap<>();

    public <T> void bind(Class<T> keyClass, T bindingInstance) {
        bindings.put(keyClass, new BindingTarget<T>(bindingInstance));
    }

    public <T> void bind(Class<T> keyClass, Class<? extends T> targetClass) {
        bindings.put(keyClass, new BindingTarget<>(targetClass));
    }


    public <T> void bindToSelf(Class<T> clazz) {
        bind(clazz, clazz);
    }

    public <T> Map<Class<?>, BindingTarget<?>> bindings() {
        return bindings;
    }
}
