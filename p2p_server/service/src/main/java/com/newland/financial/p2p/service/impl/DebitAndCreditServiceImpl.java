package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.*;
import com.newland.financial.p2p.domain.entity.*;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import com.newland.financial.p2p.common.exception.OverloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 贷款单的service数据处理层.
 *
 * @author cendaijuan
 */
@Service
public class DebitAndCreditServiceImpl implements IDebitAndCreditService {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Dao层对象.
     */
    @Autowired
    private ILenderDao lenderDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IProductDao productDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IInterestDao interestDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    /**
     * Dao层对象.
     */
    @Autowired
    private IRepayALoanDao repayALoanDao;


    /**
     * 进行贷款.
     *
     * @param userId     String用户编号
     * @param productId  String产品编号
     * @param money      BigDecimal贷款金额
     * @param interestId String利率编号
     * @throws OverloadException 贷款金额超出额度限制
     */
    public void createDebitAndCredit(String userId,
                                     String productId, BigDecimal money,
                                     int interestId) throws OverloadException {
        Lender lender = lenderDao.findById(userId);
        AbstractProduct product = productDao.findById(productId);
        product.setInterestList(interestDao.findByProId((productId)));

        logger.info("DebitAndCreditServiceImpl====:" + lender.toString());
        logger.info("DebitAndCreditServiceImpl====:" + product.toString());
        //创建贷款单
        DebitAndCredit debitAndCredit =
                DebitAndCreditFactory.createDebitAndCredit(
                        product, lender, money, interestId);
        //根据分期数生成相应还款单
        List<RepayALoan> repayALoanList = RepayALoanFactory.createRepayAloan(
                product, lender, money, interestId, debitAndCredit.getDtId());

        logger.info(debitAndCredit.toString());

        lenderDao.updateLender(lender); //更新用户信息
        //插入信息到相应数据库
        debitAndCreditDao.insertDebitAndCredit(debitAndCredit);
        for (int count = 0; count < repayALoanList.size(); count++) {
            logger.info(repayALoanList.get(count).toString());
            repayALoanDao.insertRepayAloan(repayALoanList.get(count));
        }
    }

    /**
     * 查询历史借款记录.
     *
     * @param userId String用户编号
     * @return List返回该用户所有的贷款信息
     */
    public List<DebitAndCredit> findDebitAndCreditHistory(final String userId) {
        return debitAndCreditDao.findByUserId(userId);
    }

    /**
     * 插入用户.
     *
     * @param lender Lender对象
     * @return boolean成功返回true, 失败false
     */
    public boolean insertLender(final Lender lender) {
        return lenderDao.insertLender(lender);
    }

    /**
     * 插入利率信息.
     *
     * @param interest Interest对象
     * @return boolean成功返回true, 失败false
     */
    public boolean insertInterest(final Interest interest) {
        return interestDao.insertInterest(interest);
    }

    /**
     * 插入产品.
     *
     * @param product Product对象
     * @return boolean成功返回true, 失败false
     */
    public boolean insertProduct(final Product product) {
        return productDao.insertProduct(product);
    }

    /**
     * 删除用户.
     *
     * @param userId String用户编号
     * @return boolean成功返回true, 失败false
     */
    public boolean deleteLender(final String userId) {
        return lenderDao.deleteLender(userId);
    }

    /**
     * 删除利率信息.
     *
     * @param ittId String利率编号
     * @return boolean成功返回true, 失败false
     */
    public boolean deleteInterest(final String ittId) {
        //return interestDao.deleteInterest(ittId);
        return true;
    }

    /**
     * 删除产品.
     *
     * @param proId String产品编号
     * @return boolean成功返回true, 失败false
     */
    public boolean deleteProduct(final String proId) {
        return productDao.deleteProduct(proId);
    }

    /**
     * 删除还款单.
     *
     * @param userId String用户编号
     * @return boolean成功返回true, 失败false
     */
    public boolean deleteRepayALoan(final String userId) {
        return repayALoanDao.deleteRepayAloan(userId);
    }

    /**
     * 删除贷款单.
     *
     * @param userId String用户编号
     * @return boolean成功返回true, 失败false
     */
    public boolean deleteDebitAndCredit(final String userId) {
        return debitAndCreditDao.deleteDebitAndCredit(userId);
    }

    /**
     * 用户对应所有产品的贷款状态.
     *
     * @param userId 用户编号
     * @return 返回所有产品贷款状态
     */
    public Object findAllProStatus(String userId) {
        List<DebitAndCredit> list = debitAndCreditDao.findAllProStatus(userId);
        List<DebitAndRepaySummary> list1 = new ArrayList<DebitAndRepaySummary>();
        for (DebitAndCredit de : list) {
            if ("1".equals(de.getStus())) {
                DebitAndRepaySummary debitAndRepaySummary = new DebitAndRepaySummary();
                debitAndRepaySummary.setProId(de.getDProId()); //产品Id
                debitAndRepaySummary.setProName(de.getDProName()); //产品名称
                debitAndRepaySummary.setLoanMoney(de.getDMoney()); //借款金额
                debitAndRepaySummary.setRepayed(new BigDecimal("0")); //已还金额
                debitAndRepaySummary.setTotleRepay(new BigDecimal("0")); //应还总金额
                debitAndRepaySummary.setStatus(de.getStus()); //状态
                list1.add(debitAndRepaySummary);
            } else {
                DebitAndRepaySummary debitAndRepaySummary = new DebitAndRepaySummary();
                debitAndRepaySummary.setProId(de.getDProId()); //产品Id
                debitAndRepaySummary.setProName(de.getDProName()); //产品名称
                debitAndRepaySummary.setLoanMoney(de.getLoanMoney()); //借款金额
                debitAndRepaySummary.setTotleRepay(de.getYetPay().add(de.getUnPay())); //应还总金额
                debitAndRepaySummary.setRepayed(de.getYetPay()); //已还金额
                debitAndRepaySummary.setLoanDate(de.getLoanDate()); //放款时间
                debitAndRepaySummary.setLastRePayDate(de.getLastRePayDate()); //到期时间
                debitAndRepaySummary.setStatus(de.getStus()); //状态
                list1.add(debitAndRepaySummary);
            }

        }
        if (list1 != null) {
            return list1;
        }
        return false;
    }
}
