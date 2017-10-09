package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.service.ILenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 对用户进行操作的service类.
 * @author cendaijuan
 * */
@Service
public class LenderServiceImpl implements ILenderService {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**Dao层对象.*/
    @Autowired
    private ILenderDao lenderDao;
    /**Dao层对象.*/
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    /**Dao层对象.*/
    @Autowired
    private IRepayALoanDao repayALoanDao;
    /**
     * 获取用户信息.
     * @param userId String用户编号
     * @return Lender返回用户信息.
     * */
    public Lender getLender(String userId) {
        logger.info("LenderService:lenderId(userId)--:" + userId);
        return lenderDao.findById(userId);
    }
    /**
     * 清楚用户数据,目前禁止使用.
     *@param userId String用户编号
     * */
    public void clear(String userId) {
        logger.info("LenderService:clear(userId)--:" + userId);
        debitAndCreditDao.deleteDebitAndCredit(userId);
        repayALoanDao.deleteRepayAloan(userId);
        lenderDao.getBack(userId);
    }
}
