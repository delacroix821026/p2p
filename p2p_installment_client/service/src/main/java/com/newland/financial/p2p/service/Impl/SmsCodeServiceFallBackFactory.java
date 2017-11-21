package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISmsCodeService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class SmsCodeServiceFallBackFactory implements FallbackFactory<ISmsCodeService> {
    public ISmsCodeService create(final Throwable cause) {
        return new ISmsCodeService() {
            public Object getMsgCodeReqPram(String jsonStr) {
                    log.info("*********getMsgCodeReqPram:被熔断***********");
                    log.error(cause);
                    return "1026";
            }
        };
    }
}
