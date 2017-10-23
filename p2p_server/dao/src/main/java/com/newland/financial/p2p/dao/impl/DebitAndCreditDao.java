package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        if (userId != null && userId.length() != 0 && proId != null && proId.length() != 0) {
            map.put("userId", userId);
            map.put("proId", proId);
            map.put("stus", "2");
            DebitAndCredit debitAndCredit = super.selectEntity("selectDebitId", map);
            if (debitAndCredit != null) {
                return debitAndCredit.getDtId();
            }
        }
        return null;
    }

    /**
     * 用户对应所有产品的贷款状态.
     *
     * @param userId 用户编号
     * @return 返回该用户所有贷款产品
     */
    public List<DebitAndCredit> findAllProStatus(String userId) {
        return super.select("selectAllProStatus", userId);
    }

    /**
     * 如果贷款单申请时间超过15天，自动将stus变为3.
     */
    public void updateStusToThree() {
        //查找时间超过15天的贷款单
        List<DebitAndCredit> list = new ArrayList<DebitAndCredit>();
        Date nowDate = new Date();
        list = super.select("selectStus", nowDate);
        //将这些贷款单的stus改为3，即为拒绝状态
        List<String> dtIdlist = new ArrayList<String>();
        for (DebitAndCredit de : list) {
            dtIdlist.add(de.getDtId());
        }
        if (dtIdlist.size() != 0) {
            super.update("updateStusToThree", dtIdlist);
        }
    }

    /**
     * 用户申请某产品贷款时，先查找是都已经存在该产品申请中或者分期计划的贷款单，如有则不能继续申请.
     *
     * @param userId 用户编号
     * @param proId  产品编号
     * @return 贷款单集合
     */
    public List<DebitAndCredit> findRecord(String userId, String proId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("proId", proId);
        return super.select("selectRecord", map);
    }
}
