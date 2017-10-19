package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款单持久层.
 * @author Gregory
 *
 */
@Repository
public class DebitAndCreditDao extends
        MybatisBaseDao<DebitAndCredit> implements IDebitAndCreditDao {

    /**
     * 查看该人所有的贷款单.
     * @return List返回该用户所有的贷款信息
     */
    public List<DebitAndCredit> findAll() {
        return super.selectAll();
    }

    /**
     * 查询指定状态的所有订单.
     * @param stus 订单状态
     * @return  返回该用户所有的订单信息
     */
    public List<DebitAndCredit> findAllByStus(String stus){
        return super.select("selectByStus",stus);
    }

    /**
     * 修改订单状态.
     * @param isSend 订单状态
     * @return  true   false
     */
    public boolean updateStus(String isSend){
        return super.update("updateStus",isSend);
    }

}
