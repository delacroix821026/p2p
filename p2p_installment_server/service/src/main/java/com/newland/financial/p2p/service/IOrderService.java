package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.entity.OrderInfo;

/**
 * 订单处理Service.
 *
 * @author Gregory
 */
public interface IOrderService {
    /**
     * 生成订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单的编号
     */
    Object createBlankOrder(String jsonStr);

    /**
     * 获取相应订单信息.
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderInfo findOrderInfo(String orderId);
}
