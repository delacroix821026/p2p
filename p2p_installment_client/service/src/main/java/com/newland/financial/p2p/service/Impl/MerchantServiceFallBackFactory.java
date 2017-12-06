package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.service.IMerchantService;

public class MerchantServiceFallBackFactory {
    public IMerchantService create(final Throwable cause) {
        return new IMerchantService() {


            public Object getMerchantList(PageModel<MerInfo> pageModel) {
                return null;
            }

            public MerInfo getMerchantDetail(String merchantId) {
                return null;
            }

            public void updateMerchantBySystem(MerInfo merInfo) {

            }

            public void uploadMerchantBySystem(MerInfo merInfo) {

            }

            public void updateMerchant(MerInfo merInfo) {

            }
        };
    }
}
