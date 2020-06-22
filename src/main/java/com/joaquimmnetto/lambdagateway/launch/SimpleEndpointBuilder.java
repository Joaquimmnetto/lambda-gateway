package com.joaquimmnetto.lambdagateway.launch;


import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;


public class SimpleEndpointBuilder {

    private ServiceBuilder service;

    public SimpleEndpointBuilder(ServiceBuilder service) {
        this.service = service;
    }

    public <T> SimpleEndpointBuilder addRESTBinding(String method, String path,
                                                    MessageHandler handler, Class<T> requestClass) {
        HTTPEndpoint bindingEndpoint = new HTTPEndpoint(method, path);
        service.bindOnLaunch(restBinder -> restBinder.bind(bindingEndpoint, handler, requestClass));
        return this;
    }

    public void launch() {
        service.launch();
    }
}
