package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.Refund;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Ref;
import java.util.List;
import java.util.Map;

/**
 * 退款处理DaoImpl.
 *
 * @author Gregory
 */
public interface IRefundDao {
    /**
     * 运营平台商户退款中
     *
     * @param map 查询条件
     * @return 信息集合
     */
    List<Refund> findRefundList(Map<String, Object> map);

    /**
     * 商户退款查询
     *
     * @param map 查询条件
     * @return 信息集合
     */
    List<Refund> getOrderInfoListByMerchant(Map<String, Object> map);

    /**
     * 插入或者更新Refund.
     *
     * @param refund 退款单
     */
    Object insertOrUpdateRefund(Refund refund);

    /**
     * 更新退款单
     *
     * @param refund 退款单信息
     * @return true or false
     */
    Boolean updateRefund(Refund refund);
}
