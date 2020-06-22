package com.joaquimmnetto.lambdagateway.infra.tools;

import com.google.gson.Gson;

public interface Serializer {

    static Serializer instance() {
        return new GSONSerializer(new Gson());
    }

    <T> T deserialize(String serialized, Class<T> targetClass);

    String serialize(Object toSerialize);
}
