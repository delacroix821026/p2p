package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISendService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SendServiceHystrix implements ISendService {
    public Object sendMsgToLbf(String jsonStr) {
        log.info("********sendMsgToLbf:被熔断********");
        return 1026;
    }
}
