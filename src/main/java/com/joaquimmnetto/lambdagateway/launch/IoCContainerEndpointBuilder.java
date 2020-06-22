package com.joaquimmnetto.lambdagateway.launch;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoCContainerEndpointBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ServiceBuilder service;
    private IoCInjector injector;


    public IoCContainerEndpointBuilder(ServiceBuilder service, IoCInjector injector) {
        this.service = service;
        this.injector = injector;
    }

    public <T> IoCContainerEndpointBuilder addRESTBinding(String method, String path,
                                                          Class<? extends MessageHandler> handlerClass,
                                                          Class<T> requestClass) {
        MessageHandler handler = injector.instance(handlerClass);
        HTTPEndpoint bindingEndpoint = new HTTPEndpoint(method, path);

        logger.info("Scheduling bind ({} - {})", bindingEndpoint, handlerClass.getSimpleName());
        service.bindOnLaunch((restBinder) -> restBinder.bind(bindingEndpoint, handler, requestClass));
        return this;
    }

    public void launch() {
        service.launch();
    }
}
