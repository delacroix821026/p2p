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

    /**
     * 查找某产品stus=2的贷款单编号.
     * @param userId 用户编号
     * @param proId 产品编号
     * @return 贷款单编号
     */
    String selectDebitId(String userId, String proId);
    /**
     *用户对应所有产品的贷款状态.
     * @param userId 用户编号
     * @return 返回该用户所有贷款产品（stus=1 or 2 的贷款单）
     */
    List<DebitAndCredit> findAllProStatus(String userId);
    /**
     * 如果贷款单申请时间超过15天，自动将stus变为3.
     */
    void updateStusToThree();

    /**
     * 用户申请某产品贷款时，先查找是都已经存在该产品申请中或者分期计划的贷款单，如有则不能继续申请.
     * @param userId 用户编号
     * @param proId 产品编号
     * @return 贷款单集合
     */
    List<DebitAndCredit> findRecord(String userId, String proId);
}
