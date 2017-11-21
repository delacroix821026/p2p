package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.SmsIfqServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *外发Ifqpay-client短信接口.
 * @author Gregory
 */
@FeignClient(name = "p2p",url = "localhost:3003", fallbackFactory = SmsIfqServiceFallBackFactory.class)
public interface ISmsIfqService {
    /**
     * 请求Ifqpay-client短信接口.
     * @param jsonStr 请求参数 CodeMsgReq对象
     * @return  Ifqpay-client返回报文
     */
    @RequestMapping(method = RequestMethod.POST, value = "/smsCodeController/backSMSCodeRequest")
    Object backSMSCodeRequest(Object jsonStr);
}
