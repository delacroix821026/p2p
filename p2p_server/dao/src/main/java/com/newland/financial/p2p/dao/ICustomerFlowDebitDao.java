package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;

import java.util.List;
import java.util.Map;

/**
 * @author Mxia
 */
public interface ICustomerFlowDebitDao {
    /**
     * 插入面签产品贷款信息.
     * @param customerFlowDebit 贷款信息实体
     * @return true or false
     */
    boolean insertDebitInfo(CustomerFlowDebit customerFlowDebit);
    /**
     * 贷款单分页查询.
     * @param reqMap 分页查询条件
     * @return 分页结果集
     */
    List<CustomerFlowDebit> findAll(Map<String, Object> reqMap);
    /**
     * 根据用户ID删除数据，测试类专用.
     * @param userId 用户编号
     * @return true or false
     */
    boolean deleteByUserId(String userId);
}
