package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.DebitAndCredit;

import java.util.List;

/**
 * 定义对贷款信息操作的接口.
 * @author Mxia
 */
public interface IDebitAndCreditDao {
    /**
     * 增加贷款单.
     *
     * @param debitAndCredit DebitAndCredit对象
     * @return boolean插入成功返回true,失败false
     */
    boolean insertDebitAndCredit(DebitAndCredit debitAndCredit);

    /**
     * 查看该人所有的贷款单.
     *
     * @param userId String用户编号
     * @return List返回该用户所有的贷款信息
     */
    List<DebitAndCredit> findByUserId(String userId);

    /**
     * 删除贷款单.
     *
     * @param userId String用户编号
     * @return boolean删除成功返回true,失败false
     */
    boolean deleteDebitAndCredit(String userId);
}
