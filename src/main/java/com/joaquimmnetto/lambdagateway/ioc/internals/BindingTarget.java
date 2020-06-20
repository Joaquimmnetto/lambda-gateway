package com.joaquimmnetto.lambdagateway.ioc.internals;

public class BindingTarget<T> {

    private final Class<T> clazz;
    private final T instance;

    public BindingTarget(Class<T> clazz) {
        this.clazz = clazz;
        this.instance = null;
    }

    public BindingTarget(T instance) {
        this.clazz = null;
        this.instance = instance;
    }

    public boolean isInstanceTarget() {
        return instance != null;
    }

    public T instance() {
        if(instance == null) {
            throw new UnsupportedOperationException();
        }
        return instance;
    }

    public Class<T> targetClass() {
        if(clazz == null) {
            throw new UnsupportedOperationException();
        }
        return clazz;
    }
}
