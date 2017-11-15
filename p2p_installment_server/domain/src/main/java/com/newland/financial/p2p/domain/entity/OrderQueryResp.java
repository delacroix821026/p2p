package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 订单查询应答报文.
 */
@Getter
@Setter
public class OrderQueryResp extends BaseEntityResp {
    /**商户订单号.*/
    private String orderId;
    /**合同号.*/
    private String contractsCode;
    /**订单总金额.*/
    private String txnAmt;
    /**合同状态.*/
    private String contractsState;
    /**已还款总期数.*/
    private Integer sumTerms;
    /**已还款总金额.*/
    private String sumAmount;
    /**剩余还款金额.*/
    private String remainAmount;
    /**商户退款金额.*/
    private String cancelAmount;
    /**商户退款利息.*/
    private String cancelInterest;
    /**增值服务费.*/
    private String valueAdded;
    /**商户退还金额.*/
    private String returnAmount;
    /**收货状态.*/
    private String receipt;
    /**原交易应答码.*/
    private String origRespCode;
    /**原交易应答信息.*/
    private String origRespMsg;
}
