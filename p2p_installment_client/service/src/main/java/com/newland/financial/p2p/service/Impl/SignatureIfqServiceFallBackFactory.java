package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISignatureIfqService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SignatureIfqServiceFallBackFactory implements FallbackFactory<ISignatureIfqService> {
    
    public ISignatureIfqService create(final Throwable cause) {
        return new ISignatureIfqService() {
            public String signature(String jsonStr) {
                    log.info("*********signature:被熔断***********");
                    log.error(cause);
                    return "1026";
            }
        };
    }
}
