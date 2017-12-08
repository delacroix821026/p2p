package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class MerchantServiceFallBackFactory {
    public IMerchantService create(final Throwable cause) {
        return new IMerchantService() {


            public Object getMerchantList(PageModel<MerInfo> pageModel) {
                log.info("*********getMerchantList:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public MerInfo getMerchantDetail(String merchantId) {
                return null;
            }

            public boolean updateMerchantBySystem(MerInfo merInfo) {
                log.info("*********updateMerchantBySystem:被熔断***********");
                log.error(cause);
                return false;
            }

            public Object uploadMerchantBySystem(MerInfo merInfo) {
                log.info("*********uploadMerchantBySystem:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public Object updateMerchant(String merId, MerInfo merInfo) {
                log.info("*********updateMerchant:被熔断***********");
                log.error(cause);
                return "1026";
            }
        };
    }
}
