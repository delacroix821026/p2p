package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.OrderInfo;

import java.util.List;
import java.util.Map;

/**
 *订单处理Dao.
 * @author Gregory,Mxia
 */
public interface IOrderInfoDao {
    /**
     * 插入订单信息.
     *
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    boolean insertOrder(OrderInfo orderInfo);

    /**
     * 更新订单.
     *
     * @param orderInfo 订单对象.
     * @return true or false.
     */
    boolean updateOrder(OrderInfo orderInfo);

    /**
     * 查询订单信息.
     *
     * @param orderId 订单号.
     * @return OrderInfo 订单对象.
     */
    OrderInfo selectOrderInfo(String orderId);

    /**
     * 删除订单.
     *
     * @param orderId 订单号.
     * @return true or false.
     */
    boolean deleteOrderInfo(String orderId);
    /**
     * pos端查询单个订单详细信息.
     *
     * @param orderId 订单号.
     * @param merId 商户代码.
     * @return 订单信息.
     */
   OrderInfo selectOrderInfoPos(String orderId, String merId);

    /**
     * 运营平台商户订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo>  findOrderInfoListByPlantManager(Map<String, Object> map);

    /**
     * 运营平台商户已结清订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo>  findOrderInfoListByFinish(Map<String, Object> map);
    /**
     * Pos端全部订单查询(列表).
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo>  getOrderInfoListByMerchant(Map<String, Object> map);
    /**
     * 运营平台商户还款中订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findRepayList(Map<String, Object> map);
    /**
     * 运营平台商户退款中订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findRefundList(Map<String, Object> map);
    /**
     * POS退款订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findRefundListPos(Map<String, Object> map);
    /**
     * 结清与未结清订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findOrderListPos(Map<String, Object> map);
    /**
     * 微信还款中订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findRepayWeixin(Map<String, Object> map);
    /**
     * 微信退款中订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findRefundWeixin(Map<String, Object> map);
    /**
     * 微信结清与全部订单查询.
     *
     * @param map 查询条件.
     * @return 信息集合.
     */
    List<OrderInfo> findByFinishWeixin(Map<String, Object> map);
    /**
     * 微信端查询订单信息.
     *
     * @param openId  微信Id.
     * @param orderId 订单id.
     * @return orderInfo.
     */
    OrderInfo findOrderInfoWeiXin(String openId, String orderId);
    /**
     * 平台查询订单详情.
     *
     * @param orderId 订单id.
     * @return orderInfo.
     */
    OrderInfo getOrderInfoByManager(String orderId);
}
