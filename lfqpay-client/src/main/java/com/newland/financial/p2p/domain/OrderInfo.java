package com.newland.financial.p2p.domain;

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
    private Long txnAmt;
    /**分期数.*/
    private Integer txnterms;
    /**商户订单号.*/
    private String orderId;
    /**持卡人姓名.*/
    private String accName;
    /**信用卡账号.*/
    private String accNo;
    /**信用卡有效期.*/
    private String validity;
    /**持卡人身份证号.*/
    private String accIdcard;
    /**持卡人银行预留手机号码.*/
    private String accMobile;
    /**信用卡背面末三位数字.*/
    private String cvn2;
    /**手续费补贴金额.*/
    private Long discount;
    /**合同编号.*/
    private String contractsCode;
    /**首期还款金额.*/
    private Long amount;
    /**请求编号.*/
    private String queryId;
    /**持卡人分期手续费.*/
    private Long poundage;
    /**合同状态0：未生效 1：已生效 2：已结束(正常还款) 3：已结束(断供) 4：已结束(提前还款) 5：已结束(退款) 6：退款中 7：退款失败.*/
    private String contractsState;
    /**已还款总期数.*/
    private Integer sumTerms;
    /**已还款总金额.*/
    private Long sumAmount;
    /**剩余还款金额.*/
    private Long remainAmount;
    /**商户退款金额.*/
    private Long cancelAmount;
    /**商户退款利息.*/
    private Long cancelInterest;
    /**增值服务费.*/
    private Long valueAdded;
    /**创建时间.*/
    private Date createTime;
    /**0退款失败，1退款成功，2人工审核，无值或者null时表示订单没有退款行为.*/
    private String stus;
    /**响应码.*/
    private String respCode;
    /**响应信息.*/
    private String respMsg;
    /**商户名称.*/
    private String merName;
    /**新大陆处商户Id.*/
    private String merchantId;
}
