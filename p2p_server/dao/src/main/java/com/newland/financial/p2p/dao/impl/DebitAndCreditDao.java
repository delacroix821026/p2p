package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款单持久层.
 *
 * @author Mxia
 */
@Repository
public class DebitAndCreditDao extends
        MybatisBaseDao<DebitAndCredit> implements IDebitAndCreditDao {

    /**
     * 增加贷款单.
     *
     * @param debitAndCredit DebitAndCredit对象
     * @return boolean插入成功返回true, 失败false
     */
    public boolean insertDebitAndCredit(
            final DebitAndCredit debitAndCredit) {
        return super.insertSelective(debitAndCredit);
    }

    /**
     * 查看该人所有的贷款单.
     *
     * @param userId String用户编号
     * @return List返回该用户所有的贷款信息
     */
    public List<DebitAndCredit> findByUserId(final String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return super.select("selectByProperties", map);
    }

    /**
     * 删除贷款单.
     *
     * @param userId String用户编号
     * @return boolean删除成功返回true, 失败false
     */
    public boolean deleteDebitAndCredit(final String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return super.deletes("deleteByProperties", map);
    }

    /**
     * 查找某产品stus=2的贷款单编号.
     *
     * @param userId 用户编号
     * @param proId  产品编号
     * @return 贷款单编号
     */
    public String selectDebitId(String userId, String proId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId != null && userId.length() != 0 && proId != null && proId.length() != 0){
            map.put("userId",userId);
            map.put("proId",proId);
            map.put("stus","2");
            DebitAndCredit debitAndCredit =  super.selectEntity("selectDebitId",map);
            if (debitAndCredit != null){
                return debitAndCredit.getDtId();
            }
        }
        return null;
    }
}
