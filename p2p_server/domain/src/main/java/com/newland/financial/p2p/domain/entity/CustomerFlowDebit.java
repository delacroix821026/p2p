package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * 流水贷扩展实体.
 * @author Mxia
 * */
@Setter
@Getter
public class CustomerFlowDebit extends DebitAndCredit {
    /**流水贷Id.*/
    private String id;
    /**贷款人姓名.*/
    private String applyName;
    /**身份证号码.*/
    private String identityCard;
    /**联系方式.*/
    private String phone;
    /**省.*/
    private String province;
    /**市.*/
    private String city;
    /**区或县.*/
    private String region;
    /**详细地址.*/
    private String detailAdd;
    /**记录是否有效：0无效，1有效.*/
    private String effective;
    /**是否发送：0未发送，1已发送.*/
    private String isSend = "0";
    /**申请单号.*/
    private String oddNumbers;
    /**星管家账号.*/
    private String starAccount;
    /**第三方合同号.*/
    private String contractNumber;
    /**应还总金额.*/
    private BigDecimal totleNeedRepay;
    /**已还总金额.*/
    private BigDecimal yetRepay;
    /**待还总金额.*/
    private BigDecimal needRepay;
    /**已还本金.*/
    private BigDecimal repayedCorpus;
    /**已还利息.*/
    private BigDecimal repayedInterest;
    /**待还本金.*/
    private BigDecimal unpayCorpus;
    /**待还利息.*/
    private BigDecimal unpayInterest;
    /**应还滞纳金.*/
    private BigDecimal feeOverdue;
    /**已还滞纳金.*/
    private BigDecimal repayedOverdue;
    /**已还手续费.*/
    private BigDecimal repayedSerFee;
}
