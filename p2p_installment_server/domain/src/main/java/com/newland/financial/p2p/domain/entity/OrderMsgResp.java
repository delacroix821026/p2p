package com.newland.financial.p2p.domain.entity;

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
    private Long txnAmt;
    /**分期期数.*/
    private Integer txnTerms;
    /**商户订单号.*/
    private String orderId;
    /**合同编号.*/
    private String contractsCode;
    /**持卡人识别码.*/
    private String customerCode;
    /**首期还款金额.*/
    private Long amount;
    /**持卡人分期手续费.*/
    private Long poundage;
    /**手续费补贴金额.*/
    private Long discount;
    /**抵押期数.*/
    private Integer pawnTerms;
    /**抵押金额.*/
    private Long pawnAmount;
    /**租用总金额.*/
    private Long rentAmount;
    /**租用每期金额.*/
    private Long monthAmount;
    /**首期其他费用.*/
    private Long firstPay;
    /**是否立即收货.*/
    private String receipt;

}
