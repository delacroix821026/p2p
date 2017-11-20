package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IInnerService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class InnerServiceFallBackFactory implements FallbackFactory<IInnerService> {
    public IInnerService create(final Throwable cause) {
        return new IInnerService() {
            public Object getMsgCodeReqPram(String jsonStr) {
                log.info("*********getMsgCodeReqPram:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public String updateRepayInfo(String jsonStr) {
                log.info("*********updateRepayInfo:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
