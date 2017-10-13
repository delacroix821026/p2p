package com.newland.financial.p2p.domain.entity;

import com.newland.financial.p2p.common.exception.OverloadException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 生成贷款单的工厂类.
 * @author cendaijuan
 */
public final class DebitAndCreditFactory {
    /**默认构造.*/
    private DebitAndCreditFactory() {
    }

    /**
     * 用以创建贷款单.
     * @param product    IProduct 产品对象
     * @param lender     IPositionExchange 用户对象
     * @param money      BigDecimal 贷款金额
     * @param interestId String 利率编号
     * @return DebitAndCredit 返回生成的贷款单实体
     * @throws OverloadException 所贷金额超出额度限定
     */
    public static DebitAndCredit createDebitAndCredit(IProduct product,
         IPositionExchange lender,
         BigDecimal money, int interestId)
          throws OverloadException {
        DebitAndCredit debitAndCredit = new DebitAndCredit(lender);
        debitAndCredit.setDtId(UUID.randomUUID().toString().replaceAll(
                "-", "")); //生成一个唯一贷款单主键Id
        lender.canDebit(money); //判断额度是否足够，不够则抛出异常
        product.canDebit(money); //判断所贷额度是都超过产品本身限额，超过则抛出异常
        debitAndCredit.debit(money);
        debitAndCredit.setDLnrId(lender.getUserId()); //贷款人编号
        debitAndCredit.setDProId(product.getProId()); //贷款产品编号
        debitAndCredit.setDProName(product.getProName()); //产品名称
        debitAndCredit.setDProLmt(product.getProLmt()); //产品限额
        //根据interestId获得分期数
        debitAndCredit.setDTimes(product.getByStages(interestId));
        //根据interestId获得月利率
        debitAndCredit.setDIttRa(product.getInterest(interestId));
        debitAndCredit.setDIttId(interestId);
        debitAndCredit.setDDate(new Date()); //贷款日期

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,
                product.getByStages(interestId).intValue());
        debitAndCredit.setLastRePayDate(calendar.getTime()); //最终还款日期

        return debitAndCredit;
    }

}
