package com.joaquimmnetto.lambdagateway.infra.http.sparkjava;

import com.joaquimmnetto.lambdagateway.infra.http.HTTPBody;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPResponse;
import com.joaquimmnetto.lambdagateway.infra.http.RESTOperationsFacade;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class SparkJavaRESTFacade implements RESTOperationsFacade {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SparkJavaRESTFacade() {
        Spark.afterAfter(this::logResponse);
    }

    @Override
    public void listenPOST(String path, HTTPRequestHandler requestHandlerWrapping) {
        Spark.post(path, (req, res) -> processBodiedRequest(req, res, requestHandlerWrapping) );
    }


    private String processBodiedRequest(Request req, Response res, HTTPRequestHandler requestHandler) {
        HTTPBody body = new HTTPBody(req.body());
        Map<String, String> headers = extractHeaders(req);
        Map<String, String> queryParams = extractQueryParams(req);
        Map<String, String> pathParams = req.params();
        HTTPResponse response = requestHandler.handle(body, headers, pathParams, queryParams);
        populateSparkResponse(res, response);
        return res.body();
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

    private void populateSparkResponse(Response outerResponse, HTTPResponse appResponse) {
        outerResponse.body(appResponse.body().orElse(null));
        outerResponse.status(appResponse.status());
        outerResponse.type(appResponse.contentType().orElse(null));
    }

    private void logResponse(Request request, Response response) {
        Optional<Integer> bodyLength = Optional.ofNullable(response.body()).map(body -> body.getBytes().length);
        logger.info("{}: {} {} - {} - {} bytes", request.protocol(), request.requestMethod(), request.pathInfo(),
                    response.status(), bodyLength.orElse(0));
    }

}
