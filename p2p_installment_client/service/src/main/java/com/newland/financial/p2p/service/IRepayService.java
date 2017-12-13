package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.RepayServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *还款推送处理.
 * @author Gregory
 */
@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallbackFactory = RepayServiceFallBackFactory.class)
public interface IRepayService {
    /**
     * 更新还款表.
     * @param jsonStr 乐百分推送的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/repay/receiveRepaymentInfo")
    String updateRepayInfo(@RequestBody String jsonStr);
}
