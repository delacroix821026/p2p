package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.Repay;
import com.newland.financial.p2p.service.IRepayService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *还款推送ServiceImpl
 * @author Gregory
 */
@Log4j
@Service
public class RepayService implements IRepayService {

    @Autowired
    private IRepayDao repayDao;

    /**
     * 更新还款表.
     * @param repay     还款对象
     * @return  成功：true，失败：false
     */
    public String updateRepayInfo(Repay repay){
        boolean bol = repayDao.updateRepayInfo(repay);
        if(bol){
            return "true";
        }
        return "false";
    }
}
