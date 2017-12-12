package com.newland.financial.p2p.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.newland.financial.p2p.common.exception.BaseRuntimeException;
import com.newland.financial.p2p.common.handler.RestError;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static feign.FeignException.errorStatus;

@Log4j
@Configuration
public class BaseErrorDecode implements ErrorDecoder {
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 400) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                RestError restError = mapper.readValue(response.body().asReader(), RestError.class);
                return new HystrixBadRequestException("", new BaseRuntimeException(restError));

            } catch (IOException e) {
                log.error(e);
            }
            log.info("reason" + response.reason());

        }
        return FeignException.errorStatus(methodKey, response);
    }
}
