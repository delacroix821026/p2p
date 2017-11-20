package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IDebitAndCreditDao;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Scheduler配置类.
 * @author Gregory
 */
@Log4j
@Component
public class TransToSmallLoanScheduler {

    /**
     * jobLauncher.
     */
    @Autowired
    private JobLauncher jobLauncher;
    /**
     * 单款单Dao.
     */
    @Autowired
    private IDebitAndCreditDao debitAndCreditDao;
    /**
     * 日期时间格式.
     */
    private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");
    /**
     * job.
     */
    @Resource(name = "createTransToSmallLoanCompanyJob")
    private Job job;

    /**
     * 订单流水报表Task.
     * @throws JobParametersInvalidException JobParametersInvalidException
     * @throws JobExecutionAlreadyRunningException JobExecutionAlreadyRunningException
     * @throws JobRestartException  JobRestartException
     * @throws JobInstanceAlreadyCompleteException  JobInstanceAlreadyCompleteException
     */
//    @Scheduled(fixedRate = 60000)
    @Scheduled(cron = "0 0 10 * * ?")
    public void testTasks() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.debug("每天10点执行一次。开始……");
        DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
//        dataFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String runDay = dataFormat.format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("runDay", runDay)
                .addString("queryId", "selectSendOrderList")
                .addString("statementId", "updateOrderSendStus")
                .toJobParameters();
        //jobExecution.getJobParameters().getParameters().putAll(jobParameters.getParameters());

        JobExecution execution = jobLauncher.run(job, jobParameters);
        log.info("每天10点执行一次。结束。" + execution.getStatus());
    }

    /**
     * 更新过期订单Task.
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void reportCurrentTime() {
        debitAndCreditDao.updateStusToThree(); //将数据库内申请时间超过15天的贷款贷状态改为拒绝
        log.info("The time is now：定时器执行更改超过15天申请中订单的状态为拒绝" + DATEFORMAT.format(new Date()));
    }
}
