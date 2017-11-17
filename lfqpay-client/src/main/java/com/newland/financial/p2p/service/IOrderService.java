package com.newland.financial.p2p.service;

import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;

import java.io.IOException;

/**
 * @author Mxia
 */
public interface IOrderService {
    Object sendOrderMsg(OrderMsgReq orm) throws IOException;
}
