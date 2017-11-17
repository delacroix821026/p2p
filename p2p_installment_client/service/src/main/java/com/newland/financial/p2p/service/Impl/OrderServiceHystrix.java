package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class OrderServiceHystrix implements IOrderService {

    public String createOrderInfo(String jsonStr) {
        log.info("*********createOrderInfo:被熔断***********");
        return "1026";
    }

    public Object findOrderInfo(String jsonStr) {
        log.info("*********findOrderInfo:被熔断***********");
        return 1026;
    }

    public Object tradeUpdateOrder(String jsonStr) {
        log.info("*********tradeUpdateOrder:被熔断***********");
        return 1026;
    }

    public Object updateOrderInfo(Object ob) {
        log.info("*********updateOrderInfo:被熔断***********");
        return 1026;
    }
}
