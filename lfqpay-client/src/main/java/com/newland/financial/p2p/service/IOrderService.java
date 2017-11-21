package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;

import java.io.IOException;

/**
 * @author Mxia
 */
public interface IOrderService {
    /**
     * 创建订单.
     * @param orm 请求报文
     * @return 订单信息
     * @throws IOException an error
     */
    OrderInfo sendOrderMsg(OrderMsgReq orm) throws IOException;

    /**
     * 查询订单.
     * @param oqr 请求参数
     * @return 订单信息.
     * @throws IOException an error
     */
    Object findOrderInfo(OrderQueryReq oqr) throws IOException;
}
