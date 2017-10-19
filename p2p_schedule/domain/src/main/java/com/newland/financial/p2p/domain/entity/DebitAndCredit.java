package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷款单信息实体类.
 *@author Gregory
 * */
@Getter
@Setter
public class DebitAndCredit extends BaseEntity {
    /**
     * 默认构造.
     */
    public DebitAndCredit() {
    }

    /**
     * 贷款单编号.
     */
    private String dtId;
    /**
     * 利率编号.
     */
    private int dIttId;
    /**
     * 贷款人编号.
     */
    private String dLnrId;
    /**
     * 贷款产品编号.
     */
    private String dProId;
    /**
     * 产品名称.
     */
    private String dProName;
    /**
     * 产品限额.
     */
    private BigDecimal dProLmt;
    /**
     * 分期数.
     */
    private Integer dTimes;
    /**
     * 利率.
     */
    private BigDecimal dIttRa;
    /**
     * 贷款金额.
     */
    private BigDecimal dMoney;
    /**
     * 贷前额度.
     */
    private BigDecimal dBef;
    /**
     * 贷后额度.
     */
    private BigDecimal dAft;
    /**
     * 贷款日期.
     */
    private Date dDate;
    /**
     * 未还总额.
     */
    private BigDecimal unPay;
    /**
     * 已还总额.
     */
    private BigDecimal yetPay;
    /**
     * 最后还款日期.
     */
    private Date lastRePayDate;
    /**
     * 0已结清显示我的贷款，1申请中，2还款计划，3拒绝显示我的贷款.
     */
    private String stus;
}
