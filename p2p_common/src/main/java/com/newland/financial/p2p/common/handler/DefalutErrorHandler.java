package com.newland.financial.p2p.common.handler;

import com.netflix.hystrix.exception.HystrixBadRequestException;
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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseRuntimeException.class)
    public RestError handlerBaseRuntimeException(BaseRuntimeException ex) {
        RestError.Builder erb = new RestError.Builder();
        erb.setCode(1);
        erb.setMessage("cuowula");
        erb.setMoreInfoUrl("/abc.htm");
        return erb.build();
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "业务异常C")
    @ExceptionHandler(HystrixBadRequestException.class)
    public RestError handlerHystrixBadRequestException(HystrixBadRequestException ex) {
        RestError.Builder erb = new RestError.Builder();
        erb.setCode(2);
        erb.setMessage("错误啦");
        erb.setMoreInfoUrl("/2cba.htm");
        return erb.build();
    }
}
