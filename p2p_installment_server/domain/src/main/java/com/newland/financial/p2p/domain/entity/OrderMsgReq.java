package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建订单请求报文信息.
 * @author Mxia
 */
@Setter
@Getter
public class OrderMsgReq extends BaseEntityReq {
    /**订单总金额.*/
    private String txnAmt;
    /**分期期数.*/
    private String txnTerms;
    /**商户摘要.*/
    private String merNote;
    /**前台通知地址.*/
    private String frontUrl;
    /**异步通知地址.*/
    private String backUrl;
    /**商户订单号.*/
    private String orderId;
    /**帐号卡号.*/
    private String accNo;
    /**持卡人姓名.*/
    private String accName;
    /**信用卡有效期.*/
    private String validity;
    /**持卡人银行预留手机号码.*/
    private String accMobile;
    /**信用卡背面末三位数字.*/
    private String cvn2;
    /**持卡人身份证号.*/
    private String accIdcard;
    /**短信验证码.*/
    private String smsCode;
    /**分期限制.*/
    private String txnTermsList;
    /**持卡人识别码.*/
    private String customerCode;
    /**有效时间.*/
    private String validTime;
    /**终端号.*/
    private String termId;
    /**终端信息域.*/
    private String userMac;
    /**终端类型.*/
    private String termType;
    /**合同类型.*/
    private String merType;
    /**手续费补贴金额.*/
    private String discount;
    /**抵押期数.*/
    private String pawnTerms;
    /**抵押金额.*/
    private String pawnAmount;
    /**租用总金额.*/
    private String rentAmount;
    /**租用每期金额.*/
    private String monthAmount;
    /**首期其他费用.*/
    private String firstPay;
    /**是否立即收货.*/
    private String receipt;
}
