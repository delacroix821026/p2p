package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.service.Impl.OrderServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallbackFactory = OrderServiceFallBackFactory.class)
public interface IOrderService {

    @RequestMapping(method = RequestMethod.POST, value = "/order/{merId}")
    String createOrderInfo(@RequestBody String jsonStr, @PathVariable(name = "merId") String merId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}")
    Object findOrderInfo(@PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/OrderMsgReq")
    Object tradeUpdateOrder(@RequestBody String jsonStr, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}")
    Object updateOrderInfo(@RequestBody Object ob, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/OrderInfo")
    Object updateAndGetOrder(@RequestBody Object ob, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}/OrderInfo")
    Object findBlankOrder(@PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/my/{userId}")
    List<OrderInfo> getOrderInfoListByCustomer(@PathVariable(name = "userId") String userId, OrderInfo orderInfo);

    @RequestMapping(method = RequestMethod.GET, value = "/order/my/{userId}/{orderId}")
    OrderInfo getOrderInfoDetailByCustomer(@PathVariable(name = "userId") String userId, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/merchant/{merchantId}")
    List<OrderInfo> getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, OrderInfo orderInfo);

    @RequestMapping(method = RequestMethod.GET, value = "/order/merchant/{merchantId}/{orderId}")
    OrderInfo getOrderInfoDetailByMerchant(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/plant/")
    OrderInfo getOrderInfoListByPlantManager(OrderInfo orderInfo);

    @RequestMapping(method = RequestMethod.GET, value = "/order/plant/{orderId}")
    OrderInfo getOrderInfoDetailByPlantManager(@PathVariable(name = "orderId") String orderId);
}

