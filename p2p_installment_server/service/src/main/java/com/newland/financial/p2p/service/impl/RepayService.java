package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import com.newland.financial.p2p.domain.entity.Repay;
import com.newland.financial.p2p.service.IRepayService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 处理还款推送ServiceImpl.
 *
 * @author Gregory
 */
@Log4j
@Service
public class RepayService implements IRepayService {
    /**
     * 还款表操作Dao.
     */
    @Autowired
    private IRepayDao repayDao;
    /**
     * 订单Dao.
     */
    @Autowired
    private IOrderInfoDao orderInfoDao;

    /**
     * 接收还款推送信息.
     *
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    public String receiveRepayInfo(Repay repay) {
        log.info("--------------------------------进入RepayService:");
        String id = UUID.randomUUID().toString().replace("-", "");
        boolean bol = false;
        repay.setId(id);
        bol = repayDao.insertRepayInfo(repay);
        // 如果是最后一期则需要同步订单的状态
        if (repay.getTxnAmt() == repay.getSumAmount()) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(repay.getOrderId());
            String payType = repay.getPayType();
            if ("0".equals(payType)) {
                orderInfo.setContractsState("2");
            } else if ("1".equals(payType)) {
                orderInfo.setContractsState("4");
            }
            log.info("最后一期还款，更新订单状态：" + orderInfo.toString());
            orderInfoDao.updateOrder(orderInfo);
        }
        if (bol) {
            return "true";
        }
        return "false";
    }
}
