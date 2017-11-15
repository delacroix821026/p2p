package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 订单查询请求报文信息.
 */
@Setter
@Getter
public class OrderQueryReq extends BaseEntityReq {
    /**商户订单号.*/
    private String orderId;
    /**合同号.*/
    private String contractsCode;
}
