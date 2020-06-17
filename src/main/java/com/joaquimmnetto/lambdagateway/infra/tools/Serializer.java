package com.joaquimmnetto.lambdagateway.infra.tools;

public interface Serializer {

    <T> T deserialize(String serialized, Class<T> targetClass);

    String serialize(Object toSerialize);
}
