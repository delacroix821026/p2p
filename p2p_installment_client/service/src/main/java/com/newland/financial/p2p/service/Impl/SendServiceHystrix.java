package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.ISendService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SendServiceHystrix implements ISendService {
    public Object sendOrderMsgToLbf(Object object) {
        log.info("********sendOrderMsgToLbf:被熔断********");
        return 1026;
    }

    public Object sendOrderQueryMsg(Object object) {
        log.info("********sendOrderQueryMsg:被熔断********");
        return 1026;
    }
}
