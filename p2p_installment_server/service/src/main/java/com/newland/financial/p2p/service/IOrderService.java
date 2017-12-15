package com.newland.financial.p2p.service;

import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.MerInfo;
import com.newland.financial.p2p.domain.entity.OrderInfo;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 订单处理Service.
 *
 * @author Gregory,Mxia
 */
public interface IOrderService {
    /**
     * 生成订单.
     *
     * @param jsonStr 订单信息.
     * @return 空白订单的编号
     */
    String createBlankOrder(String jsonStr);

    /**
     * 获取相应订单信息.
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderInfo findOrderInfo(String orderId);

    /**
     * 进行分期交易并更新订单.
     * @param jsonStr 请求信息
     * @return 返回创建订单请求报文.
     */
    Object tradeUpdateOrder(String jsonStr);

    /**
     * 查询商户信息.
     * @param orderInfo 包含商户id.
     * @return 商户信息
     */
    MerInfo findMerInfo(OrderInfo orderInfo);

    /**
     * 更新订单.
     * @param or 订单信息
     * @return true or false
     */
    boolean updateOrderInfo(OrderInfo or);
    /**
     * pos端查询单个订单详细信息.
     * @param orderId 订单号
     * @param merId 商户代码
     */
    OrderInfo findOrderInfoPos(String orderId, String merId);

    /**
     *  微信顾客查询订单.
     */
    PageInfo<OrderInfo> getOrderInfoListByCustomer(String jsonStr);
    /**
     *  Pos端订单查询(列表)
     */
    Object getOrderInfoListByMerchant(@PathVariable(name = "merchantId") String merchantId, String jsonStr);

    OrderInfo getOrderInfoDetailByMerchant(@PathVariable(name = "merchantId") String merchantId, @PathVariable(name = "orderId") String orderId);

    /**
     *  商户订单查询
     */
    Object getOrderInfoListByPlantManager(PageModel<OrderInfo> pageModel);

    OrderInfo getOrderInfoDetailByPlantManager(@PathVariable(name = "orderId") String orderId);
}
