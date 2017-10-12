package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.common.exception.AlreadyRepayException;
import com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import com.newland.financial.p2p.service.IRepayALoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
/**
 * 对还款单进行操作的service类.
 * @author cengdaijuan
 * */
@Service
public class RepayALoanServiceImpl implements IRepayALoanService {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**Dao层对象.*/
    @Autowired
    private IRepayALoanDao repayALoanDao;
    /**Dao层对象.*/
    @Autowired
    private ILenderDao lenderDao;
    /**
     * 获取某用户的所有还款单.
     *@param userId String用户编号
     * @return List返回该用户所有的还款单
     * */
    public List<RepayALoan> getRepayALoanList(final String userId) {

        return repayALoanDao.findByUserId(userId);
    }

    /**
     * 还款.
     * @param  repayId String还款单编号
     * @throws  AlreadyRepayException 如之前已还则抛出异常
     */
    public void repay(final String repayId)  throws AlreadyRepayException {
        RepayALoan repayALoan = repayALoanDao.findByReId(repayId);
        Lender lender = lenderDao.findById(repayALoan.getReLndId());
        logger.info("service--lender----------:" + lender.toString());
        repayALoan.setPositionExchange(lender);
        repayALoan.repay();

        logger.info(repayALoan.toString());

        repayALoanDao.updateRepayAloan(repayALoan);
        lenderDao.updateLender(lender);
    }

    /**
     * 查询当月还款总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回本月应还款金额
     */
    public BigDecimal getTotalMoney(final String userId) {
        return repayALoanDao.findUnPay(userId);
    }

    /**
     * 已还总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回已还款金额
     */
    public BigDecimal getTotalMoneyRepayed(final String userId) {
        return repayALoanDao.findYetPay(userId);
    }

    /**
     * 未还总金额.
     * @param userId String用户编号
     * @return  BigDecimal返回未还款总额
     */
    public BigDecimal getTotalMoneyNeedRepay(final String userId) {
        return repayALoanDao.findNeedPay(userId);
    }
    /**
     * 获取当月应还 已还 未还总金额.
     * @param userId String用户编号
     * @return  DebitAndRepaySummary对象
     */
    public DebitAndRepaySummary getDebitAndRepaySummary(final String userId) {
        DebitAndRepaySummary debitAndRepaySummary = new DebitAndRepaySummary();
        debitAndRepaySummary.setRepayInMonth(getTotalMoney(userId)); //本月应还金额
        debitAndRepaySummary.setRepayed(getTotalMoneyRepayed(userId)); //已还金额
        //未还金额
        debitAndRepaySummary.setNeedRepay(getTotalMoneyNeedRepay(userId));

        return debitAndRepaySummary;
    }

    /**
     * 更改status数值.
     * @param userId String用户编号
     * @return boolean成功true,失败false
     * */
    public boolean updateSta(final String userId) throws Exception{
        repayALoanDao.updateStatus(userId);
        throw new Exception();
    }

}
