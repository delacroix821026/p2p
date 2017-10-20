package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对还款单进行操作的service类.
 *
 * @author cengdaijuan
 */
@Service
public class RepayALoanServiceImpl implements IRepayALoanService {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Dao层对象.
     */
    @Autowired
    private IRepayALoanDao repayALoanDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private ILenderDao lenderDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;

    /**
     * 获取某用户的所有还款单.
     *@param oddNumbers 申请单号
     * @return List返回该申请单号所有的还款单
     * */
    public List<RepayALoan> getRepayALoanList(String oddNumbers) {
        return repayALoanDao.findByOddNumbers(oddNumbers);
    }

    /**
     * 还款.
     *
     * @param repayId String还款单编号
     * @throws AlreadyRepayException 如之前已还则抛出异常
     */
    public void repay(final String repayId) throws AlreadyRepayException {
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
     *
     * @param userId String用户编号
     * @return BigDecimal返回本月应还款金额
     */
    public BigDecimal getTotalMoney(final String userId) {
        return repayALoanDao.findUnPay(userId);
    }

    /**
     * 已还总金额.
     *
     * @param userId String用户编号
     * @return BigDecimal返回已还款金额
     */
    public BigDecimal getTotalMoneyRepayed(final String userId) {
        return repayALoanDao.findYetPay(userId);
    }

    /**
     * 未还总金额.
     *
     * @param userId String用户编号
     * @return BigDecimal返回未还款总额
     */
    public BigDecimal getTotalMoneyNeedRepay(final String userId) {
        return repayALoanDao.findNeedPay(userId);
    }

    /**
     * 获取当月应还 已还 未还总金额.
     *
     * @param userId String用户编号
     * @return DebitAndRepaySummary对象
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
     *
     * @param userId String用户编号
     * @return boolean成功true, 失败false
     * @throws Exception has an erroe
     */
    public boolean updateSta(final String userId) throws Exception {
        repayALoanDao.updateStatus(userId);
        throw new Exception();
    }

    /**
     * 查询某用户对应产品的分期计划.
     *
     * @param userId 用户Id
     * @param proId  产品id
     * @return 分期计划信息集合
     */
    public Object findRepayAloanInfo(String userId, String proId) {
        //先去贷款表中查出相应有分期计划的贷款单编号，该记录只会有一条
        String debitId = debitAndCreditDao.selectDebitId(userId, proId);
        if (debitId != null && debitId.length() != 0){
            if (userId != null && userId.length() != 0 && proId != null && proId.length() != 0) {
                logger.info("RepayALoanServiceImpl------");
                DebitAndRepaySummary debitAndRepaySummary = new DebitAndRepaySummary();
                debitAndRepaySummary.setRepayInMonth(repayALoanDao.findUnPayByDebitId(debitId)); //本月应还金额
                debitAndRepaySummary.setRepayed(repayALoanDao.findYetPayByDebitId(debitId)); //已还金额
                debitAndRepaySummary.setNeedRepay(repayALoanDao.findNeedPayByDebitId(debitId)); //未还金额（待还金额）
                List<RepayALoan> list = repayALoanDao.findRepayAloanInfo(debitId);
                List result = new ArrayList();
                logger.info("debitAndRepaySummary=======" + debitAndRepaySummary.toString());
                result.add(debitAndRepaySummary);
                result.add(list);
                return result;
            }
        }
        logger.info("RepayALoanServiceImpl++++++");
        return false;
    }


}
