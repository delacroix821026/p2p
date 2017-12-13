package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.ISendService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SendServiceFallBackFactory implements FallbackFactory<ISendService> {
    public ISendService create(final Throwable cause) {
        return new ISendService() {
            public Object sendOrderMsgToLbf(Object object) {
                log.info("********sendOrderMsgToLbf:被熔断********");
                log.error(cause);
                return "1026";
            }
            public Object sendOrderQueryMsg(Object object) {
                log.info("********sendOrderQueryMsg:被熔断********");
                log.error(cause);
                return "1026";
            }

            public Refund sendRefundMsgReq(RefundMsgReq refundMsgReq) {
                log.info("********sendRefundMsgReq:被熔断********");
                log.error(cause);
                return null;
            }
        };
    }
}
