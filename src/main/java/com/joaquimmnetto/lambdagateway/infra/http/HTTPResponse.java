package com.joaquimmnetto.lambdagateway.infra.http;

import org.apache.http.entity.ContentType;

import java.util.Optional;

public class HTTPResponse {

    private String responseBody;
    private int responseStatus;
    private String responseContentType;

    public HTTPResponse(String responseBody, int responseStatus, String responseContentType) {
        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
        this.responseContentType = responseContentType;
    }

    public static HTTPResponse success() {
        return new HTTPResponse(null, 204, null);
    }

    public static HTTPResponse success(String response) {
        return new HTTPResponse(response, 200, ContentType.APPLICATION_JSON.getMimeType());
    }

    public static HTTPResponse serverError(Exception e) {
        return new HTTPResponse(e.getMessage(), 500, ContentType.TEXT_PLAIN.getMimeType());
    }

    public static HTTPResponse requestError(Exception e) {
        return new HTTPResponse(e.getMessage(), 400, ContentType.TEXT_PLAIN.getMimeType());
    }

    public Optional<String> body() {
        return Optional.ofNullable(responseBody);
    }

    public int status() {
        return responseStatus;
    }

    public Optional<String> contentType() {
        return Optional.ofNullable(responseContentType);
    }
}
