package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.ICustomerFlowDebitDao;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import org.springframework.stereotype.Repository;

/**
 * @author Mxia
 */
@Repository
public class CustomerFlowDebitDao extends
        MybatisBaseDao<CustomerFlowDebit> implements ICustomerFlowDebitDao {
    /**
     * 插入面签产品贷款信息.
     *
     * @param customerFlowDebit 贷款信息实体
     * @return true or false
     */
    public boolean insertDebitInfo(CustomerFlowDebit customerFlowDebit) {
        boolean b1 = super.insert("insertIntoFlow", customerFlowDebit);
        boolean b2 = super.insert("insertIntoDebit", customerFlowDebit);
        return b1 && b2;
    }
}
