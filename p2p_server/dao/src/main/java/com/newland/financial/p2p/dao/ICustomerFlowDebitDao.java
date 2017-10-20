package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;

import java.util.List;

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

}
