package com.newland.financial.p2p.service.impl;

import lombok.extern.log4j.Log4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j
public class TransforToFtpTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        FTPClient ftp = new FTPClient();
        String host = "192.168.37.21";
        int port = 21;
        String username = "root";
        String password = "root";
        String basePath = "/home/resource";
        String filePath = "/2017/10/23";
        String filename = "outputfile-#{jobParameters['runDay']}.csv";
        FileInputStream input = new FileInputStream(new File("d:\\scheduel\\outputfile-#{jobParameters['runDay']}.csv"));

        boolean result = TransforToFtpTasklet.uploadFile(host,port,username,password,basePath,filePath,filename,input);

        return RepeatStatus.FINISHED;
    }

    /**
     * Description: 向FTP服务器上传文件.
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//			ftp.connect(host);
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            log.info("-----------------------"+reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
//            OutputStream os = ftp.storeFileStream("aaa.csv");
//            byte[] b = new byte[1024];
//            int len = 0;
//            while ((len = input.read(b)) != -1) {
//                os.write(b,0,len);
//            }
            input.close();
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
