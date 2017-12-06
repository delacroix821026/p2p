package com.newland.financial.p2p.service;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.Impl.MerchantServiceFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商户信息
 * @author Delacroix
 */
@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallbackFactory = MerchantServiceFallBackFactory.class)
public interface IMerchantService {
    @RequestMapping(method = RequestMethod.GET, value = "/merchant")
    Object getMerchantList(PageModel<MerInfo> pageModel);

    @RequestMapping(method = RequestMethod.GET, value = "/merchant/{merchantId}")
    MerInfo getMerchantDetail(@PathVariable(name = "merchantId") String merchantId);

    @RequestMapping(method = RequestMethod.POST, value = "/merchant/synchMerchant")
    void updateMerchantBySystem(MerInfo merInfo);

    @RequestMapping(method = RequestMethod.POST, value = "/merchant/synchMerchantFile")
    void uploadMerchantBySystem(MerInfo merInfo);

    @RequestMapping(method = RequestMethod.POST, value = "/merchant/{merchantId}")
    void updateMerchant(MerInfo merInfo);

}
