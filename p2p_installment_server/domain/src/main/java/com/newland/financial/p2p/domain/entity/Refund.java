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
    private String refundId;
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
    /**响应码.*/
    private String respCode;
    /**响应信息.*/
    private String respMsg;
    /**凭证状态：0:未上传，1:已上传.*/
    private String vocher;
    /**凭证上传时间.*/
    private Date sendTime;
    /**汇款凭证保存路径.*/
    private String path;
    /**申请退款金额.*/
    private Long applyCancelAmount;
}
