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
<<<<<<< HEAD

    public Object updateAndGetOrder(Object ob) {
        log.info("*********updateAndGetOrder:被熔断***********");
        return 1026;
    }

=======
>>>>>>> c4a1bf8776ca74eacf786d2c2686a8819cd4d6bd
}
