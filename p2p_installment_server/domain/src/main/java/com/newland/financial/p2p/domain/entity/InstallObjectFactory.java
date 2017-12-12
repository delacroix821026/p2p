package com.newland.financial.p2p.domain.entity;

import lombok.extern.log4j.Log4j;

import java.util.Date;

/**
 * @author Mxia
 * 封装报文类.
 */
@Log4j
public class InstallObjectFactory {
    /**
     * 创建订单请求报文信息封装.
     *
     * @param orderInfo 订单信息
     * @param merInfo 商户信息
     * @param smsCode 短信验证码
     * @return OrderMsgReq
     */
    public static OrderMsgReq installOrderMsgReq(OrderInfo orderInfo, MerInfo merInfo, String smsCode) {
        OrderMsgReq orm = new OrderMsgReq();
        orm.setTxnType("01");
        orm.setMerId(orderInfo.getMerId());
        log.info("merInfo:====" + (merInfo == null));
        log.info("===========" + merInfo.toString());
        orm.setMerPwd(merInfo.getMerPwd());
        orm.setMerName(merInfo.getMerName());
        orm.setMerAbbr(merInfo.getMerAbbr());
        orm.setTxnAmt(orderInfo.getTxnAmt() + "");
        orm.setTxnTerms(orderInfo.getTxnterms() + "");
        orm.setOrderId(orderInfo.getOrderId());
        orm.setAccNo(orderInfo.getAccNo());
        orm.setAccMobile(orderInfo.getAccMobile());
        orm.setCvn2(orderInfo.getCvn2());
        orm.setValidity(orderInfo.getValidity());
        orm.setAccName(orderInfo.getAccName());
        orm.setAccIdcard(orderInfo.getAccIdcard());
        orm.setSmsCode(smsCode);
        return orm;
    }

    /**
     * 封装订单查询请求报文.
     *
     * @param orderInfo 订单信息
     * @param merInfo 商户信息
     * @return OrderQueryReq
     */
    public static OrderQueryReq installOrderQueryReq(OrderInfo orderInfo, MerInfo merInfo) {
        OrderQueryReq oq = new OrderQueryReq();
        oq.setTxnType("73");
        oq.setMerId(merInfo.getMerId());
        oq.setMerName(merInfo.getMerName());
        oq.setMerPwd(merInfo.getMerPwd());
        oq.setMerAbbr(merInfo.getMerAbbr());
        oq.setOrderId(orderInfo.getOrderId());
        oq.setContractsCode(orderInfo.getContractsCode());
        return oq;
    }

    /**
     * 生成退款报文.
     *
     * @param orderInfo 订单信息
     * @return RefundMsgReq
     */
    public static RefundMsgReq installRefundMsgReq(OrderInfo orderInfo, MerInfo merInfo) {
        RefundMsgReq refundMsgReq = new RefundMsgReq();
        refundMsgReq.setTxnType("04");
        refundMsgReq.setMerId(orderInfo.getMerId());
        refundMsgReq.setMerPwd(merInfo.getMerPwd());
        refundMsgReq.setMerName(merInfo.getMerName());
        refundMsgReq.setMerAbbr(merInfo.getMerAbbr());
        refundMsgReq.setContractsCode(orderInfo.getContractsCode());
        Long beg = orderInfo.getTxnTime().getTime();
        Long end = new Date().getTime();
        log.info("beg:" + beg + ";end:" + end);
        if (end - beg > 3888000000L) {
            log.info("订单时间已经超过45天，无法退款");
            return null;
        }
        return refundMsgReq;
    }

}
