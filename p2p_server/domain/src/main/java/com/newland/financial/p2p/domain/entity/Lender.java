package com.newland.financial.p2p.domain.entity;

import com.newland.financial.p2p.common.exception.OverloadException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * describtion:lender用户信息实体类.
 *@author cendaijuan
 * */
@Setter
@Getter
public class Lender extends BaseEntity implements IPositionExchange {
    /**
     * describtion:标记额度的改变.
     *@param number 传入所需贷款的额度
     * */
    public void changePosition(BigDecimal number) {
        currentLmt = currentLmt.add(number);
    }
    /**
     *describtion:获取目前的额度.
     *@return BigDecimal 返回当前额度
     * */
    public BigDecimal getPosition() {
       return currentLmt;
    }
    /**
     * describtion:用于判断所贷金额是否超过额度.
     *@param position  所需贷款金额
     *@throws OverloadException 超出当前剩余额度异常
     * */
    public void canDebit(BigDecimal position) throws OverloadException {
        if (position.compareTo(currentLmt) > 0) {
            throw new OverloadException("个人额度不足，还剩：" + this.currentLmt);
        }
    }
    /**贷款人编号.*/
    private String userId;
    /**贷款单编号.*/
    private String loId;
    /**商户姓名.*/
    private String lenderName;
    /**贷款人手机号.*/
    private String phone;
    /**身份证号.*/
    private String identityCard;
    /**账户总额度.*/
    private BigDecimal totleLmt;
    /**当前额度.*/
    private BigDecimal currentLmt;
    /**本月待还金额.*/
    private BigDecimal repay;
    /**收款账户.*/
    private String inAct;
    /**收款账户发卡行.*/
    private String inBank;
    /**还款账户.*/
    private String outAct;
    /**还款账户发卡行.*/
    private String outBank;
    /**用户名.*/
    private String userName;
    /**行业.*/
    private String industry;
    /**地址.*/
    private String companyAddress;
    /**注册时间.*/
    private Date registTime;
}
