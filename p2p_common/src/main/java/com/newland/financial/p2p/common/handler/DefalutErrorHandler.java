package com.newland.financial.p2p.common.handler;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefalutErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseRuntimeException.class)
    public RestError handlerBaseRuntimeException(BaseRuntimeException ex) {
        RestError.Builder erb = new RestError.Builder();
        erb.setCode(1);
        erb.setMessage("cuowula");
        erb.setMoreInfoUrl("/abc.htm");
        return erb.build();
    }
}
