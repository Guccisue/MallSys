package com.jjn.mall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP请求客户端
 * @Title: HttpURLConnectionUtil<br>
 * @Package: com.ott.util.HttpURLConnectionClient<br>
 * 
 * @author 翟仁元
 */
public class HttpURLConnectionClient {
	/** GET请求 */
	public static final String GET = "GET";
	/** POST请求 */
	public static final String POST = "POST";
	
	/** charSet UTF-8 */
	public static final String UTF8 = "UTF-8";
	/** charSet GBK */
	public static final String GBK = "GBK";
	/** charSet BIG5 */
	public static final String BIG5 = "BIG5";
	/** charSet GB2312 */
	public static final String GB2312 = "GB2312";
	
	/** 连接超时 */
	private int connectTimeout = 5  * 1000;
	/** 读取超时 */
	private int readTimeout = 300 * 1000;
	/** 响应Code */
	private int responseCode;

	/**
	 * 打开HTTP连接
	 * @param method
	 * @param service
	 * @param connectTimeout
	 * @param readTimeout
	 * @param proxy
	 * @return
	 * @throws IOException
	 * @author 翟仁元
	 */
	public HttpURLConnection openConnection(String method, String service, int connectTimeout, int readTimeout, Proxy... proxy) 
		throws IOException {
		// 请求方式验证
		if (method == null || !method.matches(GET + "|" + POST))
			throw new IllegalArgumentException("请求方式错误");
		// 请求路径验证
		if (service == null || "".equals(service))
			throw new NullPointerException("请求地址不可为空");
		
		// 设置请求
		String path = service;
		URL url = new URL(path);
//		System.out.println("URL: " + path);
		// 获取连接
		Proxy p = proxy != null && proxy.length > 0 ? proxy[0] : null;
		HttpURLConnection conn = (HttpURLConnection)(p == null ? url.openConnection() : url.openConnection(p));
		
		// 设置默认属性
		conn.setRequestMethod(method); // 请求方式
		conn.setConnectTimeout(connectTimeout); // 连接超时
		conn.setReadTimeout(readTimeout); // 读取超时
		if (GET.equalsIgnoreCase(method)) {
			conn.setDoInput(true); // 输入操作
		} else {
			conn.setDoOutput(true); // 输出操作
		}
		// 设置请求属性
		setCacheControl(conn, "no-cache");
		setPragma(conn, "no-cache");
		setExpires(conn, "-1"); // 过期时间
		setConnection(conn, "keep-alive");
		
		// 设置代理属性
		if (p != null) {
//			conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
//			conn.setRequestProperty("Accept","*/*");
//			conn.setRequestMethod("GET");
//			conn.setUseCaches(false);
//			conn.setRequestProperty("Content-Type", "application/octet-stream");
//			conn.addRequestProperty("Accept-Encoding", "gzip");
			
			// 格式: "Proxy-Authorization"= "Basic Base64.encode(user:password)"
//			String value = "Basic " + Base64.encodeBase64((host + ":" + port).getBytes());
//			conn.setRequestProperty("Proxy-Authorization", value);
		}
		
		return conn;
	}
	
	/**
	 * 打开HTTP连接
	 * @param method
	 * @param service
	 * @param proxy
	 * @return
	 * @throws IOException
	 * @author 翟仁元
	 */
	public HttpURLConnection openConnection(String method, String service, Proxy... proxy) 
		throws IOException {
		return openConnection(method, service, connectTimeout, readTimeout, proxy);
	}
	
	/**
	 * 使用代理
	 * @param host
	 * @param port
	 * @author 翟仁元
	 */
	public Proxy usingProxy(String host, int port) {
		// 实例化代理
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
		return proxy;
	}
	
	/**
	 * 发送请求
	 * @param conn
	 * @param method
	 * @param service
	 * @param params
	 * @param charSet
	 * @param proxy
	 * @return
	 * @throws IOException
	 * @author 翟仁元
	 */
	public String send(HttpURLConnection conn, String params, String charSet) 
		throws IOException {		
		// 设置编码格式
		setContentEncoding(conn, charSet);
		// 设置内容长度
		setContentLength(conn, (params != null ? params.length() : 0) + "");
		
		// 发送请求
		InputStream input;
		if (conn.getDoOutput() && params != null) {
			// POST, 输出字节
//			System.out.println("POST");
			// 获取输出
			byte[] bytes = params.getBytes(charSet);
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		} else {
			// GET, 发起连接
//			System.out.println("GET");
			conn.connect();
		}
		
		// 打印HeaderFields
//		System.out.println("----------- HeaderFields ------------");
//		for (Entry<String, List<String>> h : conn.getHeaderFields().entrySet()) {
//			System.out.println(h.getKey());
//			for (String s : h.getValue()) {
//				System.out.println(s);
//			}
//			System.out.println("-----------------------");
//		}
//		System.out.println(conn.usingProxy());
//		System.out.println(conn.getURL());
		
		// 获取输入流
		input = conn.getInputStream();
		StringBuilder response = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charSet));
		String line;
		while ((line = reader.readLine()) != null) {
			response.append(line + "\n");
		}
		reader.close();
		// 设置响应code
		responseCode = conn.getResponseCode();
		
		// 关闭连接
		conn.disconnect();
		input.close();
		// 返回文档
		return response.toString();
	}
	
	/**
	 * 发送请求
	 * @param method
	 * @param service
	 * @param params
	 * @param charSet
	 * @param proxy 代理，使用client.usingProxy(host, port)获取
	 * @return
	 * @throws IOException
	 * @author 翟仁元
	 */
	public String send(String method, String service, String params, String charSet, Proxy... proxy) 
		throws IOException {
		// 获取连接
		HttpURLConnection conn = openConnection(method, service, proxy);
		// 发送请求
		return send(conn, params, charSet);
	}
	
	/**
	 * 发送XML请求
	 * @param conn
	 * @param xml
	 * @param charSet
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author 翟仁元
	 */
	public String sendXML(HttpURLConnection conn, String xml, String charSet) 
		throws IOException {
		// 设置内容类型为XML
		setContentType(conn, "text/xml");
		return send(conn, xml, charSet);
	}
	
	/**
	 * 发送XML请求
	 * @param service
	 * @param xml
	 * @param charSet
	 * @param proxy 代理，使用client.usingProxy(host, port)获取
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author 翟仁元
	 */
	public String sendXML(String service, String xml, String charSet, Proxy... proxy) 
		throws IOException {
		// 获取连接
		HttpURLConnection conn = openConnection(POST, service, proxy);
		// 发送请求
		String response = sendXML(conn, xml, charSet);
		return response;
	}
	
	/**
	 * 获取响应码
	 * @return
	 * @author 翟仁元
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**********************************************************
	 * 
	 * 设置RequestProperty
	 * 
	 **********************************************************/

	/**
	 * 浏览器可接受的MIME类型
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setAccept(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Accept", value);
	}
	
	/**
	 * 浏览器可接受的字符集
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setAcceptCharset(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Accept-Charset", value);
	}
	
	/**
	 * 浏览器能够进行解码的数据编码方式，比如gzip<br>
	 * Servlet能够向支持gzip的浏览器返回经gzip编码的HTML页面<br>
	 * 许多情形下这可以减少5到10倍的下载时间
	 * @param conn
	 * @param value
	 * @author: 翟仁元<br>
	 * @date: 2014-5-19<br>
	 */
	public void setAcceptEncoding(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Accept-Encoding", value);
	}
	
	/**
	 * 浏览器所希望的语言种类,当服务器能够提供一种以上的语言版本时要用到
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setAcceptLanguage(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Accept-Language", value);
	}
	
	/**
	 * 授权信息,通常出现在对服务器发送的WWW-Authenticate头的应答中
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setAuthorization(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Authorization", value);
	}
	
	/**
	 * 表示是否需要持久连接<br>
	 * 如果Servlet看到这里的值为"Keep-Alive",或者看到请求使用的是HTTP 1.1(HTTP 1.1默认进行持久连接),<br>
	 * 它就可以利用持久连接的优点,当页面包含多个元素时(例如Applet,图片),显著地减少下载所需要的时间<br>
	 * 要实现这一点,Servlet需要在应答中发送一个Content-Length头,最简单的实现方法是：<br>
	 * 先把内容写入ByteArrayOutputStream,然后在正式写出内容之前计算它的大小
	 * @param conn
	 * @param value keep-alive
	 * @author 翟仁元
	 */
	public void setConnection(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Connection", value);
	}
	
	/**
	 * 代理连接
	 * @param conn
	 * @param value keep-alive
	 * @author 翟仁元
	 */
	public void setProxyConnection(HttpURLConnection conn, String value) {
		conn.setRequestProperty("proxy-connection", value);
	}
	
	/**
	 * 表示请求消息正文的长度
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setContentLength(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Content-Length", value);
	}
	
	/**
	 * 这是最重要的请求头信息之一
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setCookie(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Cookie", value);
	}
	
	/**
	 * 请求发送者的email地址,由一些特殊的Web客户程序使用,浏览器不会用到它
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setFrom(HttpURLConnection conn, String value) {
		conn.setRequestProperty("From", value);
	}
	
	/**
	 * 初始URL中的主机和端口
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setHost(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Host", value);
	}
	
	/**
	 * 只有当所请求的内容在指定的日期之后又经过修改才返回它,否则返回304“Not Modified”应答
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setIfModifiedSince(HttpURLConnection conn, String value) {
		conn.setRequestProperty("If-Modified-Since", value);
	}
	
	/**
	 * 指定"no-cache"值表示服务器必须返回一个刷新后的文档,即使它是代理服务器而且已经有了页面的本地拷贝
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setPragma(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Pragma", value);
	}
	
	/**
	 * 包含一个URL,用户从该URL代表的页面出发访问当前请求的页面
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setReferer(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Referer", value);
	}
	
	/**
	 * 浏览器类型<br>
	 * 如果Servlet返回的内容与浏览器类型有关则该值非常有用
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setUserAgent(HttpURLConnection conn, String value) {
		conn.setRequestProperty("User-Agent", value);
	}
	
	/**
	 * 文档内容类型
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setContentType(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Content-Type", value);
	}
	
	/**
	 * 文档内容编码格式
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setContentEncoding(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Content-Encoding", value);
	}
	
	/**
	 * 应该在什么时候认为文档已经过期,从而不再缓存它
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setExpires(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Expires", value);
	}
	
	/**
	 * 缓存控制
	 * @param conn
	 * @param value
	 * @author 翟仁元
	 */
	public void setCacheControl(HttpURLConnection conn, String value) {
		conn.setRequestProperty("Cache-Control", value);
	}

	/**********************************************************
	 * 
	 * 状态码
	 * 
	 **********************************************************/
	
//	http状态返回代码 1xx（临时响应）
//	表示临时响应并需要请求者继续执行操作的状态代码。
//
//	http状态返回代码 代码 说明
//	100 （继续） 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
//	101 （切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换。
//
//	http状态返回代码 2xx （成功）
//	表示成功处理了请求的状态代码。
//
//	http状态返回代码 代码 说明
//	200 （成功） 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
//	201 （已创建） 请求成功并且服务器创建了新的资源。
//	202 （已接受） 服务器已接受请求，但尚未处理。
//	203 （非授权信息） 服务器已成功处理了请求，但返回的信息可能来自另一来源。
//	204 （无内容） 服务器成功处理了请求，但没有返回任何内容。
//	205 （重置内容） 服务器成功处理了请求，但没有返回任何内容。
//	206 （部分内容） 服务器成功处理了部分 GET 请求。
//
//	http状态返回代码 3xx （重定向）
//	表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
//
//	http状态返回代码 代码 说明
//	300 （多种选择） 针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。
//	301 （永久移动） 请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。
//	302 （临时移动） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
//	303 （查看其他位置） 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。
//	304 （未修改） 自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。
//	305 （使用代理） 请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。
//	307 （临时重定向） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
//	http状态返回代码 4xx（请求错误）
//	这些状态代码表示请求可能出错，妨碍了服务器的处理。
//
//	http状态返回代码 代码 说明
//	400 （错误请求） 服务器不理解请求的语法。
//	401 （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
//	403 （禁止） 服务器拒绝请求。
//	404 （未找到） 服务器找不到请求的网页。
//	405 （方法禁用） 禁用请求中指定的方法。
//	406 （不接受） 无法使用请求的内容特性响应请求的网页。
//	407 （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
//	408 （请求超时） 服务器等候请求时发生超时。
//	409 （冲突） 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
//	410 （已删除） 如果请求的资源已永久删除，服务器就会返回此响应。
//	411 （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
//	412 （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
//	413 （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
//	414 （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
//	415 （不支持的媒体类型） 请求的格式不受请求页面的支持。
//	416 （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
//	417 （未满足期望值） 服务器未满足”期望”请求标头字段的要求。
//
//	http状态返回代码 5xx（服务器错误）
//	这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
//
//	http状态返回代码 代码 说明
//	500 （服务器内部错误） 服务器遇到错误，无法完成请求。
//	501 （尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
//	502 （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
//	503 （服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
//	504 （网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
//	505 （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。
//
//	一些常见的http状态返回代码为：
//	200 – 服务器成功返回网页
//	404 – 请求的网页不存在
//	503 – 服务不可用

	/**********************************************************
	 * 
	 * HTTP应答头概述(HttpServletResponse)
	 * 
	 **********************************************************/
	
//	HTTP应答头概述（HttpServletResponse）
//	Web服务器的HTTP应答一般由以下几项构成：一个状态行，一个或多个应答头，一个空行，内容文档。设置HTTP应答头往往和设置状态行中的状态代码结合起来。例如，有好几个表示“文档位置已经改变”的状态代码都伴随着一个Location头，而401（Unauthorized）状态代码则必须伴随一个WWW-Authenticate头。
//	然而，即使在没有设置特殊含义的状态代码时，指定应答头也是很有用的。应答头可以用来完成：设置Cookie，指定修改日期，指示浏览器按照指定的间隔刷新页面，声明文档的长度以便利用持久HTTP连接，……等等许多其他任务。
//	设置应答头最常用的方法是HttpServletResponse的setHeader，该方法有两个参数，分别表示应答头的名字和值。和设置状态代码相似，设置应答头应该在发送任何文档内容之前进行。
//	setDateHeader方法和setIntHeadr方法专门用来设置包含日期和整数值的应答头，前者避免了把Java时间转换为GMT时间字符串的麻烦，后者则避免了把整数转换为字符串的麻烦。
//	HttpServletResponse还提供了许多设置
//	setContentType：设置Content-Type头。大多数Servlet都要用到这个方法。
//	setContentLength：设置Content-Length头。对于支持持久HTTP连接的浏览器来说，这个函数是很有用的。
//	addCookie：设置一个Cookie（Servlet API中没有setCookie方法，因为应答往往包含多个Set-Cookie头）。
//	另外，如上节介绍，sendRedirect方法设置状态代码302时也会设置Location头。
//	HTTP应答头 说明
//	Allow 服务器支持哪些请求方法（如GET、POST等）。
//	Content-Encoding 文档的编码（Encode）方法。只有在解码之后才可以得到Content-Type头指定的内容类型。利用gzip压缩文档能够显著地减少HTML文档的下载时间。Java的GZIPOutputStream可以很方便地进行gzip压缩，但只有Unix上的Netscape和Windows上的IE 4、IE 5才支持它。因此，Servlet应该通过查看Accept-Encoding头（即request.getHeader("Accept-Encoding")）检查浏览器是否支持gzip，为支持gzip的浏览器返回经gzip压缩的HTML页面，为其他浏览器返回普通页面。
//	Content-Length 表示内容长度。只有当浏览器使用持久HTTP连接时才需要这个数据。如果你想要利用持久连接的优势，可以把输出文档写入ByteArrayOutputStram，完成后查看其大小，然后把该值放入Content-Length头，最后通过byteArrayStream.writeTo(response.getOutputStream()发送内容。
//	Content-Type 表示后面的文档属于什么MIME类型。Servlet默认为text/plain，但通常需要显式地指定为text/html。由于经常要设置Content-Type，因此HttpServletResponse提供了一个专用的方法setContentType。
//	Date 当前的GMT时间。你可以用setDateHeader来设置这个头以避免转换时间格式的麻烦。
//	Expires 应该在什么时候认为文档已经过期，从而不再缓存它？
//	Last-Modified 文档的最后改动时间。客户可以通过If-Modified-Since请求头提供一个日期，该请求将被视为一个条件GET，只有改动时间迟于指定时间的文档才会返回，否则返回一个304（Not Modified）状态。Last-Modified也可用setDateHeader方法来设置。
//	Location 表示客户应当到哪里去提取文档。Location通常不是直接设置的，而是通过HttpServletResponse的sendRedirect方法，该方法同时设置状态代码为302。
//	Refresh 表示浏览器应该在多少时间之后刷新文档，以秒计。除了刷新当前文档之外，你还可以通过setHeader("Refresh", "5; URL=http://host/path")让浏览器读取指定的页面。注意这种功能通常是通过设置HTML页面HEAD区的<META HTTP-EQUIV="Refresh" CONTENT="5;URL=http://host/path">实现，这是因为，自动刷新或重定向对于那些不能使用CGI或Servlet的HTML编写者十分重要。但是，对于Servlet来说，直接设置Refresh头更加方便。注意Refresh的意义是“N秒之后刷新本页面或访问指定页面”，而不是“每隔N秒刷新本页面或访问指定页面”。因此，连续刷新要求每次都发送一个Refresh头，而发送204状态代码则可以阻止浏览器继续刷新，不管是使用Refresh头还是<META HTTP-EQUIV="Refresh" ...>。注意Refresh头不属于HTTP 1.1正式规范的一部分，而是一个扩展，但Netscape和IE都支持它。
//	Server 服务器名字。Servlet一般不设置这个值，而是由Web服务器自己设置。
//	Set-Cookie 设置和页面关联的Cookie。Servlet不应使用response.setHeader("Set-Cookie", ...)，而是应使用HttpServletResponse提供的专用方法addCookie。参见下文有关Cookie设置的讨论。
//	WWW-Authenticate 客户应该在Authorization头中提供什么类型的授权信息？在包含401（Unauthorized）状态行的应答中这个头是必需的。例如，response.setHeader("WWW-Authenticate", "BASIC realm=\"executives\"")。注意Servlet一般不进行这方面的处理，而是让Web服务器的专门机制来控制受密码保护页面的访问（例如.htaccess）。

	/**********************************************************
	 * 
	 * 相关操作
	 * 
	 **********************************************************/

	/**
	 * 获取客户端真是IP
	 * @param request
	 * @return
	 * @author 翟仁元
	 */
	public synchronized static String getRemoteAddr(HttpServletRequest request) {
		if (request == null) return null;
		// 
		String remoteAddr;
		// X-Forwarded-For
		String xff = request.getHeader("X-Forwarded-For");
		remoteAddr = (xff == null ? request.getHeader("x-forwarded-for") : xff);
		// Proxy-Client-IP
		if (remoteAddr == null || "".equals(remoteAddr)) {
			String pci = request.getHeader("Proxy-Client-IP");
			remoteAddr = (pci == null ? request.getHeader("proxy-client-ip") : pci);
		}
		// WL-Proxy-Client-IP
		if (remoteAddr == null || "".equals(remoteAddr)) {
			String wpci = request.getHeader("WL-Proxy-Client-IP");
			remoteAddr = (wpci == null ? request.getHeader("wl-proxy-client-ip") : wpci);
		}
		// RemoteAddr
		if (remoteAddr == null || "".equals(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		} else {
			remoteAddr = remoteAddr.split(",")[0];
		}
		
		return remoteAddr;
	}
	
	/**
	 * 测试
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		HttpURLConnectionClient client = new HttpURLConnectionClient();
		// 代理属性
		// 122.96.59.106:82 江苏 南京
		// 183.129.198.247:81 浙江 杭州
		// 116.226.77.86:8080 上海
		// 218.240.156.82:80 福建 福州
		// 58.20.127.178:3128 湖南 长沙
		String host = "58.20.127.178";
		int port = 3128;
		// 打开连接
		// http://202.102.113.118:8080/VersionManager/ver/showInfo
		// http://ajiehun.b37.80data.com/Vote.asp?id=56
		HttpURLConnection conn = client.openConnection(
				HttpURLConnectionClient.POST, 
				"http://www.baidu.com" 
//				, client.usingProxy(host, port)
				);
		// 设置header
//		client.setHost(conn, host + ":" + port);
		client.setReferer(conn, "http://ajiehun.b37.80data.com/index.asp");
		// 发送请求
		String response = client.send(conn, 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><SubscribeInfo><Subscribe Action=\"ADD\" accountCode=\"45\" productCode=\"PRODUCT2013101000000003\" /></SubscribeInfo>", 
				HttpURLConnectionClient.UTF8);
		// 打印响应
		System.out.println(client.getResponseCode());
		System.out.println(response);
	}
}
