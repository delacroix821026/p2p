package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.SmsCodeServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *短信接口.
 * @author Gregory
 */
@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallbackFactory = SmsCodeServiceFallBackFactory.class)
public interface ISmsCodeService {

    @RequestMapping(method = RequestMethod.GET, value = "/smscode/{merId}/{mobile}")
    Object getMsgCodeReqPram(@PathVariable(name = "merId") String merId, @PathVariable(name = "mobile") String mobile);
}
