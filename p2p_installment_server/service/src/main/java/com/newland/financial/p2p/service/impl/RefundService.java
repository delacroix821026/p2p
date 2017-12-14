package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.domain.entity.Refund;
import com.newland.financial.p2p.service.IRefundService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退款ServiceImpl.
 *
 * @author Gregory
 */
@Log4j
@Service
public class RefundService implements IRefundService {
    /**
     * 注入Dao.
     */
    @Autowired
    private IRefundDao refundDao;

    /**
     * 插入或者更新Refund.
     *
     * @param refund 退款单
     * @return true or false
     */
    public Object insertOrUpdateRefund(Refund refund) {
        return refundDao.insertOrUpdateRefund(refund);
    }

    /**
     * 更新退款单
     *
     * @param refund 退款单信息
     * @return true or false
     */
    public Boolean updateRefund(Refund refund) {
        log.info("service更新退款单");
        return refundDao.updateRefund(refund);
    }
}
