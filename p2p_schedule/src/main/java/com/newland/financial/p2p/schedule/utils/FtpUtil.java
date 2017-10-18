package com.newland.financial.p2p.schedule.utils;

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * ftp上传下载工具类
 * @author	Gregory
 */
public class FtpUtil {

    /**
     * Description: 向FTP服务器上传文件
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
            System.out.println("-----------------------"+reply);
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
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
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
     * Description: 从FTP服务器下载文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
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

    public static void main(String[] args){
        try {
            FileInputStream in=new FileInputStream(new File("D:\\ftp\\test.txt"));
            boolean flag = uploadFile("192.168.37.21", 2121, "root", "root", "/home/resource","/2017/10/17", "test.txt", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//		try{
//			FTPClient ftp = new FTPClient();
//			int reply;
//			ftp.connect("192.168.37.21", 2121);// 连接FTP服务器
//			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
////			ftp.connect("192.168.37.21");
//			ftp.login("root", "root");// 登录
//			reply = ftp.getReplyCode();
//			System.out.println("-----------------------"+reply);
//			ftp.disconnect();
//		}catch (Exception e){
//			e.printStackTrace();
//		}


//		FTPClient ftp = new FTPClient();
//		FTPClientConfig config = new FTPClientConfig();
//		config.setServerTimeZoneId("Pacific/Pitcairn");
//		ftp.configure(config );
//		boolean error = false;
//		try {
//			int reply;
//			String server = "192.168.37.21";
//			ftp.connect(server);
//			System.out.println("Connected to " + server + ".");
//			System.out.print(ftp.getReplyString());
//
//			reply = ftp.getReplyCode();
//
//			if(!FTPReply.isPositiveCompletion(reply)) {
//				ftp.disconnect();
//				System.err.println("FTP server refused connection.");
//				System.exit(1);
//			}
//			// transfer files
//			ftp.logout();
//		} catch(IOException e) {
//			error = true;
//			e.printStackTrace();
//		} finally {
//			if(ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch(IOException ioe) {
//					// do nothing
//				}
//			}
//			System.exit(error ? 1 : 0);
//		}
    }
}

