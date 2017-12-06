package com.newland.financial.p2p.service;

import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.CodeMsgReq;
import com.newland.financial.p2p.domain.entity.MerInfo;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.pagehelper.PageInfo;

import java.util.Map;

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

    PageInfo<MerInfo> getMerchantList(PageModel<MerInfo> pageModel);

    MerInfo getMerchantDetail(@PathVariable(name = "merchantId") String merchantId);

    void updateMerchantBySystem(MerInfo merInfo);

    void uploadMerchantBySystem(MerInfo merInfo);

    void updateMerchant(MerInfo merInfo);
}
