package com.jjn.mall.merchant.common;

/**
 * 商户相关常量类
 * 
 * @author 翟仁元
 *
 */
public class MerchantConstants {
	/** 上级类目默认编号: 0 */
	public static int CATEGORY_DEFAULT_PARENTID = 0;
	
	/** 类目级别: 一级 */
	public static int CATEGORY_LEVEL_FIRST = 0;
	/** 类目级别: 二级 */
	public static int CATEGORY_LEVEL_SECOND = 1;
	
	/** 类目状态: 待审 */
	public static int CATEGORY_STATUS_WAITING = 0;
	/** 类目状态: 正常 */
	public static int CATEGORY_STATUS_NORMAL = 1;
	/** 类目状态: 注销 */
	public static int CATEGORY_STATUS_WRITTENOFF = 2;

	/** 商户状态: 待审 */
	public static int MERCHANT_STATUS_WAITING = 0;
	/** 商户状态: 正常 */
	public static int MERCHANT_STATUS_NORMAL = 1;
	/** 商户状态: 注销 */
	public static int MERCHANT_STATUS_WRITTENOFF = 2;
	
}
