package com.jjn.mall.user.controll;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Constants;
import com.jjn.mall.user.common.UserConstants;
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
import com.jjn.mall.utils.MD5;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/user")
public class UserControll {
	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private IUserService userService;

	/*
	 * 权限管理部分======Start
	 */
	/**
	 * 校验权限名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/authNameCheck")
	public JSONObject selectAuthByName(HttpServletRequest request, TAuthInfo authInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == authInfo || StringUtil.isEmpty(authInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
			} else {
				returnJson.put("RESULTCODE", userService.selectAuthByName(authInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 权限模块,校验权限名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 新增权限信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/addAuth")
	public JSONObject addAuthInfo(HttpServletRequest request, TAuthInfo authInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == authInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			authInfo.setCreater(userModel.getUserId());
			authInfo.setStatus(UserConstants.AUTH_STATUS_NORMAL);

			LOG.info("[商城] 权限模块,新增权限, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.addAuth(authInfo);
			LOG.info("[商城] 权限模块,新增权限信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 权限模块,新增权限信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 更新权限信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/updateAuth")
	public JSONObject updateAuth(HttpServletRequest request, TAuthInfo authInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == authInfo || authInfo.getAuthId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			authInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 权限模块,修改权限, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.updateAuth(authInfo);
			LOG.info("[商城] 权限模块,修改权限信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 权限模块,修改权限信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除权限信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/deleteAuth")
	public JSONObject deleteAuth(HttpServletRequest request, TAuthInfo authInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == authInfo || authInfo.getAuthId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			authInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 权限模块,删除权限, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.deleteAuth(authInfo);
			LOG.info("[商城] 权限模块,删除权限信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 权限模块,删除权限信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查找权限信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/getAllAuth")
	public JSONObject getAllAuth(HttpServletRequest request, TAuthInfo authInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			authInfo.setStatus(UserConstants.AUTH_STATUS_NORMAL);
			authInfo.setParentId(0);
			List<TAuthInfo> taiList = new ArrayList<>();
			taiList.add(0, authInfo);
			List authModelList = userService.getAllAuth(taiList);
			if (null == authModelList || authModelList.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(authModelList));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 权限模块,查找权限信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	/*
	 * 权限管理部分======End
	 */

	/*
	 * 角色权限绑定管理部分======Start
	 */
	/**
	 * 角色权限绑定操作
	 * 
	 * @param roleId
	 * @param authIds
	 * @return
	 */
	@RequestMapping("/doRoleAuthBind")
	public JSONObject doRoleAuthBind(HttpServletRequest request, Integer roleId, String authIds) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		int resultCode = Constants.BIND_SUCCESS;
		try {
			String[] str = authIds.split(",");
			@SuppressWarnings("unchecked")
			List<TAuthInfo> plist = userService.getParentIdById(str);
			if (plist.size() > 0 && plist != null) {
				for (TAuthInfo tAuthInfo : plist) {
					authIds += "," + tAuthInfo.getParentId();
				}
			}
			LOG.info("[商城] 角色模块,绑定角色权限, 角色编号:[" + roleId + "], 权限编号:[" + authIds + "]");
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleId || roleId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode(URLEncoder.encode("操作参数有误", "UTF-8"), "UTF-8"));
				return returnJson;
			}
			if (StringUtil.isEmpty(authIds)) {
				// 解绑所有关系
				TRoleAuth tra = new TRoleAuth();
				tra.setRoleId(roleId);
				resultCode = userService.deleteRoleAuth(tra);
				LOG.info("[商城] 角色模块,绑定角色权限, 没有传入权限编号, 解绑角色编号:[" + roleId + "]下的所有权限, 解绑数量:[" + resultCode + "]");
				returnJson.put("RESULTCODE", Constants.BIND_SUCCESS);
			} else {
				// 解绑所有关系
				TRoleAuth tra = new TRoleAuth();
				tra.setRoleId(roleId);
				LOG.info("[商城] 角色模块,绑定角色权限, 先解绑角色编号:[" + roleId + "]下的所有权限, 解绑数量:[" + userService.deleteRoleAuth(tra)
						+ "]");

				// 按照入参进行绑定
				if (authIds.indexOf(",") < 0) {
					// 单个绑定
					TRoleAuth tra1 = new TRoleAuth();
					tra1.setRoleId(roleId);
					tra1.setAuthId(Integer.parseInt(authIds));
					resultCode = userService.doRoleAuthBind(tra1);
					returnJson.put("RESULTCODE", resultCode);
				} else {
					// 多个绑定
					String[] authIdArr = authIds.split(",");
					int bindSuccessNumber = 0;
					for (String authIdStr : authIdArr) {
						TRoleAuth tra1 = new TRoleAuth();
						tra1.setRoleId(roleId);
						tra1.setAuthId(Integer.parseInt(authIdStr));
						if (userService.doRoleAuthBind(tra1) > 0) {
							LOG.info("[商城] 角色模块,绑定角色权限, 角色编号:[" + roleId + "], 权限编号:[" + authIdStr + "], 绑定成功");
							bindSuccessNumber++;
						}
					}
					if (bindSuccessNumber == authIdArr.length) {
						returnJson.put("RESULTCODE", Constants.BIND_SUCCESS);
					} else {
						returnJson.put("RESULTCODE", Constants.ERROR_CODE_92003);
						returnJson.put("RESULTMSG", URLEncoder.encode("部分绑定成功", "UTF-8"));
					}

				}
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,角色权限绑定操作捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查看角色已绑定的权限
	 * 
	 * @param roleInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getAuthByRoleId")
	public JSONObject getAuthByRoleId(HttpServletRequest request, TRoleInfo roleInfo) {
		JSONObject returnJson = new JSONObject();

		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleInfo || roleInfo.getRoleId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			LOG.info("[商城] 角色模块,查询角色绑定的权限, 角色编号:[" + roleInfo.getRoleId() + "]");
			TRoleAuth tra = new TRoleAuth();
			tra.setRoleId(roleInfo.getRoleId());
			List<TRoleAuth> traList = userService.getAllBindAuthId(tra);
			if (null == traList || traList.size() < 1) {
				returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
				returnJson.put("RESULTJSON", "");
			} else {
				// Json标记已绑定的权限
				List<TAuthInfo> bindedAuthInfoList = new ArrayList<TAuthInfo>();
				for (TRoleAuth traTemp : traList) {
					TAuthInfo authInfoTemp = new TAuthInfo();
					authInfoTemp.setAuthId(traTemp.getAuthId());
					bindedAuthInfoList.add(authInfoTemp);
				}
				TAuthInfo authInfo = new TAuthInfo();
				authInfo.setStatus(UserConstants.AUTH_STATUS_NORMAL);
				authInfo.setParentId(0);
				List<TAuthInfo> taiList = new ArrayList<>();
				taiList.add(0, authInfo);
				List authModelList = userService.getAllAuthAndMarkBinded(taiList, bindedAuthInfoList);
				if (null == authModelList || authModelList.size() < 1) {
					returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
					returnJson.put("RESULTJSON", "");
				} else {
					returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
					returnJson.put("RESULTJSON", JSONArray.toJSON(authModelList));
				}
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,查看角色已绑定的权限捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/*
	 * 角色权限绑定管理部分======End
	 */

	/*
	 * 角色管理部分======Start
	 */
	/**
	 * 校验角色名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/roleNameCheck")
	public JSONObject selectRoleByName(HttpServletRequest request, TRoleInfo roleInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleInfo || StringUtil.isEmpty(roleInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
			} else {
				returnJson.put("RESULTCODE", userService.selectRoleByName(roleInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,校验角色名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 新增角色信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/addRole")
	public JSONObject addRoleInfo(HttpServletRequest request, TRoleInfo roleInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			roleInfo.setCreater(userModel.getUserId());
			roleInfo.setStatus(UserConstants.ROLE_STATUS_NORMAL);

			LOG.info("[商城] 角色模块,新增角色, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.addRole(roleInfo);
			LOG.info("[商城] 角色模块,新增角色信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,新增角色信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 更新角色信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/updateRole")
	public JSONObject updateRole(HttpServletRequest request, TRoleInfo roleInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleInfo || roleInfo.getRoleId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			roleInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 角色模块,修改角色, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.updateRole(roleInfo);
			LOG.info("[商城] 角色模块,修改角色信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,修改角色信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除角色信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/deleteRole")
	public JSONObject deleteRole(HttpServletRequest request, TRoleInfo roleInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == roleInfo || roleInfo.getRoleId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			roleInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 角色模块,删除角色, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.deleteRole(roleInfo);
			LOG.info("[商城] 角色模块,删除角色信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,删除角色信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据id查询角色信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/getRoleById")
	public JSONObject getRoleById(HttpServletRequest request, TRoleInfo tRoleInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			TRoleInfo data = userService.getRoleById(tRoleInfo);
			if (null == data) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(data));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,根据id查询角色信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查找角色信息
	 * 
	 * @param authInfo
	 * @return
	 */
	@RequestMapping("/getAllRole")
	public JSONObject getAllRole(HttpServletRequest request, RoleModel roleModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			roleModel.setStartNum((roleModel.getPageNo() - 1) * roleModel.getPageSize());
			roleModel.setEndNum(roleModel.getPageSize());
			roleModel.setStatus(UserConstants.AUTH_STATUS_NORMAL);
			roleModel = userService.getAllRole(roleModel);
			if (null == roleModel.getRoleInfoList() || roleModel.getRoleInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(roleModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 角色模块,查找角色信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	/*
	 * 角色管理部分======End
	 */

	/*
	 * 用户管理部分======Start
	 */
	/**
	 * 新增用户
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/addUser")
	public JSONObject addUser(HttpServletRequest request, TUserInfo userInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			if (userInfo.getType() == 1) {
				userInfo.setMerchantId(userInfo.getDepartmentId());
				userInfo.setDepartmentId(0);
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			userInfo.setCreater(userModel.getUserId());
			int result = userService.selectUserByName(userInfo);
			if (result != 0) {
				LOG.info("[商城] 用户模块,新增用户信息结果:[" + result + "]");
				returnJson.put("RESULTCODE", result);
				return returnJson;
			}
			if (userInfo.getUserId() != 0) {

				int resultCode = userService.updateUser(userInfo);
				LOG.info("[商城] 用户模块,修改用户信息结果:[" + resultCode + "]");
				returnJson.put("RESULTCODE", resultCode);
				returnJson.put("RESULTMSG", URLEncoder.encode("登录账号不能重复", "UTF-8"));
				return returnJson;
			}
			userInfo.setPassword(MD5.MD5Encode("000000"));
			userInfo.setStatus(UserConstants.USER_STATUS_NORMAL);

			LOG.info("[商城] 用户模块,新增用户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.addUser(userInfo);
			LOG.info("[商城] 用户模块,新增用户信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,新增用户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/userAccountCheck")
	public JSONObject selectUserByAccount(HttpServletRequest request, String account) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (StringUtil.isEmpty(account)) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
			} else {
				TUserInfo userInfo = new TUserInfo();
				userInfo.setAccount(account);
				returnJson.put("RESULTCODE", userService.selectUserByName(userInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,校验用户名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/updateUser")
	public JSONObject updateUser(HttpServletRequest request, TUserInfo userInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo || userInfo.getUserId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			userInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 用户模块,修改用户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.updateUser(userInfo);
			LOG.info("[商城] 用户模块,修改用户信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,修改用户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("deleteUser")
	public JSONObject deleteUser(HttpServletRequest request, TUserInfo userInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo || userInfo.getUserId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			userInfo.setModifier(userModel.getUserId());
			userInfo.setStatus(UserConstants.USER_STATUS_WRITTENOFF);

			LOG.info("[商城] 用户模块,删除用户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.deleteUser(userInfo);
			LOG.info("[商城] 用户模块,删除用户信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,删除用户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/updateUserPassword")
	public JSONObject updateUserPassword(HttpServletRequest request, TUserInfo userInfo, String newPassword) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo || StringUtil.isEmpty(userInfo.getPassword())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			userInfo.setModifier(userModel.getUserId());
			LOG.info("[商城] 用户模块,更新用户:[" + userModel.getAccount() + "], ID:[" + userModel.getUserId() + "]的密码");
			userInfo.setUserId(userModel.getUserId());
			userInfo.setPassword(MD5.MD5Encode(userInfo.getPassword()));
			int resultCode = userService.updateUserPassword(userInfo);
			LOG.info("[商城] 用户模块,密码修改结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			if (resultCode == Constants.ERROR_CODE_92004) {
				returnJson.put("RESULTMSG", URLEncoder.encode("用户名或原密码不正确", "UTF-8"));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,修改用户密码捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 用户登陆
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("loginUser")
	public JSONObject loginUser(HttpServletRequest request, TUserInfo userInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo || StringUtil.isEmpty(userInfo.getPassword())
					|| StringUtil.isEmpty(userInfo.getAccount())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "用户名或密码不正确");
				return returnJson;
			}
			userInfo.setPassword(MD5.MD5Encode(userInfo.getPassword()));
			UserModel userModel = userService.loginUser(userInfo);
			if (null == userModel) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92004);
				returnJson.put("RESULTMSG", "用户名或密码不正确");
				return returnJson;
			}
			HttpSession session = request.getSession();
			session.setAttribute("USER_MODEL", userModel);

			@SuppressWarnings("unchecked")
			List<TAuthInfo> tmpList = userModel.getAuthInfoList();
			JSONArray menuJsonArr = new JSONArray();
			for (TAuthInfo menu : tmpList) {
				if (menu.getIsHide() == 0) {
					JSONObject menuJson = new JSONObject();
					menuJson.put("id", menu.getAuthId());
					menuJson.put("pId", menu.getParentId());
					menuJson.put("name", menu.getName());
					menuJson.put("path", parseMenuUrl(menu));
					menuJsonArr.add(menuJson);
				}
			}

			request.getSession().setAttribute("menu", menuJsonArr.toString());
			request.getSession().setAttribute("account", userModel.getAccount());
			returnJson.put("RESULTCODE", Constants.LOGIN_SUCCESS);
			returnJson.put("RESULTJSON", JSONArray.toJSON(userModel));
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,用户登陆捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	private String parseMenuUrl(TAuthInfo sysMenu) {
		String path = sysMenu.getUrl();
		if (StringUtils.isNotBlank(path)) {
			boolean flag = path.contains("?");
			if (flag) {
				// 菜单URL已包含参数链接符号
			} else {
				path += "?";
			}
			return path += ("&projectMenuId=" + sysMenu.getAuthId());
		} else {
			return "";
		}
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/getAllUser")
	public JSONObject getAllUser(HttpServletRequest request, UserInfoModel userInfoModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			userInfoModel.setStartNum((userInfoModel.getPageNo() - 1) * userInfoModel.getPageSize());
			userInfoModel.setEndNum(userInfoModel.getPageSize());
			userInfoModel = userService.getAllUser(userInfoModel);
			if (null == userInfoModel.getUserInfoList() || userInfoModel.getUserInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(userInfoModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,查询用户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据id查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/getUserById")
	public JSONObject getUserById(HttpServletRequest request, TUserInfo userInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			TUserInfo data = userService.getUserById(userInfo);
			if (null == data) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(data));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,查询用户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据账户查询密码是否正确
	 * 
	 * @param request
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("/checkPassword")
	public JSONObject checkPassword(HttpServletRequest request, TUserInfo userInfo) {
		JSONObject returnJson = new JSONObject();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == userInfo || StringUtil.isEmpty(userInfo.getPassword())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "初始密码不正确");
				return returnJson;
			}
			userInfo.setAccount(userModel.getAccount());
			userInfo.setPassword(MD5.MD5Encode(userInfo.getPassword()));
			UserModel user = userService.loginUser(userInfo);
			if (null == user) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "初始密码不正确");
				return returnJson;
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 用户模块,检查密码捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}

	}

	/*
	 * 用户管理部分======End
	 */

	/*
	 * 部门管理部分======Start
	 */
	/**
	 * 检查部门名称是否已存在
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/departmentNameCheck")
	public JSONObject selectDepartmentByName(HttpServletRequest request, TDepartmentInfo departmentInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == departmentInfo || StringUtil.isEmpty(departmentInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
			} else {
				returnJson.put("RESULTCODE", userService.selectDepartmentByName(departmentInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,校验部门名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 新增部门信息
	 * 
	 * @param roleInfo
	 * @return
	 */
	@RequestMapping("/addDepartment")
	public JSONObject addDepartment(HttpServletRequest request, TDepartmentInfo departmentInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == departmentInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			departmentInfo.setCreater(userModel.getUserId());
			departmentInfo.setStatus(UserConstants.DEPARTMENT_STATUS_NORMAL);

			LOG.info("[商城] 部门模块,新增部门, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.addDepartment(departmentInfo);
			LOG.info("[商城] 部门模块,新增部门信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,新增部门信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 更新部门信息
	 * 
	 * @param roleInfo
	 * @return
	 */
	@RequestMapping("/updateDepartment")
	public JSONObject updateDepartment(HttpServletRequest request, TDepartmentInfo departmentInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == departmentInfo || departmentInfo.getDepartmentId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			departmentInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 部门模块,修改部门, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.updateDepartment(departmentInfo);
			LOG.info("[商城] 部门模块,修改部门信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,修改部门信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除部门信息
	 * 
	 * @param roleInfo
	 * @return
	 */
	@RequestMapping("deleteDepartment")
	public JSONObject deleteDepartment(HttpServletRequest request, TDepartmentInfo departmentInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == departmentInfo || departmentInfo.getDepartmentId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			departmentInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 部门模块,删除部门, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = userService.deleteDepartment(departmentInfo);
			LOG.info("[商城] 部门模块,删除部门信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,删除部门信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 得到所有部门信息
	 * 
	 * @param departmentInfo
	 * @return
	 */
	@RequestMapping("/getAllDepartment")
	public JSONObject getAllDepartment(HttpServletRequest request, DepartmentModel departmentModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		DepartmentModel returnDepartmentModel = new DepartmentModel();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnDepartmentModel.setDepartmentInfoList(userService.getAllDepartment(departmentModel));
			returnDepartmentModel.setCount(userService.getAllDepartmentCount(departmentModel));
			if (null == returnDepartmentModel.getDepartmentInfoList()
					|| returnDepartmentModel.getDepartmentInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnDepartmentModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,查找部门信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 获得树形部门列表
	 * 
	 * @param departmentInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAllDepartmentTree")
	public JSONObject getAllDepartmentTree(HttpServletRequest request, TDepartmentInfo departmentInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			departmentInfo.setStatus(UserConstants.DEPARTMENT_STATUS_NORMAL);
			departmentInfo.setParentId(0);
			List<TDepartmentInfo> tdiList = new ArrayList<>();
			tdiList.add(0, departmentInfo);
			List departmentModelList = userService.getAllDepartmentTree(tdiList);
			if (null == departmentModelList || departmentModelList.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(departmentModelList));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 部门模块,查找部门信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/*
	 * 部门管理部分======End
	 */
}
