package com.newland.financial.p2p.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Mxia
 * 订单信息实体.
 */
@Setter
@Getter
public class OrderInfo extends BaseEntity {
    /**主键Id.*/
    private String id;
    /**发送时间.*/
    private Date txnTime;
    /**商户代码.*/
    private String merId;
    /**订单总金额.*/
    private long txnAmt;
    /**分期数.*/
    private int txnterms;
    /**商户订单号.*/
    private String orderId;
    /**持卡人姓名.*/
    private String accName;
    /**信用卡账号.*/
    private String accNo;
    /**持卡人身份证号.*/
    private String accIdcard;
    /**持卡人银行预留手机号码.*/
    private String accMobile;
    /**信用卡背面末三位数字.*/
    private String CVN2;
    /**持卡人识别码.*/
    private String customerCode;
    /**手续费补贴金额.*/
    private long discount;
    /**合同编号.*/
    private String contractsCode;
    /**首期还款金额.*/
    private long amount;
    /**请求编号.*/
    private String queryId;
    /**持卡人分期手续费.*/
    private long poundage;
    /**合同状态0：未生效 1：已生效 2：已结束(正常还款) 3：已结束(断供) 4：已结束(提前还款) 5：已结束(退款).*/
    private int contractsState;
    /**已还款总期数.*/
    private int sumTerms;
    /**已还款总金额.*/
    private long sumAmount;
    /**剩余还款金额.*/
    private long remainAmount;
    /**商户退款金额.*/
    private long cancelAmount;
    /**商户退款利息.*/
    private long cancelInterest;
    /**增值服务费.*/
    private long valueAdded;
}
