package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Log4j
public class TransforToFtpTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("TransforToFtpProcessor:process ftp");
        return RepeatStatus.FINISHED;
    }
}
