package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.Refund;

/**
 * 退款Service.
 * @author Gregory
 */
public interface IRefundService {
    /**
     * 插入或者更新Refund.
     * @param refund 退款单
     */
    void insertOrUpdateRefund(Refund refund);
}
