package com.joaquimmnetto.lambdagateway.infra.http.sparkjava;

import com.joaquimmnetto.lambdagateway.infra.http.HTTPBody;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPResponse;
import com.joaquimmnetto.lambdagateway.infra.http.RESTOperationsFacade;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class SparkJavaRESTFacade implements RESTOperationsFacade {

    private final Serializer serializer;

    public SparkJavaRESTFacade(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void listenPOST(String path, HTTPRequestHandler requestHandlerWrapping) {
        Spark.post(path, (req, res) -> processBodiedRequest(req, res, requestHandlerWrapping) );
    }


    private Response processBodiedRequest(Request req, Response res, HTTPRequestHandler requestHandler) {
        HTTPBody body = new HTTPBody(req.body());
        Map<String, String> headers = extractHeaders(req);
        Map<String, String> queryParams = extractQueryParams(req);
        Map<String, String> pathParams = req.params();
        HTTPResponse response = requestHandler.handle(body, headers, pathParams, queryParams);
        return populateSparkResponse(res, response);
    }

    private Map<String, String> extractQueryParams(Request req) {
        return req.queryParams().stream()
                .map(paramName -> new String[]{paramName, req.queryParams(paramName)})
                .collect(toMap(headerParts -> headerParts[0], headerParts -> headerParts[1]));
    }

    private Map<String, String> extractHeaders(Request req) {
        return req.headers().stream()
                .map(headerKey -> new String[]{headerKey, req.headers(headerKey)})
                .collect(toMap(headerParts -> headerParts[0], headerParts -> headerParts[1]));
    }

    private Response populateSparkResponse(Response res, HTTPResponse response) {
        res.body(response.body().orElse(null));
        res.status(response.status());
        res.type(response.contentType().orElse(null));
        return res;
    }

}
