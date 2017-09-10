package com.newland.financial.p2p.service;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.domain.entity.AbstractProduct;
import com.newland.financial.p2p.domain.entity.DebitAndCreditFactory;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import com.newland.financial.p2p.common.exception.OverloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import com.newland.financial.p2p.domain.entity.RepayALoanFactory;
import com.newland.financial.p2p.domain.entity.Interest;
import com.newland.financial.p2p.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;
/**
 * 贷款单的service数据处理层.
 * @author cendaijuan
 * */
@Service
public class DebitAndCreditServiceImpl implements IDebitAndCreditService {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**Dao层对象.*/
    @Autowired
    private ILenderDao lenderDao;
    /**Dao层对象.*/
    @Autowired
    private IProductDao productDao;
    /**Dao层对象.*/
    @Autowired
    private IInterestDao interestDao;
    /**Dao层对象.*/
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    /**Dao层对象.*/
    @Autowired
    private IRepayALoanDao repayALoanDao;

    /**
     * 进行贷款.
     * @param userId String用户编号
     * @param productId String产品编号
     * @param money BigDecimal贷款金额
     * @param interestId String利率编号
     * @throws OverloadException 贷款金额超出额度限制
     */
    public void createDebitAndCredit(final String userId,
            final String productId, final BigDecimal money,
            final String interestId) throws OverloadException {
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
     * @param userId String用户编号
     * @return List返回该用户所有的贷款信息
     */
    public List<DebitAndCredit> findDebitAndCreditHistory(final String userId) {
        return debitAndCreditDao.findByUserId(userId);
    }

    /**
     * 插入用户.
     * @param lender Lender对象
     * @return  boolean成功返回true,失败false
     * */
    public boolean insertLender(final Lender lender) {
        return lenderDao.insertLender(lender);
    }
    /**
     * 插入利率信息.
     * @param interest Interest对象
     * @return  boolean成功返回true,失败false
     * */
    public boolean insertInterest(final Interest interest) {
        return interestDao.insertInterest(interest);
    }
    /**
     * 插入产品.
     * @param product Product对象
     * @return  boolean成功返回true,失败false
     * */
    public boolean insertProduct(final Product product) {
        return productDao.insertProduct(product);
    }
    /**
     * 删除用户.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    public boolean deleteLender(final String userId) {
        return lenderDao.deleteLender(userId);
    }
    /**
     * 删除利率信息.
     * @param ittId String利率编号
     * @return  boolean成功返回true,失败false
     * */
    public boolean deleteInterest(final String ittId) {
        return interestDao.deleteInterest(ittId);
    }
    /**
     * 删除产品.
     * @param proId String产品编号
     * @return  boolean成功返回true,失败false
     * */
    public boolean deleteProduct(final String proId) {
        return productDao.deleteProduct(proId);
    }
    /**
     * 删除还款单.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    public boolean deleteRepayALoan(final String userId) {
        return repayALoanDao.deleteRepayAloan(userId);
    }
    /**
     * 删除贷款单.
     * @param userId String用户编号
     * @return  boolean成功返回true,失败false
     * */
    public boolean deleteDebitAndCredit(final String userId) {
        return debitAndCreditDao.deleteDebitAndCredit(userId);
    }
}
