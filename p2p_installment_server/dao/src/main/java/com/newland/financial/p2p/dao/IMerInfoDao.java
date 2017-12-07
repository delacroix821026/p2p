package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.MerInfo;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询商户列表(管理平台).
     * @param map 查询条件
     * @return 信息集合
     */
    List<MerInfo> findMerchantList(Map<String, Object> map);
    /**
     * 更新商户信息(费率和合同号).
     * @param merInfo 更新内容
     * @return boolean
     */
    boolean updateMerchant(MerInfo merInfo);
}
