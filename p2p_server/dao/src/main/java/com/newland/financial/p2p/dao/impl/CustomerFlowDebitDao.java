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

}
