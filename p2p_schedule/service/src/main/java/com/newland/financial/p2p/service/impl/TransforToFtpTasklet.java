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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j
public class TransforToFtpTasklet implements Tasklet {

    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;

    @Value("${SMALLLOAN_HOST}")
    private String host;

    @Value("${SMALLLOAN_PORT}")
    private int port;

    @Value("${SMALLLOAN_USERNAME}")
    private String username;

    @Value("${SMALLLOAN_PASSWORD}")
    private String password;
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("TransforToFtpProcessor:process ftp");
        String[] strs = null;
        List<String> filePathList = new ArrayList<>();
        File file = new File(address);
        if (file.isDirectory()){
            File[] fileArray = file.listFiles();
            for(int i=0;i<fileArray.length;i++){
                String temp = fileArray[i].getPath();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(temp.endsWith(".csv")){
                    String fileDate = temp.substring(temp.length()-20,temp.length()-10);
                    if(sdf.format(new Date()).equals(fileDate)){
                        filePathList.add(temp);
                    }
                }
            }
        }
        Object[] obj = filePathList.toArray();
        strs = new String[obj.length];
        for(int i = 0;i<obj.length;i++){
            strs[i] = (String) obj[i];
            log.debug("--------------------------------------csvFilePath:"+strs[i]);
        }
        String zipName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        writeZip(strs,zipName,address);
        FileInputStream fio = new FileInputStream(new File(address+zipName+".zip"));
        uploadFile(host,port,username,password,zipName+".zip",fio);
        return RepeatStatus.FINISHED;
    }
    /**
     * 上传文件.
     * @param host  IP
     * @param port  端口
     * @param username  用户名
     * @param password  密码
     * @param filename  目标文件名
     * @param input     源文件输入流
     * @return  true:成功,false:失败
     */
    public static boolean uploadFile(String host, int port, String username, String password,String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
//			ftp.connect(host);
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            log.debug("------------------------------------------loginReply:"+reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            log.debug("------------------------------------------WorkingDirectory:"+ftp.printWorkingDirectory());
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            //上传文件
            boolean storeFile = ftp.storeFile(filename, input);
            if (!storeFile) {
                return result;
            }
            log.debug("------------------------------------------storeFile:"+storeFile);
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

    /**
     * 压缩文件.
     * @param strs 源文件路径
     * @param zipname   目标文件名
     * @param path  目标文件保存目录
     * @throws IOException
     */
    public static void writeZip(String[] strs, String zipname,String path) throws IOException {
        String[] files = strs;
        OutputStream os = new BufferedOutputStream(new FileOutputStream(path + zipname + ".zip"));
        ZipOutputStream zos = new ZipOutputStream(os);
        byte[] buf = new byte[8192];
        int len;
        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i]);
            if (!file.isFile())
                continue;
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((len = bis.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
        }
        zos.closeEntry();
        zos.close();

//        for (int i = 0; i < files.length; i++) {
//            System.out.println("---------------------delete:" + files);
//            File file = new File(files[i]);
//            file.delete();
//        }
    }
}
