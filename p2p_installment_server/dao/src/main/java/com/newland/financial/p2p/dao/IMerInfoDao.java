package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.MerInfo;

/**
 * @author Mxia
 * 商户信息.
 */
public interface IMerInfoDao {
    /**
     * 根据商户代码查询商户信息.
     * @param merId 商户代码
     * @return 商户信息
     */
    MerInfo selectMerInfoByMerId(String merId);
}
