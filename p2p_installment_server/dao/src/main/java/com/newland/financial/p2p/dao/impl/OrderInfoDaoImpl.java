package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrderInfoDao;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * pos端查询单个订单详细信息.
     * @param orderId 订单号
     * @param merchantId 商户代码
     */
    public OrderInfo selectOrderInfoPos(String orderId, String merchantId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", orderId);
        map.put("merchantId", merchantId);
        return super.selectEntity("selectOrderPos", map);
    }
    /**
     * 微信顾客查询订单.
     * 跟据map查询
     */
    public List<OrderInfo> findOrderInfoDetailByCustomer(Map<String, Object> map){

        return super.select("findOrderByCustomer", map);
    }
    /**
     * 运营平台商户订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findOrderInfoListByPlantManager(Map<String, Object> map) {
        return super.select("findOrderByPlantByCustomer", map);
    }
    /**
     * 已结清订单
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findOrderInfoListByFinish(Map<String, Object> map) {
        return super.select("findOrderInfoListByFinish", map);
    }

    /**
     * Pos端全部订单查询(列表)
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo>  getOrderInfoListByMerchant(Map<String, Object> map){
        return super.select("getOrderByCustomer", map);
    }
    /**
     * 还款中订单
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findRepayList(Map<String, Object> map) {
        return super.select("findRepayList", map);
    }
    /**
     * 退款中订单
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findRefundList(Map<String, Object> map) {
        return super.select("findFundInfo", map);
    }
    /**
     * POS退款订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findRefundListPos(Map<String, Object> map) {
        return super.select("findRefundListPos", map);
    }
    /**
     * 结清与未结清订单查询
     * @param map 查询条件
     * @return 信息集合
     */
    public List<OrderInfo> findOrderListPos(Map<String, Object> map) {
        return super.select("findOrderListPos", map);
    }


}
