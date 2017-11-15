package com.newland.financial.p2p.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 还款推送报文.
 */
@Getter
@Setter
public class RepayMsgResp extends BaseEntityResp {
    /**订单总金额.*/
    private Long txnAmt;
    /**还款期数.*/
    private Integer terms;
    /**还款金额.*/
    private Long amount;
    /**已还款金额.*/
    private Long sumAmount;
    /**还款时间.*/
    private String instalmentDate;
    /**下期还款日期.*/
    private String nextDate;
    /**合同号.*/
    private String contractsCode;
    /**订单号.*/
    private String orderId;
    /**还款类型.*/
    private String payType;
}
