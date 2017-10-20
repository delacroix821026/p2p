package com.newland.financial.p2p.service.impl;

import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Log
public class TransToSmallLoanScheduler {

    /***/
    @Autowired
    JobLauncher jobLauncher;

    /***/
    @Resource(name = "createTransToSmallLoanCompanyJob")
    Job job;

    @Scheduled(fixedRate=20000)
    public void testTasks() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("每20秒执行一次。开始……");
        //statusTask.healthCheck();
        log.info("Job restartable is:" + job.isRestartable());
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        log.info("每20秒执行一次。结束。");
    }
}
