package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.RepayServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallback = RepayServiceHystrix.class)
public interface IRepayService {

    /**
     * 更新还款表.
     * @param jsonStr 乐百分推送的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/RepayController/receiveRepaymentInfo")
    String updateRepayInfo(@RequestBody String jsonStr);



}
