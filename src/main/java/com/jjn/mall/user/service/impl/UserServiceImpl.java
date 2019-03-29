package com.jjn.mall.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.common.Constants;
import com.jjn.mall.user.dao.IUserInfoDao;
import com.jjn.mall.user.dao.pojo.TAuthInfo;
import com.jjn.mall.user.dao.pojo.TDepartmentInfo;
import com.jjn.mall.user.dao.pojo.TRoleAuth;
import com.jjn.mall.user.dao.pojo.TRoleInfo;
import com.jjn.mall.user.dao.pojo.TUserInfo;
import com.jjn.mall.user.model.DepartmentModel;
import com.jjn.mall.user.model.RoleModel;
import com.jjn.mall.user.model.UserInfoModel;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.user.service.IUserService;

/**
 * 
 * @author 翟仁元
 *
 */
@Service(value="userService")
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserInfoDao userInfoDao;
	
	/*
	 * 权限管理部分======Start
	 */
	@Override
	public int addAuth(TAuthInfo authInfo) throws Exception{
		if(selectAuthByName(authInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.addAuth(authInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int selectAuthByName(TAuthInfo authInfo) throws Exception {
		return userInfoDao.selectAuthByName(authInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int updateAuth(TAuthInfo authInfo) throws Exception {
		if(selectAuthByName(authInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.updateAuth(authInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}
	
	@Override
	public int deleteAuth(TAuthInfo authInfo) throws Exception {
		TRoleAuth tro = new TRoleAuth();
		tro.setAuthId(authInfo.getAuthId());
		if(selectRoleAuthBind(tro) > 0) {
			return Constants.ERROR_CODE_92002;
		}
		return userInfoDao.deleteAuth(authInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAllAuth(List<TAuthInfo> authInfoList) throws Exception {
		List<Map> listMap = new ArrayList();
		for(TAuthInfo authInfo : authInfoList) {
			List<TAuthInfo> aiList = userInfoDao.getAllAuth(authInfo);
			Map tempMap = new HashMap();
			tempMap.put("authid", authInfo.getAuthId());
			tempMap.put("parentid", authInfo.getParentId());
			tempMap.put("name", authInfo.getName());
			tempMap.put("url", authInfo.getUrl());
			if(null != aiList && aiList.size() > 0) {
				tempMap.put("child", getAllAuth(aiList));
			}
			listMap.add(tempMap);
		}
		return listMap;
	}
	
	/*
	 * 权限管理部分======End
	 */

	/*
	 * 角色权限绑定管理部分======Start
	 */
	@Override
	public int selectRoleAuthBind(TRoleAuth tra) throws Exception {
		return userInfoDao.selectRoleAuthBind(tra);
	}
	
	@Override
	public int doRoleAuthBind(TRoleAuth tra) throws Exception {
		if(selectRoleAuthBind(tra) > 0) {
			return Constants.BIND_SUCCESS;
		}
		return userInfoDao.doRoleAuthBind(tra);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllBindAuthId(TRoleAuth tra) throws Exception {
		return userInfoDao.getAllBindAuthId(tra);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAllAuthAndMarkBinded(List<TAuthInfo> authInfoList, List<TAuthInfo> bindedAuthInfoList) throws Exception {
		List<Map> listMap = new ArrayList();
		for(TAuthInfo authInfo : authInfoList) {
			List<TAuthInfo> aiList = userInfoDao.getAllAuth(authInfo);
			System.out.println(aiList);
			Map tempMap = new HashMap();
			tempMap.put("authid", authInfo.getAuthId());
			tempMap.put("parentid", authInfo.getParentId());
			tempMap.put("name", authInfo.getName());
			tempMap.put("url", authInfo.getUrl());
			for(TAuthInfo tai : bindedAuthInfoList) {
				if(authInfo.getAuthId() == tai.getAuthId()) {
					tempMap.put("binded", Constants.BIND_SUCCESS);
					break;
				} else {
					tempMap.put("binded", Constants.BIND_FAIL);
				}
			}
			if(null != aiList && aiList.size() > 0) {
				tempMap.put("child", getAllAuthAndMarkBinded(aiList, bindedAuthInfoList));
			}
			listMap.add(tempMap);
		}
		System.out.println(listMap);
		return listMap;
	}
	
	/**
	 * 角色权限解绑
	 * @param tra
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleAuth(TRoleAuth tra) throws Exception {
		return userInfoDao.deleteRoleAuth(tra) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getParentIdById(String[] authIds) throws Exception {
		return userInfoDao.getParentIdById(authIds);
	}
	
	/*
	 * 角色权限绑定管理部分======End
	 */
	
	/*
	 * 角色管理部分======Start
	 */
	@Override
	public int addRole(TRoleInfo roleInfo) throws Exception {
		if(selectRoleByName(roleInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.addRole(roleInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int selectRoleByName(TRoleInfo roleInfo) throws Exception {
		return userInfoDao.selectRoleByName(roleInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}
	
	@Override
	public int updateRole(TRoleInfo roleInfo) throws Exception {
		if(selectRoleByName(roleInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.updateRole(roleInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}
	
	@Override
	public int deleteRole(TRoleInfo roleInfo) throws Exception {
		TRoleAuth tro = new TRoleAuth();
		tro.setRoleId(roleInfo.getRoleId());
		if(selectRoleAuthBind(tro) > 0) {
			return Constants.ERROR_CODE_92002;
		}
		if(selectUserByRoleId(roleInfo) > 0) {
			return Constants.ERROR_CODE_92002;
		}
		return userInfoDao.deleteRole(roleInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}
	
	@Override
	public TRoleInfo getRoleById(TRoleInfo roleInfo) throws Exception {
		return userInfoDao.getRoleById(roleInfo);
	}
	
	@Override
	public RoleModel getAllRole(RoleModel roleModel) throws Exception {
		roleModel.setRoleInfoList(userInfoDao.getAllRole(roleModel));
		roleModel.setCount(userInfoDao.getRoleCount(roleModel));
		return roleModel;
	}
	/*
	 * 角色管理部分======End
	 */

	/*
	 * 用户管理部分======Start
	 */
	@Override
	public int addUser(TUserInfo userInfo) throws Exception {
		if(selectUserByName(userInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.addUser(userInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int selectUserByName(TUserInfo userInfo) throws Exception {
		return userInfoDao.selectUserByName(userInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int updateUser(TUserInfo userInfo) throws Exception {
		if(selectUserByName(userInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.updateUser(userInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public int deleteUser(TUserInfo userInfo) throws Exception {
		return userInfoDao.deleteUser(userInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public int updateUserPassword(TUserInfo userInfo) throws Exception {
		return userInfoDao.updateUserPassword(userInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public UserModel loginUser(TUserInfo userInfo) throws Exception {
		UserModel userModel = new UserModel();
		List<TUserInfo> userInfoList = userInfoDao.loginUser(userInfo);
		if(null == userInfoList || userInfoList.size() < 1) {
			return null;
		}
		// 单个属性赋值
		TUserInfo resultUser = userInfoList.get(0);
		userModel.setAccount(resultUser.getAccount());
		userModel.setName(resultUser.getName());
		userModel.setPhone(resultUser.getPhone());
		userModel.setType(resultUser.getType());
		userModel.setMerchantId(resultUser.getMerchantId());
		userModel.setDepartmentId(resultUser.getDepartmentId());
		userModel.setUserId(resultUser.getUserId());
		userModel.setRoleId(resultUser.getRoleId());
		userModel.setStatus(resultUser.getStatus());
		
		// 获得用户拥有的权限编号
		/*List<TRoleAuth> traList = new ArrayList<>();
		if(resultUser.getRoleId() > 0) {
			TRoleAuth tra = new TRoleAuth();
			tra.setRoleId(resultUser.getRoleId());
			traList = userInfoDao.getAllBindAuthId(tra);
		}*/
		
		// 得到所有权限列表
		/*List<TAuthInfo> authInfoList = new ArrayList<>();
		for(TRoleAuth tra : traList) {
			TAuthInfo tai = new TAuthInfo();
			tai.setAuthId(tra.getAuthId());
			tai.setStatus(UserConstants.AUTH_STATUS_NORMAL);
			List<TAuthInfo> taiList = userInfoDao.getAuth(tai);
			if(null != taiList && taiList.size() > 0) {
				authInfoList.add(taiList.get(0));
			}
		}*/
		userModel.setAuthInfoList(getAllAuthNoTree(userModel.getRoleId()));
		return userModel;
	}
	
	public List<TAuthInfo> getAllAuthNoTree(int roleId) throws Exception {
		return userInfoDao.getAllAuthNoTree(roleId);
	}
	
	
	@Override
	public TUserInfo getUserById(TUserInfo userInfo) throws Exception {
		return userInfoDao.getUserById(userInfo);
	}
	
	@Override
	public UserInfoModel getAllUser(UserInfoModel userInfoModel) throws Exception {
		userInfoModel.setUserInfoList(userInfoDao.getAllUser(userInfoModel));
		userInfoModel.setCount(userInfoDao.getAllUserCount(userInfoModel));
		return userInfoModel;
	}

	@Override
	public int selectUserByRoleId(TRoleInfo roleInfo) throws Exception {
		return userInfoDao.selectUserByRoleId(roleInfo);
	}

	@Override
	public int selectUserByDepartmentId(TDepartmentInfo departmentInfo) throws Exception {
		return userInfoDao.selectUserByDepartmentId(departmentInfo);
	}
	/*
	 * 用户管理部分======End
	 */
	
	/*
	 * 部门管理部分======Start
	 */
	@Override
	public int addDepartment(TDepartmentInfo departmentInfo) throws Exception {
		if(selectDepartmentByName(departmentInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.addDepartment(departmentInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int updateDepartment(TDepartmentInfo departmentInfo) throws Exception {
		if(selectDepartmentByName(departmentInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return userInfoDao.updateDepartment(departmentInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public int selectDepartmentByName(TDepartmentInfo departmentInfo) throws Exception {
		return userInfoDao.selectDepartmentByName(departmentInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int deleteDepartment(TDepartmentInfo departmentInfo) throws Exception {
		if(selectUserByDepartmentId(departmentInfo) > 0) {
			return Constants.ERROR_CODE_92002;
		}
		return userInfoDao.deleteDepartment(departmentInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public List<TDepartmentInfo> getAllDepartment(DepartmentModel departmentModel) throws Exception {
		return userInfoDao.getAllDepartment(departmentModel);
	}
	
	@Override
	public int getAllDepartmentCount(DepartmentModel departmentModel) throws Exception {
		return userInfoDao.getAllDepartmentCount(departmentModel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAllDepartmentTree(List<TDepartmentInfo> departmentInfoList) throws Exception {
		List<Map> listMap = new ArrayList();
		for(TDepartmentInfo departmentInfo : departmentInfoList) {
			List<TDepartmentInfo> tdList = userInfoDao.getAllDepartmentTree(departmentInfo);
			Map tempMap = new HashMap();
			tempMap.put("departmentid", departmentInfo.getDepartmentId());
			tempMap.put("parentid", departmentInfo.getParentId());
			tempMap.put("name", departmentInfo.getName());
			if(null != tdList && tdList.size() > 0) {
				tempMap.put("child", getAllDepartmentTree(tdList));
			}
			listMap.add(tempMap);
		}
		return listMap;
	}
	/*
	 * 部门管理部分======End
	 */

	

	
}

