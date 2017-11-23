package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import org.springframework.stereotype.Repository;

/**
 * 订单处理DaoImpl.
 *
 * @author Gregory, Mxia
 */
@Repository
public class OrderInfoDaoImpl extends MybatisBaseDao<OrderInfo> implements IOrderInfoDao {
    /**
     * 插入订单信息.
     *
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    public boolean insertOrder(OrderInfo orderInfo) {
        return super.insert("insertOrder", orderInfo);
    }
    /**
     * 更新订单.
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    public boolean updateOrder(OrderInfo orderInfo) {
        return super.update("updateByOrderId", orderInfo);
    }
    /**
     * 查询订单信息.
     * @param orderId 订单号.
     * @return OrderInfo 订单对象.
     */
    public OrderInfo selectOrderInfo(String orderId) {
        return super.selectEntity("selectOrderInfo", orderId);
    }
    /**
     * 删除订单.
     * @param orderId 订单号
     * @return true or false
     */
    public boolean deleteOrderInfo(String orderId) {
        if (orderId == null || orderId.length() == 0) {
            return false;
        }
        return super.deletes("deleteByOrderId", orderId);
    }


}
