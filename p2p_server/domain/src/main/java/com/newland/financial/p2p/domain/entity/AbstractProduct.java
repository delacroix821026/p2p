package com.newland.financial.p2p.domain.entity;


import com.newland.financial.p2p.common.exception.OverloadException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 抽象的产品实体类，可被继承.
 *@author cendaijuan
 * */
@Setter
@Getter
public abstract class AbstractProduct extends BaseEntity implements IProduct {
    /**
     * 获取相应利率编号的具体利率.
     *@param id Interest中的编号
     * @return BigDecimal 返回对应编号的利率
     * */
    public BigDecimal getInterest(final String id) {
        Interest interest = getInterestById(id);
        return interest != null ? interest.getIntRate() : new BigDecimal(0);
    }
    /**
     * 获取相应利率编号的分期数.
     *@param id Interest中的编号
     * @return Integer 返回对应编号的分期数
     * */
    public Integer getByStages(final String id) {
        Interest interest = getInterestById(id);
        return interest != null ? interest.getTimes() : 0;
    }
    /**
     * 月利率计算,暂时不被引用.
     *@param id 利率编号
     * @return BigDecimal 返回产品的月利率
     * */
    public BigDecimal getInterestByMonth(final String id) {
        return getInterest(id).divide(new BigDecimal(getByStages(id)).multiply(
                new BigDecimal(100)), 4, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 一个产品会根据分期数有不同的利率信息,根据传进来的利率编号进行匹配需要使用哪一条利率.
     *@param id 利率编号
     * @return 返回对应利率编号的利率信息
     * */
    private Interest getInterestById(final String id) {
        for (int count = 0; count < interestList.size(); count++) {
            Interest interest = interestList.get(count);
            if (interest.getIttId().equals(id)) {
                return interest;
            }
        }
        return null;
    }
    /**
     * 判断额度是否超过限制.
     *@param position 所需贷款金额
     * @throws OverloadException 超过产品限定的额度
     * */
    public void canDebit(final BigDecimal position) throws OverloadException {
        if (position.compareTo(this.proLmt) > 0) {
            throw new OverloadException("额度超过产品限额");
        }
    }
    /**
     *产品编号.
     * */
    private String proId;
    /**
     *管理员所看到的产品名称.
     * */
    private String proName;
    /**
     *产品限额.
     * */
    private BigDecimal proLmt;
    /**
     *产品还款日期.
     * */
    private Date payDate;
    /**
     *产品利率.
     * */
    private BigDecimal proInterest;
    /**
     * 利率信息.
     * */
    private List<Interest> interestList;
    /**
     *操作员所看到的产品名称.
     */
    private String proNameOperator;
}
