package com.joaquimmnetto.lambdagateway.infra.http;

import com.joaquimmnetto.lambdagateway.infra.http.sparkjava.SparkJavaRESTFacade;

public interface RESTOperationsFacade {
    static RESTOperationsFacade sparkJavaOperations() {
        return new SparkJavaRESTFacade();
    }

    void listenPOST(String path, HTTPRequestHandler requestHandlerWrapping);
}
