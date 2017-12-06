package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.domain.entity.MerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *商户信息处理DaoImpl.
 * @author Gregory
 */
@Repository
public class MerInfoDaoImpl extends MybatisBaseDao<MerInfo> implements IMerInfoDao {
    /**
     * 根据商户代码查询商户信息.
     * @param merId 商户代码
     * @return 商户信息
     */
    public MerInfo selectMerInfoByMerId(String merId) {
        return super.selectEntity("selectByMerId", merId);
    }
    /**
     * 分页查询商户列表(管理平台).
     * @param map 查询条件
     * @return 信息集合
     */
    public List<MerInfo> findMerchantList(Map<String, Object> map) {
        return super.select("selectMerchantList", map);
    }
}
