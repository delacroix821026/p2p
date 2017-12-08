package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.Impl.LfqMerchantServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 外发商户信息
 * @author Delacroix
 */
@FeignClient(value = "lfqpay-client${DEVLOPER_NAME:}", fallbackFactory = LfqMerchantServiceFallBackFactory.class)
public interface ILfqMerchantService {
    @RequestMapping(method = RequestMethod.POST, value = "/merchant/synchMerchant")
    String updateMerchantBySystem(MerInfo merInfo);

    @RequestMapping(method = RequestMethod.POST, value = "/merchant/synchMerchantFile")
    void uploadMerchantBySystem(MerInfo merInfo);
}
