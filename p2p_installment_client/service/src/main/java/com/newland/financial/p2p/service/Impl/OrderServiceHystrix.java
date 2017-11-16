package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class OrderServiceHystrix implements IOrderService {

    public Object createOrderInfo(String jsonStr) {
        log.info("*********createOrderInfo:被熔断***********");
        return 1026;
    }

    public Object findOrderInfo(String jsonStr) {
        log.info("*********findOrderInfo:被熔断***********");
        return 1026;
    }
}
