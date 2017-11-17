package com.newland.financial.p2p.common.exception;

public class BaseRuntimeException extends RuntimeException {
    private Integer errorCode;
    public BaseRuntimeException(Integer errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
