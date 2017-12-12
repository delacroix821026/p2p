package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.IRefundService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class RefundServiceFallBackactory {
    public IRefundService create(final Throwable cause) {
        return new IRefundService() {


            public RefundMsgReq getRefundMsg(String jsonStr) {
                log.info("*********getRefundMsg:被熔断***********");
                log.error(cause);
                return null;
            }

            public RefundMsgReq insertRefund(Refund refund) {
                log.info("*********insertRefund:被熔断***********");
                log.error(cause);
                return null;
            }
        };
    }
}
