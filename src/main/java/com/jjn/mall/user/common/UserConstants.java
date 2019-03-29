package com.jjn.mall.user.common;

/**
 * 用户相关常量
 * 
 * @author 翟仁元
 *
 */
public class UserConstants {
	/** 父权限默认编号: 0 */
	public static int AUTH_DEFAULT_PARENTID = 0;
	
	/** 权限状态: 待审 */
	public static int AUTH_STATUS_WAITING = 0;
	/** 权限状态: 正常 */
	public static int AUTH_STATUS_NORMAL = 1;
	/** 权限状态: 注销 */
	public static int AUTH_STATUS_WRITTENOFF = 2;
	
	/** 角色状态: 待审 */
	public static int ROLE_STATUS_WAITING = 0;
	/** 角色状态: 正常 */
	public static int ROLE_STATUS_NORMAL = 1;
	/** 角色状态: 注销 */
	public static int ROLE_STATUS_WRITTENOFF = 2;
	
	/** 部门状态: 待审 */
	public static int DEPARTMENT_STATUS_WAITING = 0;
	/** 部门状态: 正常 */
	public static int DEPARTMENT_STATUS_NORMAL = 1;
	/** 部门状态: 注销 */
	public static int DEPARTMENT_STATUS_WRITTENOFF = 2;
	
	/** 用户状态: 待审 */
	public static int USER_STATUS_WAITING = 0;
	/** 用户状态: 正常 */
	public static int USER_STATUS_NORMAL = 1;
	/** 用户状态: 注销 */
	public static int USER_STATUS_WRITTENOFF = 2;
	
	/** 用户账户类型: 总部 */
	public static int USER_TYPE_HQ = 0;
	/** 用户账户类型: 商户 */
	public static int USER_TYPE_MERCHANT = 1;
	
}
