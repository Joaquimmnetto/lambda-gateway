package com.joaquimmnetto.lambdagateway.ioc.internals.spring;

import com.joaquimmnetto.lambdagateway.ioc.internals.BindingTarget;
import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class SpringIoCInjector implements IoCInjector {
    GenericApplicationContext context = new GenericApplicationContext();
    public SpringIoCInjector(List<DependencyModule> toolsDependencyModules) {
        toolsDependencyModules.stream()
                .flatMap(module -> module.bindings().values().stream())
                .forEach(this::registerSpringTarget);
        context.refresh();
    }

    private <T> void registerSpringTarget(BindingTarget target) {
        if(target.isInstanceTarget()) {
            Class keyClass = target.instance().getClass();
            context.registerBean(keyClass, target::instance);
        } else {
            context.registerBean(target.targetClass());
        }
    }

    @Override
    public <T> T instance(Class<T> instanceClass) {
        return context.getBean(instanceClass);
    }
}
