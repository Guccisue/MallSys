package com.jjn.mall.goods.controller;

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
import com.jjn.mall.goods.dao.pojo.TTempletInfo;
import com.jjn.mall.goods.model.TempletDetailModel;
import com.jjn.mall.goods.model.TempletInfoModel;
import com.jjn.mall.goods.service.IGoodsInfoService;
import com.jjn.mall.goods.service.ITempletInfoService;
import com.jjn.mall.user.common.UserConstants;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/templetInfo")
public class TempletInfoController {

	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private ITempletInfoService templetInfoService;

	@Autowired
	private IGoodsInfoService goodsInfoService;

	/**
	 * 新增模板
	 * 
	 * @param request
	 * @param templetInfo
	 * @return
	 */
	@RequestMapping("/addTempletInfo")
	public JSONObject addTempletInfo(HttpServletRequest request, TTempletInfo templetInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);

			if (null == templetInfo || templetInfo.getCategoryId() < 1 || StringUtils.isEmpty(templetInfo.getName())
					|| StringUtils.isEmpty(templetInfo.getAttributeArray())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			templetInfo.setMerchantId(userModel.getMerchantId());
			// 校验模板名称是否重复
			int nums = templetInfoService.checkNameIsRepeat(templetInfo);
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92001);
				returnJson.put("RESULTMSG", "该模板名称已存在");
				return returnJson;
			}

			templetInfo.setCreater(userModel.getUserId());
			LOG.info("[商城] 模板模块, 新增模板, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = templetInfoService.addTempletInfo(templetInfo);
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 模板模块,新增模板信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改模板
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/updateTempletInfo")
	public JSONObject updateTempletInfo(HttpServletRequest request, TTempletInfo templetInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == templetInfo || templetInfo.getId() < 1 || StringUtils.isEmpty(templetInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			TempletDetailModel templetDetailModel = new TempletDetailModel();
			templetDetailModel.setId(templetInfo.getId());
			templetDetailModel = templetInfoService.getTempletInfo(templetDetailModel);
			if (templetDetailModel.getMerchantId() == 0 && userModel.getMerchantId() > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92010);
				returnJson.put("RESULTMSG", "固定模板，不得修改");
				return returnJson;
			}
			int nums = templetInfoService.checkNameIsRepeat(templetInfo);
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92001);
				returnJson.put("RESULTMSG", "该模板名称已存在");
				return returnJson;
			}
			templetInfo.setModifier(userModel.getUserId());
			templetInfo.setCreater(userModel.getUserId());
			LOG.info("[商城] 模板模块, 修改模板, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = templetInfoService.updateTempletInfo(templetInfo);
			LOG.info("[商城] 模板模块, 修改模板信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 模板模块,修改模板信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除模板
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/deleteTempletInfo")
	public JSONObject deleteTempletInfo(HttpServletRequest request, TTempletInfo templetInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == templetInfo || templetInfo.getId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			TempletDetailModel templetDetailModel = new TempletDetailModel();
			templetDetailModel.setId(templetInfo.getId());
			templetDetailModel = templetInfoService.getTempletInfo(templetDetailModel);
			if (templetDetailModel.getMerchantId() == 0 && userModel.getMerchantId() > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92010);
				returnJson.put("RESULTMSG", "固定模板，不得删除");
				return returnJson;
			}
			// 校验是否有商品绑定了该模板
			List<Integer> list = goodsInfoService.checkGoodsIdByTemplateId(templetInfo.getId());
			if (null != list && list.size() > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92014);
				returnJson.put("RESULTMSG", "商品ID为:" + list.toString() + "绑定了该模板，请先删除商品");
				return returnJson;
			}
			LOG.info("[商城] 模板模块, 删除模板, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = templetInfoService.deleteTempletInfo(templetInfo);
			LOG.info("[商城] 模板模块, 删除模板结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 模板模块 删除模板捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询所有模板
	 * 
	 * @param request
	 * @param TempletInfoModel
	 * @return
	 */
	@RequestMapping("/getAllTempletInfo")
	public JSONObject getAllTempletInfo(HttpServletRequest request, TempletInfoModel templetInfoModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				templetInfoModel.setMerchantId(userModel.getMerchantId());
			}
			templetInfoModel.setStartNum((templetInfoModel.getPageNo() - 1) * templetInfoModel.getPageSize());
			templetInfoModel.setEndNum(templetInfoModel.getPageSize());
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			templetInfoModel = templetInfoService.getAllTempletInfo(templetInfoModel);
			if (null == templetInfoModel || null == templetInfoModel.getTempletInfoList()
					|| templetInfoModel.getTempletInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(templetInfoModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 模板模块,查找模板信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询所有模板
	 * 
	 * @param request
	 * @param TempletInfoModel
	 * @return
	 */
	@RequestMapping("/getTempletInfoByCategoryId")
	public JSONObject getTempletInfoByCategoryId(HttpServletRequest request, int categoryId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			List<TTempletInfo> list = templetInfoService.getTempletInfoByCategoryId(categoryId,
					userModel.getMerchantId());
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 模板模块,查找模板信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查看模板详情
	 * 
	 * @param request
	 * @param TempletInfo
	 * @return
	 */
	@RequestMapping("/getTempletInfo")
	public JSONObject getTempletInfo(HttpServletRequest request, TempletDetailModel templetDetailModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == templetDetailModel || templetDetailModel.getId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			templetDetailModel = templetInfoService.getTempletInfo(templetDetailModel);
			if (null == templetDetailModel) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(templetDetailModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 模板模块,查找模板详情捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
}
