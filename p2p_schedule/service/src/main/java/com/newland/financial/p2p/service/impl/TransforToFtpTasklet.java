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

/**
 * ftp上传.
 * @author gregory
 */
@Log4j
public class TransforToFtpTasklet implements Tasklet {
    /**csv文件地址.*/
    @Value("${SMALLLOAN_FILE_ADDRESS}")
    private String address;
    /**FTP HOST.*/
    @Value("${SMALLLOAN_HOST}")
    private String host;
    /**FTP PORT.*/
    @Value("${SMALLLOAN_PORT}")
    private int port;
    /**用户名.*/
    @Value("${SMALLLOAN_USERNAME}")
    private String username;
    /**密码.*/
    @Value("${SMALLLOAN_PASSWORD}")
    private String password;
    /**历史文件保存目录.*/
    @Value("${SMALLLOAN_DESTPATH}")
    private String destPath;

    /**
     * 上传每日申请中的订单信息
     * @param stepContribution  stepContribution
     * @param chunkContext  stepContribution
     * @return  RepeatStatus
     * @throws Exception
     */
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("TransforToFtpProcessor:process ftp");
        List<String> filePathList = new ArrayList<>();
        File file = new File(address);
        if (file.isDirectory()){
            File[] fileArray = file.listFiles();
            for(int i=0;i<fileArray.length;i++){
                String filePath = fileArray[i].getPath();
                String fileName = fileArray[i].getName();
                if(fileName.endsWith(".csv")){
                    filePathList.add(filePath);
                }
            }
        }
        //上传文件
        for(int i = 0;i<filePathList.size();i++){
            File srcFile = new File(filePathList.get(i));
            FileInputStream fis = new FileInputStream(srcFile);
            uploadFile(host,port,username,password,srcFile.getName(),fis);
        }
//        String zipName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        writeZip(strs,zipName,address);
//        FileInputStream fio = new FileInputStream(new File(address+zipName+".zip"));
//        uploadFile(host,port,username,password,zipName+".zip",fio);
        //移动上传后的文件到temp目录下
        File[] sorcFiles = new File(address).listFiles();
        for(int i=0;i<sorcFiles.length;i++){
            //判断文件夹是否创建，没有创建则创建新文件夹
            File destPathFile = new File(destPath);
            if(!destPathFile.exists()){
                destPathFile.mkdirs();
            }
            File sorcFile = sorcFiles[i];
            File destFile = new File(destPath+File.separator+sorcFile.getName());
            if(!sorcFile.isDirectory()){
                if (sorcFile.renameTo(destFile)) {
                    log.debug("------------------------------File is moved successful!"+destFile.getName());
                } else {
                    log.debug("------------------------------File is failed to move!"+destFile.getName());
                }
            }
        }
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
//            ftp.changeWorkingDirectory("/home/ftp/testftp");
            log.debug("------------------------------------------WorkingDirectory:"+ftp.printWorkingDirectory());
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            //上传文件
            boolean storeFile = ftp.storeFile(filename, input);
            if (!storeFile) {
                return result;
            }
            log.debug("------------------------------------------storeFile "+filename+":"+storeFile);
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
//            File file = new File(files[i]);
//            if(file.delete()){
//                log.debug("------------------delete sucess:"+file.getPath());
//            }else {
//                log.debug("------------------delete failed:"+file.getPath());
//            }
//        }
    }
}
