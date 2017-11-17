package com.newland.financial.p2p.service.impl;

import com.lfq.pay.client.MpiUtil;
import com.newland.financial.p2p.Utils.IfqUtil;
import com.newland.financial.p2p.domain.MethodFactory;
import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.Map;

/**
 * @author Mxia
 */
@Log4j
public class OrderService implements IOrderService {
    // 外网测试地址
    public static final String ADDRESS_TEST = "https://tt.lfqpay.com:343";
    // 本地开发地址
    public static final String ADDRESS_DEVELOP = "https://tt.lfqpay.com:343";

    public Object sendOrderMsg(OrderMsgReq orm) throws IOException {
        log.info("====come in ybf service====");
        String requestUrlA = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/backTransRequest.do"; // 创建订单地址.
        String requestUrlB = ADDRESS_DEVELOP + "/lfq-pay/gateway/api/singleQueryRequest.do"; // 查询订单地址.
        // 首次创建订单.
        Map<String, String> map1 = MethodFactory.initOrderData(orm);
        MpiUtil.sign(map1, "utf-8"); // 签名
        Map<String, String> mapA = IfqUtil.execute(requestUrlA, map1);
        // 根据创建订单返回的报文，查询订单，获得合同状态.
        Map<String, String> map2 = MethodFactory.initQueryOrderDate(mapA, orm.getMerPwd());
        Map<String, String> mapB = IfqUtil.execute(requestUrlB, map2);
        OrderInfo od = MethodFactory.installOrderInfo(mapA, mapB, orm);
        return od;
    }
}
