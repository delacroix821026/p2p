package com.newland.financial.p2p.common.exception;

public class BaseRuntimeException extends RuntimeException {
    private String errorCode;
    public BaseRuntimeException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
