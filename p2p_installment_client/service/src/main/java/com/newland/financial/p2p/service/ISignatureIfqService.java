package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.SignatureIfqServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *外发Ifqpay-client验签接口.
 * @author Gregory
 */
@FeignClient(name = "p2p",url = "localhost:3003", fallbackFactory = SignatureIfqServiceFallBackFactory.class)
public interface ISignatureIfqService {
    /**
     * 请求Ifqpay-client验签接口.
     * @param jsonStr 请求参数 RepayMsgResp对象
     * @return  Ifqpay-client返回报文
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signature/sign")
    String signature(String jsonStr);

}
