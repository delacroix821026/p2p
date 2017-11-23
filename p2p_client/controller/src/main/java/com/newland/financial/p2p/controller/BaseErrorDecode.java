package com.newland.financial.p2p.controller;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;

@Log4j
public class BaseErrorDecode implements ErrorDecoder {
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            log.info("status" + response.status());
            log.info("reason" + response.reason());

        }
        return new HystrixBadRequestException("A");
    }
}
