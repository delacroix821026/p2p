package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IMerInfoDao;
import com.newland.financial.p2p.domain.entity.MerInfo;
import org.springframework.stereotype.Repository;

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
}
