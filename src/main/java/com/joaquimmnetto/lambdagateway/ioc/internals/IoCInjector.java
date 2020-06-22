package com.joaquimmnetto.lambdagateway.ioc.internals;


import com.joaquimmnetto.lambdagateway.ioc.internals.guice.GuiceIoCInjector;
import com.joaquimmnetto.lambdagateway.ioc.internals.spring.SpringIoCInjector;

import java.util.List;

public interface IoCInjector {

    <T> T instance(Class<T> instanceClass);

    static IoCInjector createGuice(List<DependencyModule> dependencyModules) {
        return new GuiceIoCInjector(dependencyModules);
    }
    static IoCInjector createSpring(List<DependencyModule> dependencyModules) {
        return new SpringIoCInjector(dependencyModules);
    }
}
