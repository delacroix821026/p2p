package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.OrderServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallback = OrderServiceHystrix.class)
public interface IOrderService {

    @RequestMapping(method = RequestMethod.POST, value = "/Order/createOrderInfo")
    String createOrderInfo(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/Order/findOrderInfo")
    Object findOrderInfo(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/Order/tradeUpdateOrder")
    Object tradeUpdateOrder(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/Order/updateOrderInfo")
    Object updateOrderInfo(@RequestBody Object ob);
}

