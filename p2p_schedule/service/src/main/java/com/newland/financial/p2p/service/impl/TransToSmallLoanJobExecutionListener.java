package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j
public class TransToSmallLoanJobExecutionListener implements JobExecutionListener {
    public void beforeJob(JobExecution jobExecution) {
        log.info("Enter into TransToSmallLoanJobExecutionListener-beforeJob to creat Excel document");
        String runDay = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("runDay", runDay)
                .toJobParameters();
        jobExecution.getJobParameters().getParameters().putAll(jobParameters.getParameters());

        File file = null;
        try {
            file = new File("/Users/daijuancen/Desktop/outputfile.csv");
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            log.error(e);
        }
        log.info("Leave out TransToSmallLoanJobExecutionListener-beforeJob");

    }

    public void afterJob(JobExecution jobExecution) {
        log.info("Enter into TransToSmallLoanJobExecutionListener-afterJob to destroy Excel document");
        //try {
            //File file = new File("/home/outputfile.csv");
            //if(file.exists())
                //file.delete();
        //} catch (IOException e) {
          //  log.error(e);
        //}
    }
}
