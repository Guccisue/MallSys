package com.jjn.mall.common;

public class Constants {
	/** 调用后台情况: 成功 */
	public static int REQUEST_RESULT = 0;
	/** 调用后台情况: 异常 */
	public static int REQUEST_EXCEPTION = 1;
	
	/** 新增结果: 成功 */
	public static int ADD_SUCCESS = 0;
	/** 新增结果: 失败 */
	public static int ADD_FAIL = 1;
	
	/** 更新结果: 成功 */
	public static int UPDATE_SUCCESS = 0;
	/** 更新结果: 失败 */
	public static int UPDATE_FAIL = 1;
	
	/** 删除结果: 成功 */
	public static int DELETE_SUCCESS = 0;
	/** 删除结果: 失败 */
	public static int DELETE_FAIL = 1;
	
	public static int DELETE_FAIL_ = 2;
	
	
	/** 查询结果: 成功 */
	public static int SELECT_SUCCESS = 0;
	/** 查询结果: 失败 */
	public static int SELECT_FAIL = 1;
	
	/** 绑定结果: 成功 */
	public static int BIND_SUCCESS = 0;
	/** 绑定结果: 失败 */
	public static int BIND_FAIL = 1;
	
	/** 登录结果: 成功 */
	public static int LOGIN_SUCCESS = 0;
	
	/** 用户级别: 开发人员 */
	public static int USER_LEVEL_GODLIKE = 0;
	
	/**	错误码:入参有误 */
	public static int ERROR_CODE_92000 = -92000;
	/**	错误码:名称已存在 */
	public static int ERROR_CODE_92001 = -92001;
	/**	错误码:存在绑定关系 */
	public static int ERROR_CODE_92002 = -92002;
	/**	错误码:绑定部分成功 */
	public static int ERROR_CODE_92003 = -92003;
	/**	错误码:用户名或密码错误 */
	public static int ERROR_CODE_92004 = -92004;
	/**	错误码:统一社会信用代码已存在 */
	public static int ERROR_CODE_92005 = -92005;
	/**	错误码:文件格式不正确 */
	public static int ERROR_CODE_92006 = -92006;
	/**	错误码:该商户还有未下架商品 */
	public static int ERROR_CODE_92007 = -92007;
	/**	错误码:一级类目下还有二级类目 */
	public static int ERROR_CODE_92008 = -92008;
	/**	错误码:固定类目，不得删除 */
	public static int ERROR_CODE_92009 = -92009;
	/**	错误码:总部所建模板，不得删除 */
	public static int ERROR_CODE_92010 = -92010;
	/**	错误码:扣点数据时间有重合 */
	public static int ERROR_CODE_92011 = -92011;
	/**	错误码:开始时间不能大于结束时间 */
	public static int ERROR_CODE_92012 = -92012;
	/**	错误码:没有操作权限 */
	public static int ERROR_CODE_92013 = -92013;
	/**	错误码:类目跟模板存在绑定 */
	public static int ERROR_CODE_92014 = -92014;
	/**	错误码:秒杀商品无法设置为抽奖商品 */
	public static int ERROR_CODE_92015 = -92015;
	/**	错误码:金币数不能高于价格 */
	public static int ERROR_CODE_92016 = -92016;
	/**	错误码:商品属性不得含有/ */
	public static int ERROR_CODE_92017 = -92017;
	/**	错误码:该商品数量过多 */
	public static int ERROR_CODE_92018 = -92018;
	/**	错误码:序号无法相同 */
	public static int ERROR_CODE_92019 = -92018;
	/**	错误码:其他 */
	public static int ERROR_CODE_92099 = -92099;
	/**密钥  */
	public static String SECRET_KEY="5USaIC0KqBJIaxvykPEnLg==";
}
