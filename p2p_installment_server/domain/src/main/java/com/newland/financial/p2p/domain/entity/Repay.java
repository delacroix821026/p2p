package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Mxia
 * 还款信息.
 */
@Setter
@Getter
public class Repay extends BaseEntity {
    /**主键Id.*/
    private String id;
    /**订单号.*/
    private String orderId;
    /**合同号.*/
    private String contractsCode;
    /**订单总金额,单位：分.*/
    private Long txnAmt;
    /**当期还款期数.*/
    private Integer terms;
    /**当期还款金额，单位：分.*/
    private Long amount;
    /**已还款金额,单位：分.*/
    private Long sumAmount;
    /**还款时间：yyyyMMddHHmmss.*/
//    @JSONField(format = "yyyyMMddHHmmss")
    private Date instalmentDate;
    /**下期还款日期,最后一期还款为空，格式：yyyyMMddHHmmss.*/
    private Date nextDate;
    /**还款类型:0：正常还款,1：提前还款.*/
    private String payType;
    /**还款状态码.*/
//    private String respCode;
    /**还款状态信息.*/
//    private String respMsg;
    /**响应时间.*/
    private Date respTime;
    /**还款订单和订单表一对一关联*/
    private OrderInfo orderInfo;
}
