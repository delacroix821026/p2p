package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.OrderServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallback = OrderServiceHystrix.class)
public interface IOrderService {

    @RequestMapping(method = RequestMethod.POST, value = "/OrderController/createOrderInfo")
    Object createOrderInfo(@RequestBody String jsonStr);
}

