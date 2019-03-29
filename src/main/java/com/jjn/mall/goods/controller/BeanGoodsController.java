package com.jjn.mall.goods.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.pojo.TBeanGoods;
import com.jjn.mall.goods.model.BeanGoodsModel;
import com.jjn.mall.goods.model.GoodsModel;
import com.jjn.mall.goods.model.ResultJsonModel;
import com.jjn.mall.goods.service.IBeanGoodsInfoService;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/beanGoods")
public class BeanGoodsController {

	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private IBeanGoodsInfoService beanGoodsInfoService;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 新增金豆商城首页商品
	 * 
	 * @param request
	 * @param chanceGoods
	 * @return
	 */
	@RequestMapping("/addBeanGoodsInfo")
	public JSONObject addBeanGoodsInfo(HttpServletRequest request, int goodsId, int type, int sequence) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			// 校验金豆商城首页商品是否重复
			ResultJsonModel rjm = beanGoodsInfoService.checkBeanGoodsIsRepeatAdd(goodsId, type, sequence);
			if (rjm != null) {
				returnJson.put("REQRESULT", rjm.getResultCode());
				returnJson.put("RESULTMSG", rjm.getResultMsg());
				return returnJson;
			}
			LOG.info("[商城] 金豆商城首页商品模块, 新增金豆商城首页商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = beanGoodsInfoService.addBeanGoodsInfo(goodsId, type, userModel.getUserId(), sequence);
			redisTemplate.delete("BeanIndexGoodsInfoList");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 金豆商城首页商品模块,新增金豆商城首页商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改金豆商城首页商品
	 * 
	 * @param request
	 * @param chanceGoods
	 * @return
	 */
	@RequestMapping("/updateBeanGoodsInfo")
	public JSONObject updateBeanGoodsInfo(HttpServletRequest request, int goodsId, int type, int orlType,
			int sequence) {
//		goodsId = Integer.valueOf(request.getParameter("goodsId"));
//		type = Integer.valueOf(request.getParameter("type"));
//		sequence = Integer.valueOf(request.getParameter("sequence"));
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			// 校验金豆商城首页商品是否存在
			ResultJsonModel rjm = beanGoodsInfoService.checkBeanGoodsIsRepeatUpdate(goodsId, type, sequence, orlType);
			if (rjm != null) {
				returnJson.put("REQRESULT", rjm.getResultCode());
				returnJson.put("RESULTMSG", rjm.getResultMsg());
				return returnJson;
			}
			if (sequence < 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			LOG.info("[商城] 金豆商城首页商品模块, 修改金豆商城首页商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = beanGoodsInfoService.updateBeanGoodsInfo(goodsId, type, userModel.getUserId(), sequence);
			redisTemplate.delete("BeanIndexGoodsInfoList");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 金豆商城首页商品模块,修改金豆商城首页商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除金豆商城首页商品
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/deleteBeanGoodsInfo")
	public JSONObject deleteBeanGoodsInfo(HttpServletRequest request, Integer goodsId, Integer type) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}

			LOG.info("[商城] 金豆商城首页商品模块, 删除金豆商城首页商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = beanGoodsInfoService.deleteBeanGoodsInfo(goodsId, userModel.getUserId(), type);
			LOG.info("[商城] 金豆商城首页商品模块, 删除金豆商城首页商品结果:[" + resultCode + "]");
			redisTemplate.delete("BeanIndexGoodsInfoList");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 金豆商城首页商品模块 删除金豆商城首页商品捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询所有金豆商城首页商品
	 * 
	 * @param request
	 * @param chanceGoodsModel
	 * @return
	 */
	@RequestMapping("/getAllBeanGoodsInfo")
	public JSONObject getAllBeanGoodsInfo(HttpServletRequest request, BeanGoodsModel beanGoodsModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			beanGoodsModel.setStartNum((beanGoodsModel.getPageNo() - 1) * beanGoodsModel.getPageSize());
			beanGoodsModel.setEndNum(beanGoodsModel.getPageSize());
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			beanGoodsModel = beanGoodsInfoService.getAllBeanGoodsInfo(beanGoodsModel);
			if (null == beanGoodsModel || null == beanGoodsModel.getGoodsInfoList()
					|| beanGoodsModel.getGoodsInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(beanGoodsModel));
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
	 * 查询单个金豆商城首页商品
	 * 
	 * @param request
	 * @param chanceGoodsModel
	 * @return
	 */
	@RequestMapping("/getBeanGoodsInfo")
	public JSONObject getBeanGoodsInfo(HttpServletRequest request, int goodsId, int type) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			TBeanGoods beanGood = beanGoodsInfoService.getBeanGoodsInfo(goodsId, type);

			if (null == beanGood) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(beanGood));
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
