package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定义未还,已还,本月应还的实体类.
 * @author cendaijuan
 * */
@Setter
@Getter
public class DebitAndRepaySummary {
    /**产品编号.*/
    private String proId;
    /**产品名称.*/
    private String proName;
    /**借款金额.*/
    private BigDecimal loanMoney;
    /**应还总金额.*/
    private BigDecimal totleRepay;
    /**
     * 未还.
     * */
    private BigDecimal needRepay;
    /**
     * 已还.
     * */
    private BigDecimal repayed;
    /**
     *本月应还.
     * */
    private BigDecimal repayInMonth;
    /**放款日期.*/
    private Date loanDate;
    /**到期时间.*/
    private Date lastRePayDate;
    /**状态,1审核中，2还款计划.*/
    private String status;
}
