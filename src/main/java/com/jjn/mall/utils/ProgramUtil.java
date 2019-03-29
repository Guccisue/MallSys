package com.jjn.mall.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工具类：项目工具
 * 
 * @author 翟仁元
 */
public class ProgramUtil {
	private ProgramUtil() {}
	
	private static ProgramUtil util;
	public synchronized static ProgramUtil get() {
		if (util == null) {
			util = new ProgramUtil();
		}
		return util;
	}
	
	/**
	 * 添加Cookie
	 * @param resp
	 * @param name
	 * @param value
	 * @param expiry
	 * @param path
	 */
	public void addCookie(HttpServletResponse resp, String name, String value, int expiry, String path) {
		if (resp == null || name == null) { return; }
		// 刷新桌面
		Cookie ck = new Cookie(name, value);
		ck.setMaxAge(expiry);
		ck.setPath((path == null || "".equals(path) ? "/" : path));
		resp.addCookie(ck);
	}
	
	/**
	 * 获取Cookie
	 * @param req
	 * @param name
	 * @return
	 */
	public String getCookie(HttpServletRequest req, String name) {
		if (req == null || name == null) { return null; }
		// 获取Cookie
		Cookie[] cks = req.getCookies();
		if (cks != null) {
			for (Cookie ck : cks) {
				if (ck == null) { continue; }
				if (name.equals(ck.getName())) {
					return ck.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取浏览器类型
	 * @param req
	 * @return
	 */
	public String getAgent(HttpServletRequest req) {
		String userAgent = req.getHeader("user-agent");
		if (userAgent == null) { return null; }
		/**
		 * WAP浏览器类型
		 * iPhone:
		 * Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_8 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8E401 Safari/6533.18.5
		 * 
		 * 小米：
		 * Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.34 Safari/534.24
		 *  
		 * UC - UCWEB:
		 * JUC (Linux; U; 4.0.3; zh-cn; HTC_S710d; 480*800) UCWEB7.9.4.145/139/7701
		 * 
		 * window phone - IEMobile:
		 * Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; NOKIA; Nokia 800C)
		 * 
		 * 360
		 * Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_7; en-us) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Safari/530.17; 360browser(securitypay,securityinstalled); 360 Aphone Browser (3.2.1)
		 */
		if (userAgent.matches(".*Android.*")
			|| userAgent.matches(".*iPhone.*")
			|| userAgent.matches(".*X[0-9]+.*")
			|| userAgent.matches(".*UCWEB.*")
			|| userAgent.matches(".*IEMobile.*")
			|| userAgent.matches(".*360 Aphone Browser.*")) {
			// wap
			return "wap";
		}
		/**
		 * Chrome:
		 * Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.57 Safari/537.36
		 * 
		 * IE:
		 * Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; EIE10;ZHCNMCM)
		 */
		if (userAgent.matches(".*MSIE.*")) { return "MSIE"; }
		return null;
	}
	
	/**
	 * 解析header
	 * @param request
	 */
	public void resolveRequestHeader(HttpServletRequest request) {
		// 解析header
		System.out.println("==== Header ====");
//		host: 127.0.0.1:9090
//		connection: keep-alive
//		content-length: 47748
//		cache-control: max-age=0
//		accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//		origin: http://127.0.0.1:9090
//		user-agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.57 Safari/537.36
//		content-type: multipart/form-data; boundary=----WebKitFormBoundaryiA4BeVBTAyjc1fF7
//		referer: http://127.0.0.1:9090/vlan/jsp/other/upload.jsp
//		accept-encoding: gzip,deflate,sdch
//		accept-language: zh-CN,zh;q=0.8
//		cookie:  vlan.token=fw4gAQ6vfp9f3ESBQBWsdTkw32qVP6dt3igm1sL6SYZJfc6aJ4BLwlwxdqdfg21Q;
		Enumeration<String> headers = request.getHeaderNames();
		String h;
		while (headers.hasMoreElements()) {
			h = headers.nextElement();
			System.out.println(h + ": " + request.getHeader(h));
		}
	}
	
	/**
	 * 获取项目根目录
	 * @return 项目根目录
	 */
	public String rootPath(){
		// 获取完整目录
		String path = this.getClass().getResource("/").getPath();
		try {
			// 设置编码格式为 utf-8 以去除中文乱码
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 截取web-inf，获取根目录
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") - 1);
		}
		return path;
	}
	
	public static void main(String[] args) {
		System.out.println(ProgramUtil.get().rootPath());
	}
}
