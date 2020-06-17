package com.joaquimmnetto.lambdagateway.model;

import java.util.Objects;

public class LambdaPayload {
    private Object content;

    public LambdaPayload(Object content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LambdaPayload that = (LambdaPayload) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
