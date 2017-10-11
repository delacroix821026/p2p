package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IRepayMethodDao;
import com.newland.financial.p2p.domain.entity.RepayMethod;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *@author Mxia
 *
 */
@Repository
public class RepayMethodDao extends
        MybatisBaseDao<RepayMethod> implements IRepayMethodDao {
    /**
     *根据产品编码删除对应产品的还款方式.
     * @param proId 产品编码
     * @return true or false
     */
    public Boolean deleteRepayMethod(String proId) {
        Map map = new HashMap();
        map.put("proId", proId);
        return super.deletes("deleteByProperty", map);
    }
    /**
     *插入对应产品的还款方式记录.
     * @param  repayMethod 实体
     * @return true or false
     */
    public Boolean insertRepayMethod(RepayMethod repayMethod) {
        return super.insertSelective(repayMethod);
    }
    /**
     * 查询对应产品的还款方式.
     * @param proId 产品编码
     * @return List RepayMethod集合
     */
    public List<RepayMethod> selectRepayMethod(String proId) {
        Map map = new HashMap();
        map.put("proId", proId);
        return super.select("selectByProperty", map);
    }
}
