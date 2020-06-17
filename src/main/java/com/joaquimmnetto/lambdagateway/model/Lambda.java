package com.joaquimmnetto.lambdagateway.model;

import java.util.Optional;

public interface Lambda {

    Optional<Object> call(LambdaPayload lambdaPayload);
}
