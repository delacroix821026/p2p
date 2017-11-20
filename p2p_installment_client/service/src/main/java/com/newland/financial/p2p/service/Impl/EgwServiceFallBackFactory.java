package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IEgwService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class EgwServiceFallBackFactory implements FallbackFactory<IEgwService> {

    public IEgwService create(final Throwable cause) {
        return new IEgwService() {
            public Object backSMSCodeRequest(Object jsonStr) {
                log.info("*********backSMSCodeRequest:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public String signature(String jsonStr) {
                log.info("*********signature:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
