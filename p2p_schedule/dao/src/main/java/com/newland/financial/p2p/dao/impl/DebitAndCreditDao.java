package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import com.newland.financial.p2p.domain.entity.DebitAndCredit;
import org.springframework.stereotype.Repository;

import java.util.*;

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
     * @param dtId 订单编号
     * @return  true   false
     */
    public boolean updateStus(String dtId){
        return super.update("updateStus",dtId);
    }

    /**
     * 如果贷款单申请时间超过15天，自动将stus变为3.
     */
    public void updateStusToThree() {
        //查找时间超过15天的贷款单
        List<DebitAndCredit> list = new ArrayList<DebitAndCredit>();
        Date nowDate = new Date();
        list = super.select("selectStus", nowDate);
        //将这些贷款单的stus改为3，即为拒绝状态
        List<String> dtIdlist = new ArrayList<String>();
        for (DebitAndCredit de : list) {
            dtIdlist.add(de.getDtId());
        }
        if (dtIdlist.size() != 0) {
            super.update("updateStusToThree", dtIdlist);
        }
    }

}
