package com.newland.financial.p2p.domain.entity;

import com.newland.financial.p2p.common.exception.OverloadException;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 定义产品类接口.
 * @author cendaijuan
 * */
public interface IProduct {
    /**
     * 获取利率.
     * @param id 利率编号
     * @return 利率
     * */
    BigDecimal getInterest(int id);
    /**
     * 获取分期数.
     * @param id 利率编号
     * @return 分期数
     * */
    Integer getByStages(int id);
    /**
     * 获取月利率.
     *@param id String 利率编号
     * @return BigDecimal 月利率
     * */
    //BigDecimal getInterestByMonth(String id);
    /**
     * @throws OverloadException 所贷金额超出产品本身的限定额度.
     *@param position BigDecimal 贷款金额
     * */
    void canDebit(BigDecimal position) throws OverloadException;
    /**
     * 获取产品id.
     *@return 产品id
     * */
    String getProId();
    /**
     * 获取产品名称.
     *@return 产品名称
     * */
    String getProName();
    /**
     * 获取产品限额.
     *@return 产品限额
     * */
    BigDecimal getProLmt();
    /**
     * 获取产品指定账单日.
     *@return 产品指定还款日期
     * */
    Date getPayDate();

}
