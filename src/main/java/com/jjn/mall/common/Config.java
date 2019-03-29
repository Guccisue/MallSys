package com.jjn.mall.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jjn.mall.utils.ProgramUtil;

/**
 * 获取配置文件信息
 * 
 * @author 翟仁元
 *
 */
public class Config {
	// 日志
	private static final Logger LOG = Logger.getLogger("op");
	/** 配置文件缓存集合 */
	private static Map<String, Properties> props;
	
	static {
		props = new HashMap<String, Properties>();
	}
	
	private Config() {}
	
	/**
	 * 加载配置文件
	 * @param fileName
	 * @return
	 * @author 翟仁元
	 */
	public static Properties loadConfig(String fileName) {
		Properties config = new Properties();
		try {
			// 加载配置文件
			InputStream input = new BufferedInputStream(new FileInputStream(
					ProgramUtil.get().rootPath() + "/WEB-INF/classes/" + fileName));
			config.load(input);
			
			// 配置文件记录缓存集合
			props.put(fileName, config);
			return config;
		} catch (Exception e) {
			LOG.error("[配置文件] 配置[" + fileName + "], 路径[/WEB-INF/classes/" + fileName + "], 加载失败", e);
		}
		return null;
	}
	
	/**
	 * 获取配置对象
	 * @param fileName
	 * @return
	 * @author 翟仁元
	 */
	public static Properties getConfig(String fileName) {
		return props.get(fileName);
	}
	
	/**
	 * 设置配置对象
	 * @param fileName
	 * @param prop
	 * @author 翟仁元
	 */
	public static void setConfig(String fileName, Properties prop) {
		props.put(fileName, prop);
	}
	
	/**
	 * 移除配置对象
	 * @param fileName
	 * @author 翟仁元
	 */
	public static void removeConfig(String fileName) {
		props.remove(fileName);
	}
	
	/**
	 * 获取配置信息
	 * @param config
	 * @param key
	 * @return
	 * @author 翟仁元
	 */
	public static String getConfig(Properties config, String key) {
		if (config == null || key == null) return null;
		return config.getProperty(key, null);
	}
	
	/**
	 * 获取配置信息
	 * @param fileName
	 * @param key
	 * @return
	 * @author 翟仁元
	 */
	public static String getConfig(String fileName, String key) {
		Properties prop = getConfig(fileName);
		if (prop == null) {
			prop = loadConfig(fileName);
		}
		return getConfig(prop, key);
	}
	
	/**
	 * 加载系统配置文件
	 * @author 翟仁元
	 */
	public static String getMallConfig(String key) {
		return getConfig("mall.properties", key);
	}
	
	/**
	 * 重新加载系统配置文件
	 * @author 翟仁元
	 */
	public static void reloadMallConfig() {
		removeConfig("mall.properties");
	}
	
}
