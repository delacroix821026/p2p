package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IRepayALoanDao;
import com.newland.financial.p2p.domain.entity.RepayALoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

/**
 * 还款单Dao层操作类.
 *
 * @author Mxia
 */
@Repository
public class RepayALoanDao extends MybatisBaseDao<RepayALoan>
        implements IRepayALoanDao {
    /**
     * 日志对象.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 增加还款单.
     *
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true, 失败false
     */
    public boolean insertRepayAloan(final RepayALoan repayALoan) {
        return super.insertSelective(repayALoan);
    }

    /**
     * 修改还款单.
     *
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true, 失败false
     */
    public boolean updateRepayAloan(final RepayALoan repayALoan) {
        return super.updateByPrimaryKeySelective(repayALoan);
    }

    /**
     * 获取某用户的所有还款单.
     *
     * @param oddNumbers 申请单号
     * @return List返回该申请单号所有的还款单
     */
    public List<RepayALoan> findByOddNumbers(String oddNumbers) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (oddNumbers != null && oddNumbers.length() != 0) {
            map.put("oddNumbers", oddNumbers);
            return super.select("selectByPropertie", map);
        }
        return null;
    }

    /**
     * 根据还款单编号查询相应的还款单.
     *
     * @param reId String还款单编号
     * @return RepayALoan返回相应编号的还款单信息
     */
    public RepayALoan findByReId(final String reId) {
        return super.selectByPrimaryKey(reId);
    }

    /**
     * 本月之前还未还款总额（包含本月）.
     *
     * @param userId String用户id
     * @return BigDecimal返回用户还未还款的金额
     */
    public BigDecimal findUnPay(final String userId) {
        Calendar calendar = Calendar.getInstance(); // 获取Calendar
        calendar.set(Calendar.DATE,
                calendar.getActualMaximum(Calendar.DATE)); // 设置日期为本月最大日期
        Date date = calendar.getTime(); //本月最后一天
        return findUnPay(userId, 0, date, null); //月末
    }

    /**
     * 查询已还款总额.
     *
     * @param userId String用户id
     * @return BigDecimal返回用户已还款总额
     */
    public BigDecimal findYetPay(final String userId) {
        return findUnPay(userId, 1, null, null);
    }

    /**
     * 查询未还款总额.
     *
     * @param userId String用户id
     * @return BigDecimal返回未还款总额
     */
    public BigDecimal findNeedPay(final String userId) {
        return findUnPay(userId, 0, null, null);
    }

    /**
     * 删除还款单.
     *
     * @param id String还款单编号
     * @return boolean删除成功返回true, 失败false
     */
    public boolean deleteRepayAloan(final String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", id);
        return super.deletes("deleteByPropertie", map);
    }

    /**
     * 更新还款状态.
     *
     * @param userId String还款单编号
     * @return boolean更新成功返回true, 失败false
     */
    public boolean updateStatus(final String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return super.update("updateStatus", map);
    }

    /**
     * 查询用户某一个产品的分期计划.
     *
     * @param debitId 贷款单编号
     * @return 分期计划信息
     */
    public List<RepayALoan> findRepayAloanInfo(String debitId) {
        return super.select("selectRepayAloanInfo", debitId);
    }

    /**
     * 未还款总额.
     *
     * @param userId String
     * @param status Integer
     * @param date   Date
     * @param debitId 贷款单编号
     * @return BigDecimal
     */
    private BigDecimal findUnPay(final String userId, final Integer status,
                                 final Date date, String debitId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("payDate", date);
        map.put("status", status);
        map.put("debitId", debitId);
        List list = super.select("selectByProperties", map);
        if (list != null && list.get(0) != null) {
            return (BigDecimal) list.get(0);
        }
        return new BigDecimal(0);
    }

    /*查询某一款产品对应某人的还款信息*/

    /**
     * 某人某产品本月应还金额.
     *
     * @param debitId 贷款单编号
     * @return 应还金额
     */
    public BigDecimal findUnPayByDebitId(String debitId) {
        Calendar calendar = Calendar.getInstance(); // 获取Calendar
        calendar.set(Calendar.DATE,
                calendar.getActualMaximum(Calendar.DATE)); // 设置日期为本月最大日期
        Date date = calendar.getTime(); //本月最后一天
        return findUnPay(null, 0, date, debitId);
    }

    /**
     * 某人某产品已还金额.
     *
     * @param debitId 贷款单编号
     * @return 应还金额
     */
    public BigDecimal findYetPayByDebitId(String debitId) {
        return findUnPay(null, 1, null, debitId);
    }

    /**
     * 某人某产品待还金额.
     *
     * @param debitId 贷款单编号
     * @return 应还金额
     */
    public BigDecimal findNeedPayByDebitId(String debitId) {
        return findUnPay(null, 0, null, debitId);
    }
}
