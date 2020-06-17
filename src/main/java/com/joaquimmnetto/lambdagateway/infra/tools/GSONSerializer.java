package com.joaquimmnetto.lambdagateway.infra.tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.DeserializatonException;

public class GSONSerializer implements Serializer {

    private final Gson gson;

    public GSONSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> T deserialize(String serialized, Class<T> targetClass) {
        try {
            return gson.fromJson(serialized, targetClass);
        } catch(JsonSyntaxException e) {
            throw new DeserializatonException(e);
        }
    }

    @Override
    public String serialize(Object toSerialize) {
        return gson.toJson(toSerialize);
    }
}
