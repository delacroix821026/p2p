package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IRepayService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class RepayServiceFallBackFactory implements FallbackFactory<IRepayService> {
    public IRepayService create(final Throwable cause) {
        return new IRepayService() {
            public String updateRepayInfo(String jsonStr) {
                log.info("*********updateRepayInfo:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
