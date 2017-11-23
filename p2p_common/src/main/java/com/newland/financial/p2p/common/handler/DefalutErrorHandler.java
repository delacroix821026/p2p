package com.newland.financial.p2p.common.handler;

import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import feign.FeignException;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public RestError handlerBaseRuntimeException(FeignException ex) {
        log.info("1" + ex.getCause());
        log.info("2" + ex.getMessage());
        log.info("3" + ex.getLocalizedMessage());
        //log.info("4" + ex.getSuppressed().);
        RestError.Builder erb = new RestError.Builder();
        erb.setCode(1);
        erb.setMessage("cuowula");
        erb.setMoreInfoUrl("/abc.htm");
        return erb.build();
    }
}
