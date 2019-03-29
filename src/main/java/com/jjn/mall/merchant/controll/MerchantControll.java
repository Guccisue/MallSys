package com.jjn.mall.merchant.controll;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Config;
import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.service.ITempletInfoService;
import com.jjn.mall.merchant.common.MerchantConstants;
import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;
import com.jjn.mall.merchant.dao.pojo.TMerchantInfo;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.merchant.model.MerchantModel;
import com.jjn.mall.merchant.service.IMerchantService;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/merchant")
public class MerchantControll {
	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private IMerchantService merchantService;

	@Autowired
	private ITempletInfoService templetInfoService;
	/*
	 * 商户管理部分======Start
	 */
	/**
	 * 新增商户
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/addMerchant")
	public JSONObject addMerchant(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			merchantInfo.setCreater(userModel.getUserId());
			merchantInfo.setStatus(MerchantConstants.MERCHANT_STATUS_NORMAL);

			LOG.info("[商城] 商户模块, 新增商户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.addMerchant(merchantInfo);
			LOG.info("[商城] 商户模块, 新增商户信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,新增商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 校验商户名称是否存在
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/merchantNameCheck")
	public JSONObject selectMerchantByName(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo || StringUtil.isEmpty(merchantInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
			} else {
				returnJson.put("RESULTCODE", merchantService.selectMerchantByName(merchantInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,校验商户名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 校验商户社会统一信用代码是否存在
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/merchantUCCCheck")
	public JSONObject selectMerchantByUCC(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo || StringUtil.isEmpty(merchantInfo.getUnifiedCreditCode())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
			} else {
				returnJson.put("RESULTCODE", merchantService.selectMerchantByUCC(merchantInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块, 校验商户社会统一信用代码是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 校验QQ号码是否存在
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/QQNumberCheck")
	public JSONObject selectMerchantByQQNumber(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo || StringUtil.isEmpty(merchantInfo.getQqNumber())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
			} else {
				returnJson.put("RESULTCODE", merchantService.selectMerchantByQQNumber(merchantInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块, 校验商户QQ号码是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 更新商户信息
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/updateMerchant")
	public JSONObject updateMerchant(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo || merchantInfo.getMerchantId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			merchantInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 商户模块, 修改商户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.updateMerchant(merchantInfo);
			LOG.info("[商城] 商户模块, 修改商户信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块, 修改商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询商户信息
	 * 
	 * @param request
	 * @param merchantModel
	 * @return
	 */
	@RequestMapping("/getAllMerchant")
	public JSONObject getAllMerchant(HttpServletRequest request, MerchantModel merchantModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		merchantModel.setStartNum((merchantModel.getPageNo() - 1) * merchantModel.getPageSize());
		merchantModel.setEndNum(merchantModel.getPageSize());
		MerchantModel returnMerchantModel = new MerchantModel();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			// 判断是商户，则只查询自己的商户信息
			if (userModel != null && userModel.getType() == 1) {
				merchantModel.setMerchantId(userModel.getMerchantId());
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnMerchantModel = merchantService.getAllMerchant(merchantModel);
			if (null == returnMerchantModel || null == returnMerchantModel.getMerchantInfoList()
					|| returnMerchantModel.getMerchantInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnMerchantModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,查找商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询账户表里不存在的商户信息
	 * 
	 * @param request
	 * @param merchantModel
	 * @return
	 */
	@RequestMapping("/queryAllMerchant")
	public JSONObject queryAllMerchant(HttpServletRequest request) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			List<TMerchantInfo> list = merchantService.queryAllMerchant();
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,查找商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查找商户信息
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/getMerchant")
	public JSONObject getMerchant(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			List<TMerchantInfo> returnMerchantList = merchantService.getMerchant(merchantInfo);
			if (null == returnMerchantList || returnMerchantList.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnMerchantList.get(0)));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,查找商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除商户
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("deleteMerchant")
	public JSONObject deleteMerchant(HttpServletRequest request, TMerchantInfo merchantInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == merchantInfo || merchantInfo.getMerchantId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			TGoodsInfo goodsInfo = new TGoodsInfo();
			goodsInfo.setMerchantId(merchantInfo.getMerchantId());
			int nums = merchantService.checkGoodsMerchantBind(goodsInfo);
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92007);
				returnJson.put("RESULTMSG", "被删除商户还有在线销售的商品");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			merchantInfo.setModifier(userModel.getUserId());
			merchantInfo.setStatus(MerchantConstants.MERCHANT_STATUS_WRITTENOFF);

			LOG.info("[商城] 商户模块, 删除商户, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.deleteMerchant(merchantInfo);
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商户模块,删除商户信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	/*
	 * 商户管理部分======End
	 */

	/*
	 * 类目管理部分======Start
	 */
	/**
	 * 新增类目
	 * 
	 * @param request
	 * @param categoryInfo
	 * @return
	 */
	@RequestMapping("/addCategory")
	public JSONObject addCategory(HttpServletRequest request, TCategoryInfo categoryInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == categoryInfo) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			int nums = merchantService.selectCategoryByName(categoryInfo);
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92001);
				returnJson.put("RESULTMSG", "新增类目重复");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			categoryInfo.setCreater(userModel.getUserId());
			categoryInfo.setStatus(MerchantConstants.CATEGORY_STATUS_NORMAL);

			LOG.info("[商城] 类目模块, 新增类目, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.addCategory(categoryInfo);
			LOG.info("[商城] 类目模块,新增类目信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,新增类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 校验类目名称是否存在
	 * 
	 * @param request
	 * @param categoryInfo
	 * @return
	 */
	@RequestMapping("/categoryNameCheck")
	public JSONObject selectCategoryByName(HttpServletRequest request, TCategoryInfo categoryInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == categoryInfo || StringUtil.isEmpty(categoryInfo.getName())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
			} else {
				returnJson.put("RESULTCODE", merchantService.selectCategoryByName(categoryInfo));
			}
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,校验类目名称是否存在捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改类目信息
	 * 
	 * @param request
	 * @param categoryInfo
	 * @return
	 */
	@RequestMapping("/updateCategory")
	public JSONObject updateCategory(HttpServletRequest request, TCategoryInfo categoryInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == categoryInfo || categoryInfo.getCategoryId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			if (categoryInfo.getCategoryId()  == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))
					|| categoryInfo.getCategoryId()  == Integer.parseInt(Config.getMallConfig("seckill.goods.second.category"))) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92009);
				returnJson.put("RESULTMSG", "固定类目,不得修改");
				return returnJson;
			}
			int nums = merchantService.selectCategoryByName(categoryInfo);
			if (nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92001);
				returnJson.put("RESULTMSG", "新增类目重复");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			categoryInfo.setModifier(userModel.getUserId());

			LOG.info("[商城] 类目模块, 修改类目, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.updateCategory(categoryInfo);
			LOG.info("[商城] 类目模块,修改类目信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,修改类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询类目
	 * 
	 * @param request
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping("/getAllCategory")
	public JSONObject getAllCategory(HttpServletRequest request, CategoryModel categoryModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		CategoryModel returnCategoryModel = new CategoryModel();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			categoryModel.setStartNum((categoryModel.getPageNo() - 1) * categoryModel.getPageSize());
			if (categoryModel.getParentId() == 0) {
				categoryModel.setLevel(0);
			} else {
				categoryModel.setLevel(1);
			}
			returnCategoryModel = merchantService.getAllCategory(categoryModel);
			if (null == returnCategoryModel || null == returnCategoryModel.getCategoryInfoList()
					|| returnCategoryModel.getCategoryInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnCategoryModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,查找类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	
	/**
	 * 查询所有二级类目
	 * 
	 * @param request
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping("/getAllSecondCategory")
	public JSONObject getAllSecondCategory(HttpServletRequest request) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			
			List<TCategoryInfo> list = merchantService.getAllSecondCategory();
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,查找所有二级类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询类目
	 * 
	 * @param request
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping("/queryAllCategory")
	public JSONObject queryAllCategory(HttpServletRequest request, CategoryModel categoryModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		CategoryModel returnCategoryModel = new CategoryModel();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			categoryModel.setStartNum((categoryModel.getPageNo() - 1) * categoryModel.getPageSize());
			returnCategoryModel = merchantService.getAllCategory(categoryModel);
			if (null == returnCategoryModel || null == returnCategoryModel.getCategoryInfoList()
					|| returnCategoryModel.getCategoryInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnCategoryModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,查找类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询类目
	 * 
	 * @param request
	 * @param categoryModel
	 * @return
	 */
	@RequestMapping("/getCategoryByParentId")
	public JSONObject getCategoryByParentId(HttpServletRequest request, int parentId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			List<TCategoryInfo> list = merchantService.getCategoryByPid(parentId);
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,查找类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据categoryId查询类目
	 * 
	 * @param request
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/getCategoryByCategoryId")
	public JSONObject getCategoryByCategoryId(HttpServletRequest request, int categoryId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			TCategoryInfo tCategoryInfo = merchantService.getCategoryByCategoryId(categoryId);
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			returnJson.put("RESULTJSON", JSONArray.toJSON(tCategoryInfo));
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,根据类目ID查找类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询类目树形
	 * 
	 * @param request
	 * @param categoryInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getAllCategoryTree")
	public JSONObject getAllCategoryTree(HttpServletRequest request, TCategoryInfo categoryInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			categoryInfo.setStatus(MerchantConstants.CATEGORY_STATUS_NORMAL);
			categoryInfo.setParentId(0);
			List<TCategoryInfo> tciList = new ArrayList<>();
			tciList.add(0, categoryInfo);
			List categoryModelList = merchantService.getAllCategoryTree(tciList);
			if (null == categoryModelList || categoryModelList.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(categoryModelList));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,查找树形类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除类目信息
	 * 
	 * @param request
	 * @param categoryInfo
	 * @return
	 */
	@RequestMapping("deleteCategory")
	public JSONObject deleteCategory(HttpServletRequest request, TCategoryInfo categoryInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == categoryInfo || categoryInfo.getCategoryId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			if (categoryInfo.getCategoryId()  == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))
					|| categoryInfo.getCategoryId()  == Integer.parseInt(Config.getMallConfig("seckill.goods.second.category"))) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92009);
				returnJson.put("RESULTMSG", "固定类目,不得删除");
				return returnJson;
			}
			//判断是否有模板绑定该类目
			List<Integer> list = templetInfoService.checkTemplateByCategoryId(categoryInfo.getCategoryId());
			if(null != list && list.size() > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92014);
				returnJson.put("RESULTMSG", "模板ID为:"+list.toString()+"绑定了该类目，请先删除模板");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			categoryInfo.setModifier(userModel.getUserId());
			categoryInfo.setStatus(MerchantConstants.CATEGORY_STATUS_NORMAL);

			LOG.info("[商城] 类目模块, 删除类目, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = merchantService.deleteCategory(categoryInfo);
			LOG.info("[商城] 类目模块,删除类目信息结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块,删除类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	
	@RequestMapping("getSeckillCategoryId")
	public JSONObject getSeckillCategoryId(HttpServletRequest request) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnJson.put("RESULTJSON", JSONArray.toJSON(Config.getMallConfig("seckill.goods.first.category")));
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 类目模块查询秒杀类目信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	/*
	 * 类目管理部分======End
	 */

}
