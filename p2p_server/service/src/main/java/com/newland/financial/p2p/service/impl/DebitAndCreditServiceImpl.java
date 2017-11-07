package com.newland.financial.p2p.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.dao.ILenderDao;
import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.dao.ICustomerFlowDebitDao;
import com.newland.financial.p2p.domain.entity.AbstractProduct;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import com.newland.financial.p2p.domain.entity.DebitAndCreditFactory;
import com.newland.financial.p2p.domain.entity.Lender;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import com.newland.financial.p2p.domain.entity.RepayALoanFactory;
import com.newland.financial.p2p.domain.entity.Interest;
import com.newland.financial.p2p.domain.entity.Product;
import com.newland.financial.p2p.domain.entity.DebitAndRepaySummary;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import com.newland.financial.p2p.service.IDebitAndCreditService;
import com.newland.financial.p2p.common.exception.OverloadException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * 贷款单的service数据处理层.
 *
 * @author cendaijuan
 */
@Log4j
@Service
public class DebitAndCreditServiceImpl implements IDebitAndCreditService {
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
     * Dao层对象.
     */
    @Autowired
    private ICustomerFlowDebitDao customerFlowDebitDao;

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

        log.debug("DebitAndCreditServiceImpl====:" + lender.toString());
        log.debug("DebitAndCreditServiceImpl====:" + product.toString());
        //创建贷款单
        DebitAndCredit debitAndCredit =
                DebitAndCreditFactory.createDebitAndCredit(
                        product, lender, money, interestId);
        //根据分期数生成相应还款单
        List<RepayALoan> repayALoanList = RepayALoanFactory.createRepayAloan(
                product, lender, money, interestId, debitAndCredit.getDtId());

        log.debug(debitAndCredit.toString());

        lenderDao.updateLender(lender); //更新用户信息
        //插入信息到相应数据库
        debitAndCreditDao.insertDebitAndCredit(debitAndCredit);
        for (int count = 0; count < repayALoanList.size(); count++) {
            log.debug(repayALoanList.get(count).toString());
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

    /**
     * 分页查询贷款单信息.
     *
     * @param jsonStr 分页信息
     * @return 结果集
     */
    public Object getDebitList(String jsonStr) {
        //debitAndCreditDao.updateStusToThree(); //将数据库内申请时间超过15天的贷款贷状态改为拒绝
        JSONObject paramJSON = JSON.parseObject(jsonStr);
        //获取分页信息
        Integer page = paramJSON.getInteger("page");
        Integer count = paramJSON.getInteger("count");
        if (page == null || page < 1) {
            page = 1;
        }
        if (count == null || count < 1) {
            count = 5;
        }
        //开始分页
        PageHelper.startPage(page, count);
        String proId = paramJSON.getString("proId");
        String proName = paramJSON.getString("proName");
        String oddNumbers = paramJSON.getString("oddNumbers");
        String contractNumber = paramJSON.getString("contractNumber");
        //转换日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long createTimeBeg = paramJSON.getLong("createTimeBeg");
        Long createTimeEnd = paramJSON.getLong("createTimeEnd");
        String begTime = null;
        String endTime = null;
        if (createTimeBeg != null) {
            Date begTimeDate = new Date(createTimeBeg);
            begTime = sdf.format(begTimeDate);
            log.debug("---------------------------------" + begTime);
        }
        if (createTimeEnd != null) {
            Date endTimeDate = new Date(createTimeEnd);
            endTime = sdf.format(endTimeDate);
            log.debug("---------------------------------" + endTime);
        }
        Map<String, Object> reqMap = new HashMap<String, Object>();
        if ("".equals(proId)) {
            reqMap.put("proId", null);
        } else {
            reqMap.put("proId", proId);
        }
        if ("".equals(proName)) {
            reqMap.put("proName", null);
        } else {
            reqMap.put("proName", proName);
        }
        if ("".equals(oddNumbers)) {
            reqMap.put("oddNumbers", null);
            log.debug("=====oddNumbers=====" + oddNumbers);
        } else {
            reqMap.put("oddNumbers", oddNumbers);
        }
        if ("".equals(contractNumber)) {
            reqMap.put("contractNumber", null);
        } else {
            reqMap.put("contractNumber", contractNumber);
        }
        reqMap.put("begTime", begTime);
        reqMap.put("endTime", endTime);
        List<CustomerFlowDebit> customerFlowDebitList = new ArrayList<CustomerFlowDebit>();
        customerFlowDebitList = customerFlowDebitDao.findAll(reqMap);
        PageInfo<CustomerFlowDebit> pageInfo = new PageInfo<CustomerFlowDebit>(customerFlowDebitList);
        return pageInfo;
    }
}
