package com.newland.financial.p2p.service;

import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;
import com.newland.financial.p2p.domain.entity.RepayALoan;

import java.math.BigDecimal;
import java.util.List;
/**
 * 定义对还款单操作的service接口.
 * @author cendaijuan
 * */
public interface IRepayALoanService {
    /**
     * 获取某用户的所有还款单.
     *@param userId String用户编号
     * @return List返回该用户所有的还款单
     * */
    List<RepayALoan> getRepayALoanList(String userId);

    /**
     * 还款.
     * @param  repayId String还款单编号
     * @throws  AlreadyRepayException 如之前已还则抛出异常
     */
    void repay(String repayId)  throws AlreadyRepayException;

    /**
     * 查询当月还款总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回本月应还款金额
     */
    BigDecimal getTotalMoney(String userId);

    /**
     * 已还总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回已还款金额
     */
    BigDecimal getTotalMoneyRepayed(String userId);

    /**
     * 未还总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回未还款总额
     */
    BigDecimal getTotalMoneyNeedRepay(String userId);

    /**
     * 获取当月应还 已还 未还总金额.
     * @param userId String用户编号
     * @return  DebitAndRepaySummary对象
     */
    DebitAndRepaySummary getDebitAndRepaySummary(String userId);

    /**
     * 更改status数值.
     * @param userId String用户编号
     * @return boolean成功true,失败false
     * */
    boolean updateSta(String userId);
}
