package com.joaquimmnetto.lambdagateway.infra.http;

public class HTTPEndpoint {
    private String method;
    private String path;

    public HTTPEndpoint(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }
}
