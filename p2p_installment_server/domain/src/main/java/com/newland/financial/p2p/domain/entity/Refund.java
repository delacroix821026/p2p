package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Mxia
 * 退款信息.
 */
@Setter
@Getter
public class Refund extends BaseEntity {
    /**退款单号.*/
    private String redundId;
    /**发送时间.*/
    private Date txnTime;
    /**商户代码.*/
    private String merId;
    /**合同号.*/
    private String contractsCode;
    /**商户退款金额.*/
    private Long cancelAmount;
    /**退款状态(0审核中，1退款成功，2退款失败).*/
    private String state;
    /**商户订单号.*/
    private String orderId;
    /**请求编号.*/
    private String queryId;
    /**退款订单和订单表一对一关联*/
    private OrderInfo orderInfo;
}
