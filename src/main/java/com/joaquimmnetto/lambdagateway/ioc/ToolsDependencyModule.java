package com.joaquimmnetto.lambdagateway.ioc;

import com.google.gson.Gson;
import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.infra.tools.GSONSerializer;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;

public class ToolsDependencyModule extends DependencyModule {

    public ToolsDependencyModule() {
        bind(Gson.class, new Gson());
        bind(Serializer.class, GSONSerializer.class);
    }
}
