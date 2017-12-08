package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.ILfqMerchantService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
public class LfqMerchantServiceFallBackFactory {
    public ILfqMerchantService create(final Throwable cause) {
        return new ILfqMerchantService() {


            public String updateMerchantBySystem(MerInfo merInfo) {
                log.info("*********updateMerchantBySystem:被熔断***********");
                log.error(cause);
                return "1026";
            }

            public void uploadMerchantBySystem(MerInfo merInfo) {

            }
        };
    }
}
