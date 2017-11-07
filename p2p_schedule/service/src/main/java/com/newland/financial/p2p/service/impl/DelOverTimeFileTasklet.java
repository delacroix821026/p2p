package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Date;

/**
 * 删除过期文件.
 * @author gregory
 */
@Log4j
public class DelOverTimeFileTasklet implements Tasklet {
    /**
     * 历史文件保存目录.
     */
    @Value("${SMALLLOAN_DESTPATH}")
    private String destPath;

    /**
     * 删除temp目录中3天前的文件.
     * @param stepContribution stepContribution
     * @param chunkContext     chunkContext
     * @return RepeatStatus RepeatStatus
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("DelOverTimeFileTasklet:begin==============================");
        File[] files = new File(destPath).listFiles();
        if (files != null || files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                long lastModifyTime = files[i].lastModified();
                int betDays = (int) (new Date().getTime() - lastModifyTime) / (1000 * 3600 * 24);
                if (betDays >= 3) {
                    boolean b = files[i].delete();
                    if (b) {
                        log.debug("---------------------------------delete successful:" + files[i].getPath());
                    } else {
                        log.debug("---------------------------------delete failed:" + files[i].getPath());
                    }
                }
            }
        }

        return RepeatStatus.FINISHED;
    }
}
