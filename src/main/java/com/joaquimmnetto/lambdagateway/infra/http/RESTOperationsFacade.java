package com.joaquimmnetto.lambdagateway.infra.http;

public interface RESTOperationsFacade {
    void listenPOST(String path, HTTPRequestHandler requestHandlerWrapping);
}
