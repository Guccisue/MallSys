package com.jjn.mall.freight.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Constants;
import com.jjn.mall.freight.dao.pojo.TFreightTemplet;
import com.jjn.mall.freight.model.TreightModel;
import com.jjn.mall.freight.service.IFreightService;
import com.jjn.mall.goods.model.ResultJsonModel;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.RequestJsonUtils;
import com.jjn.mall.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/freight")
public class FreightController {
	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private IFreightService iFreightService;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	/**
	 *
	 * [request, tFreightTemplet]
	 * 
	 * @Description:新增运费模板
	 * @Author:chenjunyan
	 * @Date:下午 3:36 2019/3/28 0028
	 */
	@RequestMapping("/addFreightTemplet")
	public JSONObject addFreightTemplet(HttpServletRequest request, @RequestBody TFreightTemplet tFreightTemplet) {

		// 返回Json
		JSONObject returnJson = new JSONObject();
		ResultJsonModel result;
		try {
			HttpSession session = request.getSession();
//            UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			UserModel userModel = new UserModel();
			userModel.setAccount("nike");
			userModel.setUserId(1);
			tFreightTemplet.setCreater(userModel.getUserId());
			tFreightTemplet.setModifier(userModel.getUserId());
			// 此处要添加一个对于入参的判断，TFreightTemplet是否有参数为空

			// 检查模板名称是否重复
			result = iFreightService.checkFreightTempletIsRepeat(tFreightTemplet.getName());
			if (result != null) {
				returnJson.put("REQRESULT", result.getResultCode());
				returnJson.put("RESULTMSG", result.getResultMsg());
				return returnJson;
			}
			LOG.info("[商城] 商品运费模块, 新增商品运费模板, user:[" + userModel.getUserId() + "], merchantid:["
					+ tFreightTemplet.getMerchantid() + "]");
			// 入库运费模板
			int resultCode = iFreightService.addFreightTemplet(tFreightTemplet);
			if (resultCode == 0) {
				// 为模板下的areaList添加templetid
				tFreightTemplet.setTempletIdForAreas();
				iFreightService.addFreightArea(tFreightTemplet.getAreaList());
			}
			LOG.info("[商城] 商品运费模块, 新增商品运费模板息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品运费模块, 新增商品运费模板息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 *
	 * [request, tFreightTemplet]
	 * 
	 * @Description:修改运费模板
	 * @Author:chenjunyan
	 * @Date:下午 3:36 2019/3/28 0028
	 */
	@RequestMapping("/updateFreightTemplet")
	public JSONObject updateFreightTemplet(HttpServletRequest request, @RequestBody TFreightTemplet tFreightTemplet) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		ResultJsonModel result;
		try {
			HttpSession session = request.getSession();
			UserModel userModel = new UserModel();
			userModel.setAccount("nike");
			userModel.setUserId(1);
			tFreightTemplet.setCreater(userModel.getUserId());
			tFreightTemplet.setModifier(userModel.getUserId());
			// 此处要添加一个对于入参的判断，TFreightTemplet是否有参数为空

			// 获取修改前的运费模板
			TFreightTemplet orlTemplet = iFreightService.getTempletSingle(tFreightTemplet.getId());
			// 检查模板名称是否重复
			if (!tFreightTemplet.getName().equals(orlTemplet.getName())) {
				result = iFreightService.checkFreightTempletIsRepeat(tFreightTemplet.getName());
				if (result != null) {
					returnJson.put("REQRESULT", result.getResultCode());
					returnJson.put("RESULTMSG", result.getResultMsg());
					return returnJson;
				}
			}
			// 获取新增 修改 删除的 TFreightArea
			tFreightTemplet.getUpdateAreas(orlTemplet.getAreaList());
			// 修改目的地运费方式
			int resultCode = iFreightService.updateFreightArea(tFreightTemplet);
			LOG.info("[商城] 商品运费模块, 新增商品运费模板息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品运费模块, 修改商品运费模板息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 *
	 * [request, tFreightTemplet]
	 * 
	 * @Description:删除单个运费模板
	 * @Author:chenjunyan
	 * @Date:下午 3:38 2019/3/28 0028
	 */
	@RequestMapping("/deleteFreightTemplet")
	public JSONObject deleteFreightTemplet(HttpServletRequest request, Integer templetId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = new UserModel();
		userModel.setAccount("nike");
		userModel.setUserId(1);
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (templetId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			LOG.info("[商城] 商品运费模块, 删除商品运费, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");
			int resultCode = iFreightService.deleteFreightTemplet(templetId);
			LOG.info("[商城] 商品运费模块, 删除商品运费模板结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品运费模块, 删除商品运费模板捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 *
	 * [request, treightModel]
	 * 
	 * @Description:查询该商户所有的运费模板
	 * @Author:chenjunyan
	 * @Date:下午 4:47 2019/3/28 0028
	 */
	@RequestMapping("/getAllFreightTempletInfo")
	public JSONObject getAllFreightTempletInfo(HttpServletRequest request, TreightModel treightModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			treightModel.setStartNum((treightModel.getPageNo() - 1) * treightModel.getPageSize());
			treightModel.setEndNum(treightModel.getPageSize());
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			treightModel = iFreightService.getAllFreightTempletInfo(treightModel);
			if (null == treightModel.gettFreightTemplets() || treightModel.gettFreightTemplets().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(treightModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 金豆商城首页商品模块,查找金豆商城首页商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 *
	 * [request, templetId]
	 * 
	 * @Description:模板详情
	 * @Author:chenjunyan
	 * @Date:下午 4:47 2019/3/28 0028
	 */
	@RequestMapping("/getFreightTempletInfo")
	public JSONObject getFreightTempletInfo(HttpServletRequest request, int templetId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			if (templetId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			TFreightTemplet tFreightTemplet = iFreightService.getTempletSingle(templetId);
			if (null == tFreightTemplet) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(tFreightTemplet));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 金豆商城首页商品模块,查找单个金豆商城首页商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
}