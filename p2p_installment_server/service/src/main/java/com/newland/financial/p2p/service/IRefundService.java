package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.Refund;

/**
 * 退款Service.
 *
 * @author Gregory
 */
public interface IRefundService {
    /**
     * 插入或者更新Refund.
     *
     * @param refund 退款单
     */
    Object insertOrUpdateRefund(Refund refund);

    /**
     * 更新退款单
     * @param refund 退款单信息
     * @return true or false
     */
    Boolean updateRefund(Refund refund);
}
