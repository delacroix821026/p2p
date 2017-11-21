package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISmsIfqService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class SmsIfqServiceFallBackFactory implements FallbackFactory<ISmsIfqService> {

    public ISmsIfqService create(final Throwable cause) {
        return new ISmsIfqService() {
            public Object backSMSCodeRequest(Object jsonStr) {
                log.info("*********backSMSCodeRequest:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
