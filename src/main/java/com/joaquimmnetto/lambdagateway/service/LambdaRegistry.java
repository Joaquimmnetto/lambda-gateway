package com.joaquimmnetto.lambdagateway.service;

import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;

public interface LambdaRegistry {
    Lambda get(LambdaIdentifier lambdaIdentifier);
}
