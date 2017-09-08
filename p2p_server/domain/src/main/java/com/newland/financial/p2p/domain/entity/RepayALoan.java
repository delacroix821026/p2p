package com.newland.financial.p2p.domain.entity;

import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 还款单信息实体类.
 * @author cendaijuan
 * */
@Setter
@Getter
public class RepayALoan extends BaseEntity {
    /**
     * 无参构造器.
     * */
    public RepayALoan() {

    }
    /**
     * @param pe IPositionExchange的实现类Lender
     * */
    public RepayALoan(final IPositionExchange pe) {
        this.positionExchange = pe;
    }
    /**IPositionExchange的实现类Lender.*/
    private IPositionExchange positionExchange;
    /**
     * 对还款单进行还款.
     * @throws AlreadyRepayException 如果该还款单之前已还，则会抛出异常
     */
    public void repay() throws AlreadyRepayException {
        if (this.status == 1) {
            throw new AlreadyRepayException("该还款批次已还清！");
        }
        status = 1; //状态变更已还款
        expireDate = new Date();
        reBef = positionExchange.getPosition(); //记录还款前额度
        positionExchange.changePosition(crtRe); //增加额度
        reAft = positionExchange.getPosition(); //记录还款后额度
    }
    /**还款单编号.*/
    private String reId;
    /**还款人编号.*/
    private String reLndId;
    /**还款方式.*/
    private String reMtd;
    /**产品编号.*/
    private String reProId;
    /**产品名称.*/
    private String reProNmae;
    /**产品限额.*/
    private BigDecimal reProLmt;
    /**本次还款本金.*/
    private BigDecimal crtRe;
    /**指定还款日期.*/
    private Date createDate;
    /**实际还款日期.*/
    private Date expireDate;
    /**还款前额度.*/
    private BigDecimal reBef;
    /**还款后额度.*/
    private BigDecimal reAft;
    /**是否已还 0 未 1 是.*/
    private int status = 0;
    /**利息.*/
    private BigDecimal crtInterest;
    /**手续费.*/
    private BigDecimal serMoney;
    /**其他费用.*/
    private BigDecimal otherMoney;
    /**总金额.*/
    private BigDecimal totleMoney;
    /**贷款单编号.*/
    private String debitId;
}
