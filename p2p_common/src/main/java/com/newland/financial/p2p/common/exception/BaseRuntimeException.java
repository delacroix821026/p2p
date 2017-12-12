package com.newland.financial.p2p.common.exception;

import com.newland.financial.p2p.common.handler.RestError;

public class BaseRuntimeException extends RuntimeException {
    private String errorCode;

    private RestError restError;
    public BaseRuntimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(RestError restError) {
        this.restError = restError;
    }
    public RestError getRestError() {
        return restError;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
