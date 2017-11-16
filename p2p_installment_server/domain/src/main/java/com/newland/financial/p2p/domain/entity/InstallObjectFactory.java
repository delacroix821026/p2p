package com.newland.financial.p2p.domain.entity;

/**
 * @author Mxia
 * 封装报文类.
 */
public class InstallObjectFactory {
    public static OrderMsgReq installOrderMsgReq(OrderInfo orderInfo, MerInfo merInfo, String smsCode) {
        OrderMsgReq orm = new OrderMsgReq();
        orm.setTxnType("01");
        orm.setMerId(orderInfo.getMerId());
        orm.setMerPwd(merInfo.getMerPwd());
        orm.setMerName(merInfo.getMerName());
        orm.setMerAbbr(merInfo.getMerAbbr());
        orm.setTxnAmt(orderInfo.getTxnAmt()+"");
        orm.setTxnTerms(orderInfo.getTxnterms()+"");
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
}
