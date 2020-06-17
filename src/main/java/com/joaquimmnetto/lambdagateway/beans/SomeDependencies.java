package com.joaquimmnetto.lambdagateway.beans;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class SomeDependencies extends AbstractModule {

    @Provides
    public String string() {
        throw new UnsupportedOperationException();
    }


    @Override
    protected void configure() {
        bind(String.class).toInstance("bla");
    }
}
