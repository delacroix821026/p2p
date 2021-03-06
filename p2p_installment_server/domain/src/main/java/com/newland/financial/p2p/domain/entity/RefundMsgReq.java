package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mxia
 * 退款请求报文.
 */
@Setter
@Getter
public class RefundMsgReq extends BaseEntityReq {
    /**合同号.*/
    private String contractsCode;
    /**异步通知地址.*/
    private String backUrl;
}
