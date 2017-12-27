package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.dao.IRefundDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
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
     * 注入Dao.
     */
    @Autowired
    private IOrderInfoDao orderInfoDao;

    /**
     * 插入或者更新Refund.
     *
     * @param refund 退款单
     * @return true or false
     */
    public Object insertOrUpdateRefund(Refund refund) {
        String state = refund.getState();
        if ("1".equals(state)) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setContractsState("5");
            orderInfo.setOrderId(refund.getOrderId());
            orderInfoDao.updateOrder(orderInfo);
        }
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
