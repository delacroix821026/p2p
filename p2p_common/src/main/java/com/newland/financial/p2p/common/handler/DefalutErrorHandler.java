package com.newland.financial.p2p.common.handler;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class DefalutErrorHandler {
    @ExceptionHandler(BaseRuntimeException.class)
    public RestError handlerBaseRuntimeException(BaseRuntimeException ex, HttpServletResponse response) {
        //response.setStatus(HttpStatus.CREATED);
        return null;
    }
}
