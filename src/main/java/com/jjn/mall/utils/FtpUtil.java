package com.jjn.mall.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.jjn.mall.common.Config;


/**
 * FTP文件上传工具类
 * 
 */
public class FtpUtil {
	private static final Logger LOGGER = Logger.getLogger(FtpUtil.class);

	private static String host, userName, password;
	private static Integer port;
	static {
		host = Config.getMallConfig("pic.ftp.host");
		userName = Config.getMallConfig("pic.ftp.user");
		password = Config.getMallConfig("pic.ftp.pwd");
		port = StringUtils.isBlank(Config.getMallConfig("pic.ftp.port")) ? 22 : Integer.valueOf(Config.getMallConfig("pic.ftp.port"));
	}

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	private static FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			// 连接FTP服务器
			ftpClient.connect(host, port);
			// 登陆FTP服务器
			ftpClient.login(userName, password);
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				LOGGER.error("未连接到FTP,用户名或密码错误");
				ftpClient.disconnect();
			} else {
				LOGGER.warn("FTP连接成功");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			LOGGER.error("FTP host配置错误");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("FTP port配置错误");
		}
		return ftpClient;
	}

	/**
	 * 使用当前工作路径(支持递归创建工作目录)
	 * 
	 * @param dir
	 *            要使用的工作路径,格式:/a/b/c/d
	 * @return
	 */
	private static boolean useWorkingDir(FTPClient ftpClient, String dir) {
		if (StringUtils.isBlank(dir)) {
			LOGGER.error("远程FTP工作目录为空");
			return false;
		}

		if (ftpClient == null) {
			LOGGER.error("获取FTP客户端失败");
			return false;
		}

		// 处理"\"
		dir = dir.replaceAll("\\\\", "/");
		if (dir.equals("/")) {
			try {
				boolean status = ftpClient.changeWorkingDirectory(new String(
						dir.getBytes("GBK"), "iso-8859-1"));
				LOGGER.warn(ftpClient.getReplyString());
				LOGGER.warn("远程FTP工作目录切换到:" + dir);
				return status;
			} catch (IOException e) {
				LOGGER.error("远程FTP工作目录切换发生异常");
				e.printStackTrace();
				return false;
			}

		}
		// 如果有"/"去除第一个"/"
		String str = dir.substring(0, 1);
		if ("/".equals(str)) {
			dir = dir.substring(1);
		}
		String[] dirs = dir.split("/");
		for (int i = 0; i < dirs.length; i++) {
			dirs[i] = "/" + dirs[i];
		}
		String path = "";
		try {
			for (int i = 0; i < dirs.length; i++) {
				path = path + dirs[i];
				String newPath = new String(path.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(newPath)) {
					if (ftpClient.makeDirectory(newPath)) {
						ftpClient.changeWorkingDirectory(newPath);
					} else {
						LOGGER.error("创建远程FTP工作目录发生异常" + newPath);
						return false;
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("创建远程FTP工作目录发生异常");
			e.printStackTrace();
			return false;
		}
		LOGGER.debug("远程FTP目录切换到:" + path);
		return true;
	}

	/**
	 * FTP文件下載
	 * 
	 * @param localFile
	 *            本地文件物理绝对路径列表,如:c:/a.txt
	 * @param remoteFile
	 *            远程FTP路径列表,如:/a/b/a.txt或a.txt
	 * @return
	 */
	public static boolean uploadFile(String localFile, String remoteFile) {
		boolean status = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			if (ftpClient == null) {
				LOGGER.error("FTP客户端获取失败");
				return false;
			}
			if (StringUtils.isBlank(localFile)) {
				LOGGER.error("上传文件为空");
				return false;
			}
			// 对远程目录的处理
			// 目录树
			String directory = "";
			// 处理"\"
			String remoteFileName = remoteFile;
			// 如果FTP路径中含有 "/"
			if (remoteFile.contains("/")) {
				directory = remoteFile.substring(0, remoteFile.lastIndexOf("/") + 1);
				remoteFileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
				// 切换工作目录
				if (!useWorkingDir(ftpClient, directory)) {
					LOGGER.error("远程FTP工作目录[" + directory + "]切换失败");
					return false;
				}
			}

			// 上传文件
			File file = new File(localFile);
			InputStream input = new FileInputStream(file);
			status = ftpClient.storeFile(
					new String(remoteFileName.getBytes("GBK"), "iso-8859-1"),
					input);
			input.close();
			if (status) {
				status = true;
				LOGGER.warn("本地目录:[" + localFile + "},远程目录:[" + remoteFile
						+ "]上传成功");
				boolean deleteRet = file.delete();
				if(deleteRet){
					LOGGER.warn("本地目录:[" + localFile + "},文件删除成功");
				}else{
					LOGGER.error("本地目录:[" + localFile + "},文件删除失败");
				}
				
			} else {
				status = false;
				LOGGER.error("本地目录:[" + localFile + "},远程目录:[" + remoteFile
						+ "]上传失败");
			}

			ftpClient.logout();
		} catch (IOException e) {
			LOGGER.error("FTP上传文件出错");
			e.printStackTrace();
			return false;
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					LOGGER.error("FTP客户端断开失败");
					e.printStackTrace();
				}
			}
		}

		return status;
	}

	/**
	 * FTP文件下载
	 * 
	 * @param downloadFile
	 * @param localPath
	 * @return
	 */
	public static boolean downloadFile(String remoteFile, String localPath) {
		boolean status = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = getFTPClient();
			if (ftpClient == null) {
				LOGGER.error("FTP客户端获取失败");
				return false;
			}

			if (StringUtils.isBlank(remoteFile))
			{
				LOGGER.error("FTP下载文件为空");
				return false;
			}
			
			if (StringUtils.isBlank(localPath))
			{
				LOGGER.warn("FTP下载目录为空, 设置为默认");
				localPath = "/";
			}
			
			// 对远程目录的处理
			// 目录树
			String directory = "";
			// 处理"\"
			String remoteFileName = remoteFile;
			// 如果FTP路径中含有 "/"
			if (remoteFile.contains("/")) {
				directory = remoteFile.substring(0,
						remoteFile.lastIndexOf("/") + 1);
				remoteFileName = remoteFile.substring(remoteFile
						.lastIndexOf("/") + 1);
				// 切换工作目录
				if (!useWorkingDir(ftpClient, directory)) {
					LOGGER.error("远程FTP工作目录[" + directory + "]切换失败");
					return false;
				}
			}
			File localFile = new File(localPath + remoteFileName);
			OutputStream is = new FileOutputStream(localFile);
			status = ftpClient.retrieveFile(remoteFile, is);
			is.close();

			if (status) {
				status = true;
				LOGGER.warn("下载文件[" + remoteFile + "]到本地目录:["
						+ localPath + "}成功");
			} else {
				status = false;
				LOGGER.error("下载文件[" + remoteFile + "]到本地目录:["
						+ localPath + "}失败");
			}
			ftpClient.logout();
		} catch (IOException e) {
			LOGGER.error("FTP下载文件出错");
			e.printStackTrace();
			return false;
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					LOGGER.error("FTP客户端断开失败");
				}
			}
		}

		return status;
	}
	
	/** 
     * 读取服务器的FTP路径文件 
     *  
     * @param ftpUserName 
     * @param ftpPassword 
     * @param ftpPath 
     * @param FTPServer 
     * @return 
     */  
    public static String readFTPFile2String(String ftpPath, String separator) {  
        if (StringUtils.isBlank(ftpPath)){
        	LOGGER.error("FTP读取文件为空");
        	return null;
        }
    	
        if (StringUtils.isBlank(separator)){
        	separator = Config.getMallConfig("sys.file.separator");
        }
        
    	StringBuffer resultBuffer = new StringBuffer();  
        InputStream in = null;  
        FTPClient ftpClient = getFTPClient(); 
        if (ftpClient == null){
        	LOGGER.error("FTP客户端获取失败");
			return null;
        }
        LOGGER.info("开始读取绝对路径" + ftpPath + "文件!");  
        try {  
	        // 对远程目录的处理
			// 目录树
			String directory = "";
			// 处理"\"
			String fileName = ftpPath;
			// 如果FTP路径中含有 "/"
			if (ftpPath.contains("/")) {
				directory = ftpPath.substring(0, ftpPath.lastIndexOf("/") + 1);
				fileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);
				// 切换工作目录
				if (!useWorkingDir(ftpClient, directory)) {
					LOGGER.error("远程FTP工作目录[" + directory + "]切换失败");
					return null;
				}
			}
            in = ftpClient.retrieveFileStream(fileName);  
        } catch (FileNotFoundException e) {  
            LOGGER.error("没有找到" + ftpPath + "文件", e);  
            return null;  
        } catch (SocketException e) {  
            LOGGER.error("连接FTP失败", e);  
            return null; 
        } catch (IOException e) {  
        	LOGGER.error("文件读取错误", e);  
            return null;  
        }  
        
        if (in != null) {  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            String data = null;  
            try {  
                while ((data = br.readLine()) != null) {  
                    resultBuffer.append(data).append(Config.getMallConfig("sys.file.separator"));  
                } 
            } catch (IOException e) {  
                LOGGER.error("文件读取错误", e);  
                return null;  
            }finally{  
                try {  
                    ftpClient.disconnect();  
                } catch (IOException e) {  
                	LOGGER.error("FTP客户端关闭失败", e);
                }  
            }  
        }else{  
            LOGGER.error("in为空，不能读取");  
            return null;  
        }  
        return resultBuffer.toString();  
    }  
    
    /** 
     * 读取服务器的FTP路径文件 
     *  
     * @param ftpUserName 
     * @param ftpPassword 
     * @param ftpPath 
     * @param FTPServer 
     * @return 
     */  
    public static List<String> readFTPFile2List(String ftpPath, String separator) {  
    	String contents = readFTPFile2String(ftpPath, separator);
    	return Arrays.asList(contents.split("\n"));
    }
}
