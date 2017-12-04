package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 商户信息处理Service.
 *
 * @author Gregory
 */
public interface IMerchantService {

    /**
     * 查询商户信息.
     *
     * @param merId 商户Id
     * @return MerInfo对象
     */
    CodeMsgReq getMerInfo(String merId);

    List<MerInfo> getMerchantList(MerInfo merInfo);

    MerInfo getMerchantDetail(@PathVariable(name = "merchantId") String merchantId);

    void updateMerchantBySystem(MerInfo merInfo);

    void uploadMerchantBySystem(MerInfo merInfo);

    void updateMerchant(MerInfo merInfo);
}
