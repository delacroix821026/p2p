package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ftp上传.
 * @author gregory
 */
@Log4j
public class TransforToFtpTasklet implements Tasklet {
    /**
     * csv文件地址.
     */
    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;
    /**
     * FTP HOST.
     */
    @Value("${SMALLLOAN_HOST}")
    private String host;
    /**
     * FTP PORT.
     */
    @Value("${SMALLLOAN_PORT}")
    private int port;
    /**
     * 用户名.
     */
    @Value("${SMALLLOAN_USERNAME}")
    private String username;
    /**
     * 密码.
     */
    @Value("${SMALLLOAN_PASSWORD}")
    private String password;

    /**
     * 上传每日申请中的订单信息.
     * @param stepContribution stepContribution
     * @param chunkContext     stepContribution
     * @return RepeatStatus RepeatStatus
     * @throws Exception    Exception
     */
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("TransforToFtpProcessor:process ftp==================================");
        Map<String, String> map = new HashMap<>();
        File file = new File(address);
        if (file.isDirectory()) {
            File[] fileArray = file.listFiles();
            for (int i = 0; i < fileArray.length; i++) {
                String filePath = fileArray[i].getPath();
                String fileName = fileArray[i].getName();
                if (fileName.endsWith(".csv")) {
                    map.put(fileName, filePath);
                }
            }
        }
        //上传文件
        uploadFile(host, port, username, password, map);
        return RepeatStatus.FINISHED;
    }

    /**
     * 上传文件.
     *
     * @param host     IP
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @param map      源文件流
     * @return true:成功,false:失败
     */
    public static boolean uploadFile(String host, int port, String username, String password, Map<String, String> map) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port); // 连接FTP服务器
            ftp.login(username, password);
            log.debug("------------------------------------------loginReply:" + ftp.printWorkingDirectory());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
//            ftp.changeWorkingDirectory("/home/ftp/testftp");
            log.debug("------------------------------------------WorkingDirectory:" + ftp.printWorkingDirectory());
            //主动模式
//            ftp.enterLocalActiveMode();
            //被动模式
            ftp.enterLocalPassiveMode();
            ftp.setRemoteVerificationEnabled(false);
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            //上传文件
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String filename = entry.getKey();
                FileInputStream input = new FileInputStream(new File(entry.getValue()));
                boolean storeFile = ftp.storeFile(filename, input);
                if (!storeFile) {
                    log.debug("------------------------------------------storeFile filed!!：" + ftp.getReplyString() + ", " + filename);
                    return result;
                }
                log.debug("------------------------------------------storeFile success!!：" + filename);
                input.close();
            }
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
