package com.jjn.mall.user.dao;

import java.util.List;

import com.jjn.mall.user.dao.pojo.TAuthInfo;
import com.jjn.mall.user.dao.pojo.TDepartmentInfo;
import com.jjn.mall.user.dao.pojo.TRoleAuth;
import com.jjn.mall.user.dao.pojo.TRoleInfo;
import com.jjn.mall.user.dao.pojo.TUserInfo;
import com.jjn.mall.user.model.DepartmentModel;
import com.jjn.mall.user.model.RoleModel;
import com.jjn.mall.user.model.UserInfoModel;

/**
 * 用户相关DAO
 * 
 * @author 翟仁元
 *
 */
public interface IUserInfoDao {
	/*
	 * 权限管理部分======Start
	 */
	/**
	 * 新增权限信息
	 * @param authInfo
	 * @return
	 * @throws Exception
	 */
	public int addAuth(TAuthInfo authInfo) throws Exception;
	
	/**
	 * 校验权限名称是否已存在
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int selectAuthByName(TAuthInfo authInfo) throws Exception;
	
	/**
	 * 更新权限信息
	 * @param authInfo
	 * @return
	 * @throws Exception
	 */
	public int updateAuth(TAuthInfo authInfo) throws Exception;
	
	/**
	 * 删除权限信息
	 * @param authInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteAuth(TAuthInfo authInfo) throws Exception;
	
	/**
	 * 查找权限信息
	 * @param authInfo
	 * @return
	 * @throws Exception
	 */
	public List<TAuthInfo> getAllAuth(TAuthInfo authInfo) throws Exception;
	
	/**
	 * 查找权限信息
	 * @param authInfo
	 * @return
	 * @throws Exception
	 */
	public List<TAuthInfo> getAuth(TAuthInfo authInfo) throws Exception;
	/*
	 * 权限管理部分======End
	 */
	
	/*
	 * 角色权限绑定管理部分======Start
	 */
	/**
	 * 查看是否存在角色权限绑定关系
	 * @param tro
	 * @return
	 * @throws Exception
	 */
	public int selectRoleAuthBind(TRoleAuth tra) throws Exception;
	
	/**
	 * 角色权限绑定
	 * @param tra
	 * @return
	 * @throws Exception
	 */
	public int doRoleAuthBind(TRoleAuth tra) throws Exception;
	
	/**
	 * 角色权限解绑
	 * @param tra
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleAuth(TRoleAuth tra) throws Exception;
	
	/**
	 * 获得角色绑定的所有权限
	 * @param tra
	 * @return
	 * @throws Exception
	 */
	public List<TRoleAuth> getAllBindAuthId(TRoleAuth tra) throws Exception;
	
	/**
	 * 根据二级目录查询一级目录
	 * @param authIds
	 * @return
	 * @throws Exception
	 */
	public List<TAuthInfo> getParentIdById(String[] authIds) throws Exception;
	
	/*
	 * 角色权限绑定管理部分======End
	 */
	
	/*
	 * 角色管理部分======Start
	 */
	/**
	 * 新增权限信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public int addRole(TRoleInfo roleInfo) throws Exception;
	
	/**
	 * 校验角色名称是否已存在
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int selectRoleByName(TRoleInfo roleInfo) throws Exception;
	/**
	 * 更新角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public int updateRole(TRoleInfo roleInfo) throws Exception;
	
	/**
	 * 删除角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteRole(TRoleInfo roleInfo) throws Exception;
	
	/**
	 * 查找角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public List<TRoleInfo> getAllRole(RoleModel roleModel) throws Exception;
	
	/**
	 * 查询角色总数
	 * @param roleModel
	 * @return
	 * @throws Exception
	 */
	public int getRoleCount(RoleModel roleModel)throws Exception;
	
	/**
	 * 根据id查找角色信息
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public TRoleInfo getRoleById(TRoleInfo roleInfo) throws Exception;
	/*
	 * 角色管理部分======End
	 */
	
	
	/*
	 * 用户管理部分======Start
	 */
	/**
	 * 新增用户
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int addUser(TUserInfo userInfo) throws Exception;
	
	/**
	 * 校验用户名是否已存在
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int selectUserByName(TUserInfo userInfo) throws Exception;
	
	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int updateUser(TUserInfo userInfo) throws Exception;
	
	/**
	 * 注销用户
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteUser(TUserInfo userInfo) throws Exception;
	
	/**
	 * 修改密码
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public int updateUserPassword(TUserInfo userInfo) throws Exception;
	
	/**
	 * 用户登录校验
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public List<TUserInfo> loginUser(TUserInfo userInfo) throws Exception;
	
	/**
	 * 查找用户信息
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public List<TUserInfo> getAllUser(UserInfoModel userInfoModel) throws Exception;
	
	/**
	 * 查找用户总数
	 * @param userInfoModel
	 * @return
	 * @throws Exception
	 */
	public int getAllUserCount(UserInfoModel userInfoModel) throws Exception;
	
	/**
	 * 根据id查看用户
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public TUserInfo getUserById(TUserInfo userInfo) throws Exception;
	
	/**
	 * 查看角色信息是否被用户绑定
	 * @param roleInfo
	 * @return
	 * @throws Exception
	 */
	public int selectUserByRoleId(TRoleInfo roleInfo) throws Exception;
	
	/**
	 * 查看部门信息是否被用户绑定
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public int selectUserByDepartmentId(TDepartmentInfo departmentInfo) throws Exception;
	/*
	 * 用户管理部分======End
	 */
	
	/*
	 * 部门管理部分======Start
	 */
	/**
	 * 新增部门信息
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public int addDepartment(TDepartmentInfo departmentInfo) throws Exception;
	
	/**
	 * 更新部门信息
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public int updateDepartment(TDepartmentInfo departmentInfo) throws Exception;
	
	/**
	 * 校验部门名称是否已存在
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public int selectDepartmentByName(TDepartmentInfo departmentInfo) throws Exception;
	
	/**
	 * 删除部门信息
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteDepartment(TDepartmentInfo departmentInfo) throws Exception;
	
	/**
	 * 获得部门信息
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<TDepartmentInfo> getAllDepartment(DepartmentModel departmentModel) throws Exception;
	
	/**
	 * 获得部门总数
	 * @param departmentModel
	 * @return
	 * @throws Exception
	 */
	public int getAllDepartmentCount(DepartmentModel departmentModel) throws Exception;
	
	/**
	 * 得到树形部门列表
	 * @param departmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<TDepartmentInfo> getAllDepartmentTree(TDepartmentInfo departmentInfo) throws Exception;
	/*
	 * 部门管理部分======End
	 */
	
	public List<TAuthInfo> getAllAuthNoTree(int roleId) throws Exception;
}
