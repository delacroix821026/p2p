package com.newland.financial.p2p.service;

/**
 *订单处理Service.
 * @author Gregory
 */
public interface IOrderService {
    /**
     * 生成订单.
     * @param jsonStr 订单信息.
     * @return true or false
     */
    boolean createBlankOrder(String jsonStr);
}
