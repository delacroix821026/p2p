package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.MerInfo;


/**
 * 商户信息处理Service.
 *
 * @author Gregory
 */
public interface IMerchantService {
    /**
     * 商户接入.
     * @param merInfo 商户信息
     */
    String updateMerchantBySystem(MerInfo merInfo);

    void uploadMerchantBySystem(MerInfo merInfo);

}
