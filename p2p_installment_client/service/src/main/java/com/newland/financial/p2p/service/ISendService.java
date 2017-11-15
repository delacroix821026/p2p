package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.SendServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "p2p",url = "https://tt.lfqpay.com/lfq-pay/gateway/api", fallback = SendServiceHystrix.class)
public interface ISendService {
    @RequestMapping(method = RequestMethod.POST, value = "/backSMSCodeRequest.do")
    Object sendMsgToLbf(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/backTransRequest.do")
    Object sendOrderinfoToLbf(@RequestBody String jsonStr);
}
