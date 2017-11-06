package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时器.
 *定时任务将数据库内申请时间超过15天的贷款贷状态改为拒绝.
 */
@Component
public class TimerMisson {
    private static final Logger log = LoggerFactory.getLogger(TimerMisson.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;

    @Scheduled(cron = "59 59 23 * * ?")
    public void reportCurrentTime() {
        debitAndCreditDao.updateStusToThree(); //将数据库内申请时间超过15天的贷款贷状态改为拒绝
        log.info("The time is now：定时器执行更改超过15天申请中订单的状态为拒绝", dateFormat.format(new Date()));
    }
}
