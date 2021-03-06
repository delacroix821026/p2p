package com.newland.financial.p2p.service.impl;

import com.lfq.pay.client.MpiUtil;
import com.lfq.pay.client.SecureUtil;
import com.newland.financial.p2p.Utils.IfqUtil;
import com.newland.financial.p2p.domain.MethodFactory;
import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;
import com.newland.financial.p2p.domain.RefundMsgReq;
import com.newland.financial.p2p.domain.Refund;
import com.newland.financial.p2p.service.IOrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mxia
 */
@Log4j
@Service
public class OrderService implements IOrderService {
    /**
     * 还款推送URL.
     */
    @Value("${IFQ_REPAY_ADDRESS}")
    private String repayUrl;
    /**创建订单地址.*/
    @Value("${IFQ_CREATE_ADDRESS}")
    private String createUrl;
    /**查询订单地址.*/
    @Value("${IFQ_QUERY_ADDRESS}")
    private String queryUrl;
    /**退款请求地址.*/
    @Value("${backCancel_address}")
    private String backCancelUrl;
    /**退款异步推送地址.*/
    @Value("${backTellUrl}")
    private String backTellUrl;

    /**
     * 创建订单.
     *
     * @param orm 请求参数
     * @return 订单信息
     * @throws IOException an error
     */
    public OrderInfo sendOrderMsg(OrderMsgReq orm) throws IOException {
        log.info("====come in ybf service====");
        String requestUrlA = createUrl; // 创建订单地址.
        String requestUrlB = queryUrl; // 查询订单地址.
        log.info("创建订单地址：" + requestUrlA);
        log.info("查询订单地址：" + requestUrlB);

        // 首次创建订单.
        Map<String, String> map1 = MethodFactory.initOrderData(orm);
        map1.put("backUrl", repayUrl);
        log.info("----------------repayUrl-------------->:" + repayUrl);
        MpiUtil.sign(map1, "utf-8"); // 签名

        Map<String, String> mapA = IfqUtil.execute(requestUrlA, map1);

        // 根据创建订单返回的报文，查询订单，获得合同状态.
        Map<String, String> map2 = MethodFactory.initQueryOrderDate(mapA, orm.getMerPwd());
        MpiUtil.sign(map2, "utf-8"); // 签名

        Map<String, String> mapB = IfqUtil.execute(requestUrlB, map2);
        OrderInfo od = MethodFactory.installOrderInfo(mapA, mapB, orm);
        return od;
    }

    /**
     * 查询单个订单.
     *
     * @param oqr 请求参数
     * @return 订单信息
     * @throws IOException an error
     */
    public Object findOrderInfo(OrderQueryReq oqr) throws IOException {
        String requestUrl = queryUrl;
        Map<String, String> map = new HashMap<String, String>();
        map.put("version", "1.0.0"); // 固定值：1.0.0
        map.put("encoding", "utf-8"); // 编码
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        map.put("txnTime", txnTime); // 发送时间：yyyyMMddHHmmss
        map.put("txnType", "73"); // 查询:73
        map.put("merId", oqr.getMerId());
        map.put("merPwd", SecureUtil.encryptWithDES(txnTime, oqr.getMerPwd()));
        map.put("merName", oqr.getMerName());
        map.put("merAbbr", oqr.getMerAbbr());
        map.put("orderId", oqr.getOrderId());
        //map.put("contractsCode", oqr.getContractsCode());
        log.info("=============6:查询报文内容 begin================");
        for (String key : map.keySet()) {
            log.info("key= " + key + " and value= " + map.get(key));
        }
        log.info("=============7:查询报文内容 end================");
        MpiUtil.sign(map, "utf-8"); // 签名
        Map<String, String> mapA = IfqUtil.execute(requestUrl, map);
        OrderInfo or = MethodFactory.installOrderInfoA(mapA);
        return or;
    }
    /**
     * 发送退款请求.
     * @param re 退款报文
     * @return 退款结果
     */
    public Refund senOrderQueryMsg(RefundMsgReq re) {
        RefundMsgReq ref = re;
        Map<String, String> map = MethodFactory.installRefundReqMsg(re);
        map.put("backUrl", backTellUrl); // 异步推送地址
        MpiUtil.sign(map, "utf-8"); // 签名
        Map<String, String> mapA = IfqUtil.execute(backCancelUrl, map);
        Refund refund = MethodFactory.installRefund(mapA);
        return refund;
    }
}
