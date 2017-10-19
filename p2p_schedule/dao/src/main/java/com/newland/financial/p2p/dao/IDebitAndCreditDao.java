package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.DebitAndCredit;

import java.util.List;

/**
 * 定义对贷款信息操作的接口.
 * @author Gregory
 */
public interface IDebitAndCreditDao {

    /**
     * 查看该人所有的贷款单.
     * @return List返回该用户所有的贷款信息
     */
    List<DebitAndCredit> findAll();
}
