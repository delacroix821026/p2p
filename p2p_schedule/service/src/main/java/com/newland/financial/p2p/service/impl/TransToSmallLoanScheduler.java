package com.newland.financial.p2p.service.impl;

import lombok.extern.java.Log;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        String runDay = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("runDay", runDay)
                .toJobParameters();
        //jobExecution.getJobParameters().getParameters().putAll(jobParameters.getParameters());

        JobExecution execution = jobLauncher.run(job, jobParameters);
        log.info("每20秒执行一次。结束。" + execution.getStatus());
    }
}
