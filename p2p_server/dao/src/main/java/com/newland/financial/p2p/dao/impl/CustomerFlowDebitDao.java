package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.ICustomerFlowDebitDao;
import com.newland.financial.p2p.domain.entity.CustomerFlowDebit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     * 贷款单分页查询.
     *
     * @param reqMap 分页查询条件
     * @return 分页结果集
     */
    public List<CustomerFlowDebit> findAll(Map<String, Object> reqMap) {
        return super.select("selectList", reqMap);
    }

    /**
     * 根据用户ID删除数据，测试专用.
     *
     * @param userId 用户编号
     * @return true or false
     */
    public boolean deleteByUserId(String userId) {
        if (userId != null && userId.length() != 0) {
            return super.deletes("deleteTestData", userId);
        }
        return false;
    }
}
