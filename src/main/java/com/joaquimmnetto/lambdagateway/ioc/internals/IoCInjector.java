package com.joaquimmnetto.lambdagateway.ioc.internals;


import com.joaquimmnetto.lambdagateway.ioc.internals.guice.GuiceIoCInjector;
import com.joaquimmnetto.lambdagateway.ioc.internals.spring.SpringIoCInjector;

import java.util.List;

import static java.util.Arrays.asList;

public interface IoCInjector {

    <T> T instance(Class<T> instanceClass);

    static IoCInjector forGuice(DependencyModule... dependencyModules) {
        return new GuiceIoCInjector(asList(dependencyModules));
    }
    static IoCInjector createSpring(DependencyModule... dependencyModules) {
        return new SpringIoCInjector(asList(dependencyModules));
    }
}
