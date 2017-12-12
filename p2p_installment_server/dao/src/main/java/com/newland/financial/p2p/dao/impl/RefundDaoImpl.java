package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.domain.entity.Refund;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 退款处理DaoImpl.
 *
 * @author Gregory
 */
@Repository
public class RefundDaoImpl extends MybatisBaseDao<Refund> implements IRefundDao {
    /**
     * 运营平台商户退款中
     * @param map 查询条件
     * @return 信息集合
     */
    public List<Refund> findRefundList(Map<String, Object> map) {
        return super.select("findFundInfo", map);
    }
    /**
     * 商户退款订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<Refund> getOrderInfoListByMerchant(Map<String, Object> map) {
        return super.select("getOrderInfoListByMerchant", map);
    }
}
