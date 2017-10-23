package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.Application;
import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.*;

import java.io.*;
import java.net.URISyntaxException;

@Log4j
public class TransToSmallLoanJobExecutionListener implements JobExecutionListener {
    public void beforeJob(JobExecution jobExecution) {
        log.info("Enter into TransToSmallLoanJobExecutionListener-beforeJob to creat Excel document");

        /*String runDay = jobExecution.getJobParameters().getString("runDay");

        log.debug("Parameter runDay: " + runDay);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            log.debug("begin transport CsvTemplate");

            inputStream = Application.class.getResourceAsStream("/filetemplate/CsvTemplate.csv");
            outputStream = new FileOutputStream(new File("/Users/daijuancen/Desktop/outputfile-" + runDay + ".csv"));
            byte buffer[]=new byte[1024];
            int count;
            while((count = inputStream.read(buffer)) != -1) {
                for(int i = 0;i < count; i++)
                    outputStream.write(buffer[i]);
            }
            log.debug("transport CsvTemplate over");
        } catch (IOException e) {
            log.error(e);
        } finally {
            if(inputStream != null) {
                try {
                    log.debug("close inputStream");
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e);
                }

            }
            if(outputStream != null) {
                try {
                    log.debug("close outputStream");
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }*/
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
