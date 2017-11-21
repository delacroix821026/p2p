package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.Repay;

/**
 * 还款推送Service.
 *
 * @author Gregory
 */
public interface IRepayService {

    /**
     * 接收还款推送信息.
     *
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    String receiveRepayInfo(Repay repay);
}
