package com.jjn.mall.utils;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.util.Properties;  
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jjn.mall.common.Config;

public class SFtpUtil {
	private static ChannelSftp sftp = null;  
	private static String host, user, password;
	private static Integer port;
	static {
		host = Config.getMallConfig("pic.ftp.host");
		user = Config.getMallConfig("pic.ftp.user");
		password = Config.getMallConfig("pic.ftp.pwd");
		port = StringUtils.isBlank(Config.getMallConfig("pic.ftp.port")) ? 22 : Integer.valueOf(Config.getMallConfig("pic.ftp.port"));
	}
    //下载目录  
    private static String saveFile = "D:\\VMware\\XuNiJi\\imgs";  
      
    public static SFtpUtil getConnect(){  
    	SFtpUtil ftp = new SFtpUtil();  
        try {  
            JSch jsch = new JSch();  
  
            //获取sshSession  账号-ip-端口  
            Session sshSession =jsch.getSession(user, host,port);  
            //添加密码  
            sshSession.setPassword(password);  
            Properties sshConfig = new Properties();  
            //严格主机密钥检查  
            sshConfig.put("StrictHostKeyChecking", "no");  
              
            sshSession.setConfig(sshConfig);  
            //开启sshSession链接  
            sshSession.connect();  
            //获取sftp通道  
            Channel channel = sshSession.openChannel("sftp");  
            //开启  
            channel.connect();  
            sftp = (ChannelSftp) channel;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ftp;  
    }  
      
    /** 
     *  
     * @param uploadFile 上传文件的路径 
     * @return 服务器上文件名 
     */  
    public String upload(String directory, String uploadFile, String fileName) {  
        File file = null;  
        try {
            sftp.cd(directory);  
            file = new File(uploadFile);  
            //获取随机文件名  
            sftp.put(new FileInputStream(file), fileName);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return file == null ? null : fileName;  
    }  
      
    /** 
     * 下载文件 
     *  
     * @param directory 
     *            下载目录 
     * @param downloadFile 
     *            下载的文件名 
     * @param saveFile 
     *            存在本地的路径 
     * @param sftp 
     */  
    public void download(String downloadFileName, String directory) {  
        try {  
            sftp.cd(directory);  
              
            File file = new File(saveFile);  
              
            sftp.get(downloadFileName, new FileOutputStream(file));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 删除文件 
     *  
     * @param deleteFile 
     *            要删除的文件名字 
     * @param sftp 
     */  
    public void delete(String deleteFile, String directory) {  
        try {  
            sftp.cd(directory);  
            sftp.rm(deleteFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 列出目录下的文件 
     *  
     * @param directory 
     *            要列出的目录 
     * @param sftp 
     * @return 
     * @throws SftpException 
     */  
    @SuppressWarnings("rawtypes")
	public Vector listFiles(String directory)  
            throws SftpException {  
        return sftp.ls(directory);  
	}  
}
