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
     * @return List返回该用户所有的订单信息
     */
    List<DebitAndCredit> findAll();

    /**
     * 查询指定状态的所有订单.
     * @param stus 订单状态
     * @return  返回该用户所有的订单信息
     */
    List<DebitAndCredit> findAllByStus(String stus);

    /**
     * 修改订单状态.
     * @param dtId 订单编号
     * @return  true   false
     */
    boolean updateStus(String dtId);
}
