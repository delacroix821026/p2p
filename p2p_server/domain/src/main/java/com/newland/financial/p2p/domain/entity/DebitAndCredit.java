package com.newland.financial.p2p.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 贷款单信息实体类.
 *@author cendaijuan
 * */
@Getter
@Setter
public class DebitAndCredit extends BaseEntity {
    /**
     * 默认构造.
     * */
    public DebitAndCredit() { }
    /**
     * 有参构造.
     *@param pe 传入Lender
     * */
    public DebitAndCredit(final IPositionExchange pe) {
        this.positionExchange = pe;
    }
    /**
     *lender类的接口，用以计算贷款前后额度.
     * */
    private IPositionExchange positionExchange;

    /**
     * 根据所贷金额,来修改贷前贷后剩余的额度.
     * @param money 所需贷款金额
     */
    public void debit(final BigDecimal money) {
        dMoney = money; //贷款金额
        dBef = positionExchange.getPosition(); //记录贷款或还款前额度
        positionExchange.changePosition(
                new BigDecimal(0).subtract(money)); //减少或增加相应额度
        dAft = positionExchange.getPosition(); //记录贷款或还款后额度
    }
    /**贷款单编号.*/
    private String dtId;
    /**利率编号.*/
    private int dIttId;
    /**贷款人编号.*/
    private String dLnrId;
    /**贷款产品编号.*/
    private String dProId;
    /**产品名称.*/
    private String dProName;
    /**产品限额.*/
    private BigDecimal dProLmt;
    /**分期数.*/
    private Integer dTimes;
    /**利率.*/
    private BigDecimal dIttRa;
    /**贷款金额.*/
    private BigDecimal dMoney;
    /**贷前额度.*/
    private BigDecimal dBef;
    /**贷后额度.*/
    private BigDecimal dAft;
    /**贷款日期.*/
    private Date dDate;
    /**未还总额.*/
    private BigDecimal unPay;
    /**已还总额.*/
    private BigDecimal yetPay;
    /**最后还款日期.*/
    private Date lastRePayDate;
    /**0已结清显示我的贷款，1申请中，2还款计划，3拒绝显示我的贷款.*/
    private String stus;
     /**放款日期.*/
     private Date loanDate;
}
