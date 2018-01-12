package com.newland.financial.p2p.service;

import com.newland.financial.p2p.common.util.PageModel;
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

    @RequestMapping(method = RequestMethod.POST, value = "/order/{merchantId}")
    String createOrderInfo(@RequestBody String jsonStr, @PathVariable(name = "merchantId") String merchantId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{merchantId}/{orderId}")
    Object findOrderInfo(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/OrderMsgReq")
    Object tradeUpdateOrder(@RequestBody String jsonStr, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}")
    Object updateOrderInfo(@RequestBody Object ob, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/OrderInfo")
    Object updateAndGetOrder(@RequestBody Object ob, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{orderId}/OrderInfo")
    Object findBlankOrder(@PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/weixin")
    Object getOrderInfoListByCustomer(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.GET, value = "/order//weixin/{openId}/{orderId}")
    Object getOrderInfoDetailByCustomer(@PathVariable(name = "openId") String openId, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.POST, value = "/order/{merchantId}/orderList")
    Object getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, @RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.GET, value = "/order/merchant/{merchantId}/{orderId}")
    OrderInfo getOrderInfoDetailByMerchant(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/orderList")
    Object getOrderInfoListByPlantManager(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.GET, value = "/order/plant/{orderId}")
    Object getOrderInfoDetailByPlantManager(@PathVariable(name = "orderId") String orderId);
    @RequestMapping(method = RequestMethod.GET, value = "/order/manager/{orderId}")
    Object getOrderInfoByManager(@PathVariable(name = "orderId")String orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/orderRundList")
    Object getOrderRundListByPlantManager(@RequestBody String jsonStr);
    @RequestMapping(method = RequestMethod.GET, value = "/order/excel")
    void exportOrderInfoExcel(String jsonStr);
}

