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
    void updateMerchantBySystem(MerInfo merInfo);

    void uploadMerchantBySystem(MerInfo merInfo);

}
