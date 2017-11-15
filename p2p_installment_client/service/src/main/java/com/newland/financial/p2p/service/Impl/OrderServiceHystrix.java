package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class OrderServiceHystrix implements IOrderService {

    public Object getValidateInfo(String jsonStr) {
        log.info("*********getValidateInfo:被熔断***********");
        return 1026;
    }

    public Object getRespInfo(String jsonStr) {
        log.info("*********getRespInfo:被熔断***********");
        return 1026;
    }
}
