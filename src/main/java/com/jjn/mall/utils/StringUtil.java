package com.jjn.mall.utils;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具：字符串
 * @author 翟仁元
 */
public class StringUtil {
	private StringUtil() {}
	
	
	/**
	 * 字符是否为空串
	 * @param str
	 * @return
	 * @author 翟仁元
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) return true;
		return false;
	}
	
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	
	/**
	 * UTF8转码
	 * @param message
	 * @return
	 */
	public static String encodeUTF8(String message) {
		if (message == null) return null;
		
		try {
			message = URLEncoder.encode(message, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * UTF8解码
	 * @param message
	 * @return
	 */
	public static String decodeUTF8(String message) {
		if (message == null) return null;
		
		try {
			message = URLDecoder.decode(message, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * 标准MD5加密(MySQL等值，字符为小写)
	 * @param msg
	 * @return
	 */
	public static String MD5(String msg) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] strTemp = msg.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();	// 16位加密
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (Exception e) {
            return null;
        }
	}
	
	/**
	 * java MD5加密
	 * @param msg
	 * @return
	 */
	public static String javaMD5(String msg) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(msg.getBytes());
			msg = new BigInteger(1, md5.digest()).toString();
		} catch (Exception e) {
			System.out.println("字符：" + msg + " 密码MD5加密失败");
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 连接数组内的字符串
	 * @param arr
	 * @param str
	 * @return
	 */
	public static String join(Object[] arr, String str) {
		if (arr == null || str == null) { return null; }
		
		// 连接字符串
		String rtn = "";
		for (Object temp : arr) {
			rtn += temp + str;
		}
		// 去除最后的连接符
		if (rtn.length() > str.length()) {
			rtn = rtn.substring(0, rtn.length() - str.length());	
		}
		// 返回
		return rtn;
	}
	
	/**
	 * 限制长度
	 * @param str
	 * @param len
	 * @param isMore
	 * @return
	 */
	public static String limit(String str, int len, boolean isMore) {
		if (str == null) { return null; }
		
		if (str.length() > len) {
			return str.substring(0, len) + (isMore ? "..." : "");
		}
		return str;
	}
	
	/**
	 * 字符补足
	 * @param str
	 * @param len
	 * @param ch
	 * @param isBefore 向前补齐(ch + str)
	 * @return
	 * @author 翟仁元
	 */
	public static String complement(String str, int len, char ch, boolean isBefore) {
		StringBuffer sb = new StringBuffer(str == null ? "" : str);
		int compLen = len - sb.length();
		for (int i=0; i<compLen; i++) {
			if (isBefore) {
				sb.insert(0, ch);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 格式化HTML文本
	 * @param str
	 * @return
	 */
	public static String formatHTML(String str) {
		if (str == null) { return null; }
		
		return str.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;")
			.replaceAll(" ", "&nbsp;");
	}
	
	/**
	 * str1是否包含str2
	 * @param str1 源字符串
	 * @param str2 包含字符串
	 * @return 是否包含
	 */
	public static boolean contain(String str1, String str2) {
		if (str1.indexOf(str2) == -1)
			return false;
		return true;
	}
	
	/**
	 * 获取较大的字符串
	 * @param str1 第一个字符串
	 * @param str2 第二个字符串
	 * @return 较大的字符串
	 */
	public static String max(String str1, String str2) {
		// 获取长字符串
		String maxStr = str1.length() >= str2.length()? str1 : str2;
		// 获取短字符串
		String minStr = maxStr.equals(str1) ? str2: str1;
		
		// 逐位比较两字符串的字符大小
		for (int i=0; i<minStr.length(); i++) {
			// 获取字符int值
			int maxNum = (int)maxStr.charAt(i);
			int minNum = (int)minStr.charAt(i);
			// 比较字符int值大小
			if (maxNum > minNum)
				return maxStr;
			else if (maxNum < minNum)
				return minStr;
		}
		// 若超出短字符串的长度，则长字符串大
		return maxStr;
	}
	
	/**
	 * 比较字符串大小
	 * @param str1 第一个字符串
	 * @param str2 第二个字符串
	 * @return -1(1<2), 0(1=2), 1(1>2)
	 */
	public static int compare(String str1, String str2) {
		if (str1.equals(str2))
			return 0;
		else if (max(str1, str2).equals(str1))
			return 1;
		else
			return -1;
	}
	
	/**
	 * 比较版本号
	 * @param ver1
	 * @param ver2
	 * @return 1(1>2), 0(1=2), -1(1<2)
	 * @author 翟仁元
	 */
	public static int compareVersion(String ver1, String ver2) {
		if (ver1 != null && ver2 == null) return 1;
		if (ver1 == null && ver2 == null) return 0;
		if (ver1 == null && ver2 != null) return -1;
		// 替换版本号前缀
		ver1 = ver1.replaceFirst("[^0-9]*", "");
		ver2 = ver2.replaceFirst("[^0-9]*", "");
		// 版本号格式校验
		if (!ver1.matches("[0-9]+(\\.[0-9]+)*") || !ver2.matches("[0-9]+(\\.[0-9]+)*")) {
			throw new IllegalArgumentException("比较的版本号格式错误");
		}
		
		// 比较版本号
		String[] nums1 = ver1.split("\\.");
		String[] nums2 = ver2.split("\\.");
		int min = (nums1.length > nums2.length ? nums2.length : nums1.length);
		int i1, i2;
		for (int i=0; i<min; i++) {
			i1 = Integer.parseInt(nums1[i]);
			i2 = Integer.parseInt(nums2[i]);
			if (i1 > i2) return 1;
			if (i1 < i2) return -1;
		}
		
		// 前缀完全相同, 则较长的大
		if (nums1.length > nums2.length) return 1;
		if (nums1.length < nums2.length) return -1;
		return 0;
	}
	
	/*********************************************************************
	 * 
	 * 随机字符
	 * 
	 *********************************************************************/
	
	/**
	 * 获取随机字符
	 * @param isFilter 是否过滤不易识别的字符
	 * @return 数值、小写字母、大写字母
	 * @author 翟仁元
	 */
	public static char randomCode(Boolean... isFilter) {
		boolean isfilter = isFilter != null && isFilter.length > 0 && isFilter[0] ? true : false;
		char code;
		do {
			// 0-61 共62位
			long i = Math.round(Math.random() * 61);
			if (i < 10) {
				// 0-9 数字
				code = (char)(48 + i);
			} else if (i < 36) {
				// 10-35 小写字母
				code = (char)(87 + i);
			} else {
				// 36-61 大写字母
				code = (char)(29 + i);
			}
		// filter: i:105 l:108 o:111 O:79
		} while(isfilter && (code == 'i' || code == 'l' || code == 'o' || code == 'O'));
		return code;
	}
	
	/**
	 * 获取指定长度的随机字符
	 * @param length
	 * @param isFilter 是否过滤不易识别的字符
	 * @return
	 * @author 翟仁元
	 */
	public static String randomCode(int length, Boolean... isFilter) {
		if (length <= 0) return "";
		
		StringBuffer result = new StringBuffer("");
		for (int i=0; i<length; i++) {
			result.append(randomCode(isFilter));
		}
		return result.toString();
	}
	
	/**
	 * 获取指定类型随机字符
	 * @param type<br>
	 * 格式：000<br>
	 * 100 随机数字<br>
	 * 010 随机小写字母<br>
	 * 001 随机大写字母<br>
	 * 必须至少制定一个类型<br>
	 * @param isFilter 是否过滤不易识别的字符
	 * @return [0-9][a-z][A-Z]
	 */
	public static char randomCode(String type, Boolean... isFilter) {
		if (type == null || type.matches("000") || !type.matches("[0,1]{3}")) 
			throw new NullPointerException("必须制定type, type格式为:[0,1]{3}");
		// 获取随机字符
		String ch = null;
		do {
			ch = randomCode(isFilter) + "";
			// 不包含数字
			if (type.charAt(0) == '0' && ch.matches("[0-9]")) ch = null;
			// 不包含小写字母
			if (ch != null && type.charAt(1) == '0' && ch.matches("[a-z]")) ch = null;
			// 不包含大写字母
			if (ch != null && type.charAt(2) == '0' && ch.matches("[A-Z]")) ch = null;
		} while (ch == null);
		// 返回随机字符
		return ch.charAt(0);
	}
	
	/**
	 * 分转元
	 * @param fen
	 * @return
	 * @author 翟仁元
	 */
	public static String fenToYuan(int fen) {
		String fenS = "";
		if(fen < 100) {
			fenS = StringUtil.complement(String.valueOf(fen), 3, '0', true);
		} else {
			fenS = String.valueOf(fen);
		}
		String yuanS = fenS.substring(0, fenS.length() -2) + "." + fenS.substring(fenS.length() -2, fenS.length());
		return yuanS;
	}
	
	/**
	 * 获取随机字符
	 * @param isFilter 是否过滤不易识别的字符
	 * @return
	 * @author 翟仁元
	 */
	public static char randomString(Boolean... isFilter) {
		return randomCode("011", isFilter);
	}
	
	/**
	 * 获取指定长度的随机字符
	 * @param length
	 * @param isFilter 是否过滤不易识别的字符
	 * @return
	 * @author 翟仁元
	 */
	public static String randomString(int length, Boolean... isFilter) {
		if (length <= 0) return "";
		
		StringBuffer result = new StringBuffer("");
		for (int i=0; i<length; i++) {
			result.append(randomString(isFilter));
		}
		return result.toString();
	}
	
	/**
	 * 获取随机数字
	 * @return
	 * @author 翟仁元
	 */
	public static char randomNum() {
		return randomCode("100");
	}
	
	/**
	 * 获取指定长度的随机数字
	 * @param length
	 * @return
	 * @author 翟仁元
	 */
	public static String randomNum(int length) {
		if (length <= 0) return "";
		
		StringBuffer result = new StringBuffer("");
		for (int i=0; i<length; i++) {
			result.append(randomNum());
		}
		return result.toString();
	}
	
	/**
	 * 全排列数组
	 * @param array
	 * @return
	 * @author 翟仁元
	 */
	@SuppressWarnings("unused")
	private static String[] fullArray(String[] array) {
		if (array.length == 1) 
			return new String[]{array[0]};
		
		// 数组长度
		int len = array.length;
		int size = 1;
		for (int i=1; i<=len; i++) {
			size *= i;
		}
		String[] rtn = new String[size];
		
		// 递归
		String[] temp = new String[len - 1];	// 临时数组
		String first;
		int index = 0;	// 当前索引
		for (int i=0; i<len; i++) {
			first = array[index];
			// 重组temp
			int tempJ = 0;
			for (int j=0; j<len; j++) {
				if (index != j) {
					temp[tempJ++] = array[j];
				}
			}
			// 拼接递归内容
			String[] result = fullArray(temp);
			for (int j=0; j<result.length; j++) {
				rtn[j + i * (size / len)] = first + result[j];
			}
			index++;
		}
		return rtn;
	}
	
	/**
	 * 拼接请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestParams(HttpServletRequest request) {
		StringBuffer requestParamsSB = new StringBuffer();
		Enumeration<String> paramNameEnum = request.getParameterNames();
		while (paramNameEnum.hasMoreElements()) {
			String paramName = (String) paramNameEnum.nextElement();
			requestParamsSB.append(paramName + "=");
			String[] valueArr = request.getParameterValues(paramName);
			for (int i = 0; i < valueArr.length; i++) {
				requestParamsSB.append(valueArr[i] + ",");
			}
			requestParamsSB.deleteCharAt(requestParamsSB.length() - 1);
			requestParamsSB.append("|");
		}
		requestParamsSB.deleteCharAt(requestParamsSB.length() - 1);
		return requestParamsSB.toString();
	}
	
}


