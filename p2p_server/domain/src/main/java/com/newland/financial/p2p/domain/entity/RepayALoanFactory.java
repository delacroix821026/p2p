package com.newland.financial.p2p.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;
import java.util.Calendar;

/**
 * 创建还款单的工厂.
 * @author cendaijuan
 * */
public class RepayALoanFactory {
    /**
     * 根据传进的参数进行还款单创建.
     * @param product IProduct 产品实体
     * @param lender IPositionExchange 用户实体
     * @param money BigDecimal 贷款金额
     * @param interestId String 利率编号
     * @param debitId String 贷款单编号
     * @return List 按照分期数返回相应size的一个还款单List集合
     * */
    public static List<RepayALoan> createRepayAloan(IProduct product,
        IPositionExchange lender, BigDecimal money,
         String interestId, String debitId) {
        List<RepayALoan> repayALoanList = new ArrayList<RepayALoan>();
        //按分期数遍历生成相应期数的还款单
        for (int count = 0;
             count < product.getByStages(interestId).intValue(); count++) {
            RepayALoan repayALoan = new RepayALoan();
            repayALoan.setReId(
                    UUID.randomUUID().toString().replaceAll("-", "")); //生成还款单编号
            repayALoan.setReLndId(lender.getUserId()); //还款人编号
            repayALoan.setReProId(product.getProId()); //产品编号
            repayALoan.setReProNmae(product.getProName()); //产品名称
            repayALoan.setReProLmt(product.getProLmt()); //产品限额
            repayALoan.setDebitId(debitId); //对应贷款单编号
            //本单应还金额
            repayALoan.setCrtRe(money.divide(new BigDecimal(
               product.getByStages(interestId)), 2, BigDecimal.ROUND_HALF_UP));
            repayALoan.setTotleMoney(repayALoan.getCrtRe());
            //Date date = product.getPayDate();
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(date);
            calendar.add(Calendar.MONTH, count + 1);
            Date payDate = calendar.getTime();

            /*Date date = product.getPayDate();
            Date payDate = new Date();
            payDate.setMonth(date.getMonth() + count + 1);*/
            repayALoan.setCreateDate(payDate); //指定还款日期
            repayALoanList.add(repayALoan);
        }
        return repayALoanList;
    }



}
