package com.joaquimmnetto.lambdagateway.launch;


import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleEndpointBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ServiceBuilder service;

    public SimpleEndpointBuilder(ServiceBuilder service) {
        this.service = service;
    }

    public <T> SimpleEndpointBuilder addRESTBinding(String method, String path,
                                                    MessageHandler handler, Class<T> requestClass) {
        HTTPEndpoint bindingEndpoint = new HTTPEndpoint(method, path);

        logger.info("Scheduling bind ({} - {})", bindingEndpoint, handler.getClass().getSimpleName());
        service.bindOnLaunch(restBinder -> restBinder.bind(bindingEndpoint, handler, requestClass));
        return this;
    }

    public void launch() {
        service.launch();
    }
}
