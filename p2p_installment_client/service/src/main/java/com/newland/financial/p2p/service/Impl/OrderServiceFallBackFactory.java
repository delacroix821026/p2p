package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IOrderService;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class OrderServiceFallBackFactory implements FallbackFactory<IOrderService> {
    public IOrderService create(final Throwable cause) {
        return new IOrderService() {
            public String createOrderInfo(String jsonStr, String merId) {
                log.info("*********createOrderInfo:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public Object findOrderInfo(String orderId) {
                log.info("*********findOrderInfo:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public Object tradeUpdateOrder(String jsonStr, String orderId) {
                log.info("*********tradeUpdateOrder:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public Object updateOrderInfo(Object ob, String orderId) {
                log.info("*********updateOrderInfo:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public Object updateAndGetOrder(Object ob, String orderId) {
                log.info("*********updateAndGetOrder:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
