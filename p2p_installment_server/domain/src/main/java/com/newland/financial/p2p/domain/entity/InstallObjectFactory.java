package com.newland.financial.p2p.domain.entity;

/**
 * @author Mxia
 * 封装报文类.
 */
public class InstallObjectFactory {
    /**
     * 创建订单请求报文信息封装.
     *
     * @param orderInfo
     * @param merInfo
     * @param smsCode
     * @return
     */
    public static OrderMsgReq installOrderMsgReq(OrderInfo orderInfo, MerInfo merInfo, String smsCode) {
        OrderMsgReq orm = new OrderMsgReq();
        orm.setTxnType("01");
        orm.setMerId(orderInfo.getMerId());
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
     * @param orderInfo
     * @param merInfo
     * @return
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

}
