package com.joaquimmnetto.lambdagateway.model;

import java.util.Objects;

public class LambdaIdentifier {
    private final String lambdaName;

    public LambdaIdentifier(String lambdaName) {
        this.lambdaName = lambdaName;
    }

    public String lambdaName() {
        return lambdaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LambdaIdentifier that = (LambdaIdentifier) o;
        return Objects.equals(lambdaName, that.lambdaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lambdaName);
    }
}
