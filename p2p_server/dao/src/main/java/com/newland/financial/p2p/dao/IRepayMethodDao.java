package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.RepayMethod;

import java.util.List;
/**
 *@author Mxia
 *
 */
public interface IRepayMethodDao {
    /**
     *根据产品编码删除对应产品的还款方式.
     * @param proId 产品编码
     * @return true or false
     */
    Boolean deleteRepayMethod(String proId);
    /**
     *插入对应产品的还款方式记录.
     * @param  repayMethod 实体
     * @return true or false
     */
    Boolean insertRepayMethod(RepayMethod repayMethod);
    /**
     * 查询对应产品的还款方式.
     * @param proId 产品编码
     * @return List RepayMethod集合
     */
    List<RepayMethod> selectRepayMethod(String proId);
}
