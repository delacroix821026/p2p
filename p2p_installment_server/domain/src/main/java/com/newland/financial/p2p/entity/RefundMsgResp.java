package com.newland.financial.p2p.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 退款应答报文信息.
 */
@Getter
@Setter
public class RefundMsgResp extends BaseEntityResp {
    /**商户退款金额.*/
    private Long cancelAmount;
    /**退款状态.*/
    private String state;
    /**商户订单号.*/
    private String orderId;
    /**合同号.*/
    private String contractsCode;
}
