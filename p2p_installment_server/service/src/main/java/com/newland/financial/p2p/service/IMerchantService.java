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

    /**
     * 管理平台获取商户列表.
     *
     * @param pageModel 查询条件
     * @return 分页结果
     */
    PageInfo<MerInfo> getMerchantList(PageModel<MerInfo> pageModel);

    MerInfo getMerchantDetail(String merchantId);

    /**
     * 商户接入.
     * @param merInfo
     * @return 最新的商户信息
     */
    boolean updateMerchantBySystem(MerInfo merInfo);

    void uploadMerchantBySystem(MerInfo merInfo);

    /**
     * 更新商户信息(费率和合同号).
     * @param merInfo 更新内容
     * @return boolean
     */
    boolean updateMerchant(MerInfo merInfo);
}
