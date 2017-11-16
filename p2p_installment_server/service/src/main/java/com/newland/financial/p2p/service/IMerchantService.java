package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.CodeMsgReq;

/**
 *商户信息处理Service.
 * @author Gregory
 */
public interface IMerchantService {

    /**
     * 查询商户信息.
     * @param merId     商户Id
     * @return     MerInfo对象
     */
    CodeMsgReq getMerInfo(String merId);
}
