package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.domain.entity.RefundMsgReq;
import com.newland.financial.p2p.service.Impl.OrderServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallbackFactory = OrderServiceFallBackFactory.class)
public interface IRefundService {

    @RequestMapping(method = RequestMethod.POST, value = "/refund/getRefundMsg")
    RefundMsgReq getRefundMsg(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/refund/updateRefundOrder")
    RefundMsgReq insertRefund(@RequestBody Refund refund);
}
