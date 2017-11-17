package com.newland.financial.p2p.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 创建订单应答报文.
 */
@Getter
@Setter
public class OrderMsgResp extends BaseEntityResp {
    /**订单总金额.*/
    private String txnAmt;
    /**分期期数.*/
    private String txnTerms;
    /**商户订单号.*/
    private String orderId;
    /**合同编号.*/
    private String contractsCode;
    /**持卡人识别码.*/
    private String customerCode;
    /**首期还款金额.*/
    private String amount;
    /**持卡人分期手续费.*/
    private String poundage;
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
