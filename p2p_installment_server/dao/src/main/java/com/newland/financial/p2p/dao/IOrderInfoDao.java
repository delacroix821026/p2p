package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.OrderInfo;

/**
 *订单处理Dao.
 * @author Gregory,Mxia
 */
public interface IOrderInfoDao {
    /**
     * 插入订单信息.
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    boolean insertOrder(OrderInfo orderInfo);

    /**
     * 更新订单.
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    boolean updateOrder(OrderInfo orderInfo);

    /**
     * 查询订单信息.
     * @param orderId 订单号.
     * @return OrderInfo 订单对象.
     */
    OrderInfo selectOrderInfo(String orderId);

    /**
     * 删除订单.
     * @param orderId 订单号
     * @return true or false
     */
    boolean deleteOrderInfo(String orderId);
    /**
     * pos端查询单个订单详细信息.
     * @param orderId 订单号
     * @param merId 商户代码
     */
   OrderInfo selectOrderInfoPos(String orderId, String merId);
}
