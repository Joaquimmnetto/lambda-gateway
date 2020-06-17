package com.joaquimmnetto.lambdagateway.infra.http;
import java.util.Objects;

public class HTTPBody {
    private final String body;

    public HTTPBody(String body) {
        this.body = body;
    }

    public String body() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HTTPBody httpBody = (HTTPBody) o;
        return Objects.equals(body, httpBody.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

}
