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
 *还款单Dao层操作类.
 *@author Mxia
 * */
@Repository
public class RepayALoanDao extends MybatisBaseDao<RepayALoan>
        implements IRepayALoanDao {
    /**
     * 日志对象.
     * */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 增加还款单.
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true,失败false
     * */
    public boolean insertRepayAloan(RepayALoan repayALoan) {
        return super.insertSelective(repayALoan);
    }

    /**
     * 修改还款单.
     * @param repayALoan RepayALoan还款单对象
     * @return boolean插入成功返回true,失败false
     * */
    public boolean updateRepayAloan(RepayALoan repayALoan) {
        return super.updateByPrimaryKeySelective(repayALoan);
    }

    /**
     * 查找还款单.
     * @param userId String用户Id
     * @return List返回该用户所有的还款单
     * */
    public List<RepayALoan> findByUserId(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        /*List<RepayALoan> list = super.select("selectByPropertie",map);
        for (RepayALoan re: list) {
            logger.info("findByUserId:"+re.toString());
        }*/
        return super.select("selectByPropertie", map);
    }

    /**
     * 根据还款单编号查询相应的还款单.
     * @param reId String还款单编号
     * @return RepayALoan返回相应编号的还款单信息
     * */
    public RepayALoan findByReId(String reId) {
        return super.selectByPrimaryKey(reId);
    }

    /**
     * 本月之前还未还款总额（包含本月）.
     * @param userId String用户id
     * @return BigDecimal返回用户还未还款的金额
     * */
    public BigDecimal findUnPay(String userId) {
        Calendar calendar = Calendar.getInstance(); // 获取Calendar
        calendar.set(Calendar.DATE,
                calendar.getActualMaximum(Calendar.DATE)); // 设置日期为本月最大日期
        Date date = calendar.getTime(); //本月最后一天
        return findUnPay(userId, 0, date); //月末
    }

    /**
     * 查询已还款总额.
     * @param userId String用户id
     * @return BigDecimal返回用户已还款总额
     * */
    public BigDecimal findYetPay(String userId) {
        return findUnPay(userId, 1, null);
    }

    /**
     *查询未还款总额.
     * @param userId String用户id
     * @return BigDecimal返回未还款总额
     * */
    public BigDecimal findNeedPay(String userId) {
        return findUnPay(userId, 0, null);
    }

    /**
     *删除还款单.
     * @param id String还款单编号
     * @return boolean删除成功返回true,失败false
     * */
    public boolean deleteRepayAloan(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", id);
        return super.deletes("deleteByPropertie", map);
    }
    /**
     *更新还款状态.
     * @param userId String还款单编号
     * @return boolean更新成功返回true,失败false
     * */
    public boolean updateStatus(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return super.update("updateStatus", map);
    }
    /**
     * 未还款总额.
     * @param userId String
     * @param status Integer
     * @param date Date
     * @return BigDecimal
     * */
    private BigDecimal findUnPay(String userId, Integer status,
                                 Date date) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("payDate", date);
        map.put("status", status);
        List list = super.select("selectByProperties", map);
        if (list != null && list.get(0) != null) {
            return (BigDecimal) list.get(0);
        }
        return new BigDecimal(0);
    }
}
