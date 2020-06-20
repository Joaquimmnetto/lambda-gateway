package com.joaquimmnetto.lambdagateway.ioc;

import com.joaquimmnetto.lambdagateway.ioc.internals.DependencyModule;
import com.joaquimmnetto.lambdagateway.infra.http.RESTOperationsFacade;
import com.joaquimmnetto.lambdagateway.infra.http.sparkjava.SparkJavaRESTFacade;
import com.joaquimmnetto.lambdagateway.infra.inbound.MessageHandlerWrapper;
import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;

public class RESTDependencyModule extends DependencyModule {

    public RESTDependencyModule() {
        bindToSelf(MessageHandlerWrapper.class);
        bind(RESTOperationsFacade.class, SparkJavaRESTFacade.class);
        bindToSelf(RESTHandlerBinder.class);
    }


}
