package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * 将发送后的csv文件移动到temp目录.
 * @author gregory
 */
@Log4j
public class MoveToTempTasklet implements Tasklet {
    /**csv文件地址.*/
    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;
    /**历史文件保存目录.*/
    @Value("${SMALLLOAN_DESTPATH}")
    private String destPath;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("MoveToTempTasklet:begin==================================");
        File[] sorcFiles = new File(address).listFiles();
        if(sorcFiles != null || sorcFiles.length>0){
            for(int i=0;i<sorcFiles.length;i++){
                //判断文件夹是否创建，没有创建则创建新文件夹
                File destPathFile = new File(destPath);
                if(!destPathFile.exists()){
                    destPathFile.mkdirs();
                }
                File sorcFile = sorcFiles[i];
                File destFile = new File(destPath+sorcFile.getName());
                if(!sorcFile.isDirectory()){
                    log.debug("------------------------------sorcFile:"+sorcFile.getPath());
                    log.debug("------------------------------destFile:"+destFile.getPath());
                    if (sorcFile.renameTo(destFile)) {
                        log.debug("------------------------------File is moved successful!"+destFile.getName());
                    } else {
                        log.debug("------------------------------File is failed to move!"+destFile.getName());
                    }
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}
