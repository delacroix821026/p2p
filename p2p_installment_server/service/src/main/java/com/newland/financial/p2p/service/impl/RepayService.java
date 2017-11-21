package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IRepayDao;
import com.newland.financial.p2p.domain.entity.Repay;
import com.newland.financial.p2p.service.IRepayService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 处理还款推送ServiceImpl.
 *
 * @author Gregory
 */
@Log4j
@Service
public class RepayService implements IRepayService {
    /**
     * 还款表操作Dao.
     */
    @Autowired
    private IRepayDao repayDao;

    /**
     * 接收还款推送信息.
     *
     * @param repay 还款对象
     * @return 成功：true，失败：false
     */
    public String receiveRepayInfo(Repay repay) {
        log.info("--------------------------------进入RepayService:");
        String id = UUID.randomUUID().toString().replace("-", "");
        boolean bol = false;
        Repay existRepay = repayDao.findRepayInfo(repay);
        //首次推送则插入新纪录
        if (existRepay == null) {
            repay.setId(id);
            bol = repayDao.insertRepayInfo(repay);
        } else {
            //重复推送则更新还款单
            repay.setId(existRepay.getId());
            bol = repayDao.updateRepayInfo(repay);
        }

        if (bol) {
            return "true";
        }
        return "false";
    }
}
