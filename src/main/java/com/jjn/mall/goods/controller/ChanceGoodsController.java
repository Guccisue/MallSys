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
import com.jjn.mall.common.Config;
import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.pojo.TChanceGoods;
import com.jjn.mall.goods.model.ChanceGoodsInfoModel;
import com.jjn.mall.goods.model.ChanceGoodsListModel;
import com.jjn.mall.goods.model.ChanceGoodsModel;
import com.jjn.mall.goods.service.IChanceGoodsService;
import com.jjn.mall.goods.service.IGoodsInfoService;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/chance")
public class ChanceGoodsController {

	Log LOG = LogFactory.getLog(this.getClass());
	@Autowired
	private IChanceGoodsService chanceGoodsService;

	@Autowired
	private IGoodsInfoService goodsInfoService;

	/**
	 * 新增抽奖商品
	 * 
	 * @param request
	 * @param chanceGoods
	 * @return
	 */
	@RequestMapping("/addChanceGoods")
	public JSONObject addChanceGoods(HttpServletRequest request, String listStr) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (StringUtil.isEmpty(listStr)) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}

			List<TChanceGoods> chanceGoodsList = JSONArray.parseArray(listStr, TChanceGoods.class);
			if (null == chanceGoodsList || chanceGoodsList.size() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			// 校验抽奖商品名称是否重复
			int nums = chanceGoodsService.checkIsExist(chanceGoodsList.get(0).getGoodsId());
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92001);
				returnJson.put("RESULTMSG", "该抽奖商品已存在");
				return returnJson;
			}
			// 检验是否是秒杀商品
			int parentId = goodsInfoService.getCategoryIdByGoodsId(chanceGoodsList.get(0).getGoodsId());
			if (parentId == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92015);
				returnJson.put("RESULTMSG", "秒杀商品无法设置为抽奖商品");
				return returnJson;
			}
			for (TChanceGoods chanceGoods : chanceGoodsList) {
				chanceGoods.setCreater(userModel.getUserId());
			}
			LOG.info("[商城] 抽奖商品模块, 新增抽奖商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = chanceGoodsService.addChanceGoods(chanceGoodsList);
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块,新增抽奖商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改抽奖商品
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/updateChanceGoods")
	public JSONObject updateChanceGoods(HttpServletRequest request, String listStr) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (StringUtil.isEmpty(listStr)) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			List<TChanceGoods> chanceGoodsList = JSONArray.parseArray(listStr, TChanceGoods.class);
			for (TChanceGoods chanceGoods : chanceGoodsList) {
				chanceGoods.setModifier(userModel.getUserId());
			}
			LOG.info("[商城] 抽奖商品模块, 修改抽奖商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = chanceGoodsService.updateChanceGoods(chanceGoodsList);
			LOG.info("[商城] 抽奖商品模块, 修改抽奖商品信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块,修改抽奖商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除抽奖商品
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/deleteChanceGoods")
	public JSONObject deleteChanceGoods(HttpServletRequest request, int id) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (id < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}

			LOG.info("[商城] 抽奖商品模块, 删除抽奖商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = chanceGoodsService.deleteChanceGoods(id, userModel.getUserId());
			LOG.info("[商城] 抽奖商品模块, 删除抽奖商品结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块 删除抽奖商品捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询所有抽奖商品
	 * 
	 * @param request
	 * @param chanceGoodsModel
	 * @return
	 */
	@RequestMapping("/getAllChanceGoods")
	public JSONObject getAllChanceGoods(HttpServletRequest request, ChanceGoodsListModel chanceGoodsListModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			chanceGoodsListModel
					.setStartNum((chanceGoodsListModel.getPageNo() - 1) * chanceGoodsListModel.getPageSize());
			chanceGoodsListModel.setEndNum(chanceGoodsListModel.getPageSize());
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			chanceGoodsListModel = chanceGoodsService.getAllChanceGoods(chanceGoodsListModel);
			if (null == chanceGoodsListModel || null == chanceGoodsListModel.getChanceGoodsList()
					|| chanceGoodsListModel.getChanceGoodsList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(chanceGoodsListModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块,查找抽奖商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查看抽奖商品详情
	 * 
	 * @param request
	 * @param chanceGoods
	 * @return
	 */
	@RequestMapping("/getChanceGoods")
	public JSONObject getChanceGoods(HttpServletRequest request, int goodsId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			ChanceGoodsModel chanceGoodsModel = chanceGoodsService.getChanceGoods(goodsId);
			if (null == chanceGoodsModel) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(chanceGoodsModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块,查找抽奖商品详情捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据商品ID自动获取商品信息
	 * 
	 * @param request
	 * @param chanceGoods
	 * @return
	 */
	@RequestMapping("/getGoodsDetail")
	public JSONObject getGoodsDetail(HttpServletRequest request, String goodsId) {
		
		// 返回Json	
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (!StringUtils.isNumeric(goodsId) || "".equals(goodsId)) {
				returnJson.put("RESULTJSON", "");
				returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
				return returnJson;
			}
			Integer goodsIdInt = Integer.valueOf(goodsId);
			if (goodsIdInt < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			ChanceGoodsInfoModel chanceGoodsInfoModel = chanceGoodsService.getGoodsDetail(goodsIdInt);
			if (null == chanceGoodsInfoModel) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(chanceGoodsInfoModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 抽奖商品模块,根据商品ID自动获取商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
}
