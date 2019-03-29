package com.jjn.mall.goods.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Config;
import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.common.GoodsConstants;
import com.jjn.mall.goods.dao.pojo.TGoodsAuditingInfo;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.model.GoodsDetailModel;
import com.jjn.mall.goods.model.GoodsInfoModel;
import com.jjn.mall.goods.model.GoodsModel;
import com.jjn.mall.goods.service.IGoodsInfoService;
import com.jjn.mall.user.common.UserConstants;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.SFtpUtil;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/goods")
public class GoodsInfoController {

	Log LOG = LogFactory.getLog(this.getClass());

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private IGoodsInfoService goodsInfoService;

	/**
	 * 新增商品
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/addGoods")
	public JSONObject addGoods(HttpServletRequest request, GoodsInfoModel goodsInfoModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			if (userModel == null) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "页面已失效,请重新登陆");
				return returnJson;
			}
			if (userModel.getType() == 0) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "总部商户不允许操作");
				return returnJson;
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == goodsInfoModel || StringUtils.isEmpty(goodsInfoModel.getName())
					|| StringUtils.isEmpty(goodsInfoModel.getSellingPoint()) || goodsInfoModel.getCategoryId() < 1
					|| StringUtils.isEmpty(goodsInfoModel.getPicJson())
					|| StringUtils.isEmpty(goodsInfoModel.getAttributeJson())
					|| StringUtils.isEmpty(goodsInfoModel.getStandardJson()) || goodsInfoModel.getTempletId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			if (goodsInfoModel.getIsRestrict() == 1 && StringUtils.isEmpty(goodsInfoModel.getDeliveryArea())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			goodsInfoModel.setCreater(userModel.getUserId());
			goodsInfoModel.setLikeNumber(0);
			goodsInfoModel.setShareNumber(0);
			goodsInfoModel.setSales((int) (Math.random() * 40) + 10);
			goodsInfoModel.setMerchantId(userModel.getMerchantId());
			goodsInfoModel.setStatus(GoodsConstants.GOODS_STATUS_WAITING);
			LOG.info("[商城] 商品模块, 新增商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = goodsInfoService.addGoods(goodsInfoModel);
			returnJson.put("RESULTCODE", resultCode);
			redisTemplate.delete("RandGoodsInfoList");
			redisTemplate.delete("AppRandGoodsInfoList");
			return returnJson;

		} catch (Exception e) {
			LOG.error("[商城] 商品模块,新增商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改商品
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/updateGoods")
	public JSONObject updateGoods(HttpServletRequest request, GoodsInfoModel goodsInfoModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			if (userModel == null) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "页面已失效,请重新登陆");
				return returnJson;
			}
			if (userModel.getType() == 0) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "总部商户不允许操作");
				return returnJson;
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == goodsInfoModel || goodsInfoModel.getGoodsId() < 1
					|| StringUtils.isEmpty(goodsInfoModel.getName()) || goodsInfoModel.getCategoryId() < 1
					|| goodsInfoModel.getTempletId() < 1 || StringUtils.isEmpty(goodsInfoModel.getSellingPoint())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			goodsInfoModel.setModifier(userModel.getUserId());
			LOG.info("[商城] 商品模块, 修改商品, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = goodsInfoService.updateGoods(goodsInfoModel);
			LOG.info("[商城] 商品模块, 修改商品信息结果:[" + resultCode + "]");
			redisTemplate.delete("RandGoodsInfoList");
			redisTemplate.delete("AppRandGoodsInfoList");
			redisTemplate.delete("BeanIndexOverFlowGoodsInfoList");
			redisTemplate.delete("BeanIndexGoodsInfoList");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,修改商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改商品状态
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/updateGoodsStatus")
	public JSONObject updateGoodsStatus(HttpServletRequest request, TGoodsInfo goodsInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		try {
			if (userModel == null) {
				returnJson.put("RESULTCODE", Constants.SELECT_FAIL);
				returnJson.put("RESULTMSG", "页面已失效,请重新登陆");
				return returnJson;
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == goodsInfo || goodsInfo.getGoodsId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			if (goodsInfo.getStatus() == GoodsConstants.GOODS_STATUS_REJECT
					&& StringUtils.isEmpty(goodsInfo.getRejectReason())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "请填写驳回原因");
				return returnJson;
			}
			if(true == goodsInfoService.goodsIsInBeanGoods(goodsInfo.getGoodsId())) {
				returnJson.put("RESULTCODE", Constants.REQUEST_EXCEPTION);
				returnJson.put("RESULTMSG", "金吉豆商城中已有该商品，请先删除金吉豆商品");
				return returnJson;
			}
			
			goodsInfo.setModifier(userModel.getUserId());
			LOG.info("[商城] 商品模块, 修改商品状态, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = goodsInfoService.updateGoodsStatus(goodsInfo);
			// 清空redis随机商品
			redisTemplate.delete("RandGoodsInfoList");
			redisTemplate.delete("AppRandGoodsInfoList");
			redisTemplate.delete("BeanIndexOverFlowGoodsInfoList");
			redisTemplate.delete("BeanIndexGoodsInfoList");
			LOG.info("[商城] 商品模块, 修改商品状态结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,修改商品状态捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询所有商品
	 * 
	 * @param request
	 * @param goodsModel
	 * @return
	 */
	@RequestMapping("/getAllGoods")
	public JSONObject getAllGoods(HttpServletRequest request, GoodsModel goodsModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		GoodsModel returnGoodsModel = new GoodsModel();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				goodsModel.setMerchantId(userModel.getMerchantId());
			}
			goodsModel.setStartNum((goodsModel.getPageNo() - 1) * goodsModel.getPageSize());
			goodsModel.setEndNum(goodsModel.getPageSize());
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnGoodsModel = goodsInfoService.getAllGoods(goodsModel);
			if (null == returnGoodsModel || null == returnGoodsModel.getGoodsInfoList()
					|| returnGoodsModel.getGoodsInfoList().size() < 1) {
				returnJson.put("RESULTJSON", JSONArray.toJSON(new GoodsModel()));
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnGoodsModel));
			}
			returnJson.put("IDENTITY", userModel.getType());
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,查找商品信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查看商品详情
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/getGoods")
	public JSONObject getGoodsInfo(HttpServletRequest request, GoodsDetailModel goodsDetailModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == goodsDetailModel || goodsDetailModel.getGoodsId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			goodsDetailModel = goodsInfoService.getGoodsInfo(goodsDetailModel);
			if (null == goodsDetailModel) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(goodsDetailModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,查找商品详情捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查看商品名称
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/getGoodsName")
	public JSONObject getGoodsName(HttpServletRequest request, int goodsId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			String name = goodsInfoService.getGoodsName(goodsId);
			if (StringUtil.isEmpty(name)) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", name);
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,查找商品名称捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 根据商品Id查询审核记录
	 * 
	 * @param request
	 * @param goodsInfo
	 * @return
	 */
	@RequestMapping("/getAllAuditingInfoByGoodsId")
	public JSONObject getAllAuditingInfoByGoodsId(HttpServletRequest request, int goodsId) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (goodsId < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "操作参数有误");
				return returnJson;
			}
			List<TGoodsAuditingInfo> list = goodsInfoService.getAllAuditingInfoByGoodsId(goodsId);
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,查找商品详情捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 上传商品图片
	 * 
	 * @param request
	 * @param activeInfo
	 * @return
	 */
	@RequestMapping("/uploadFile")
	public JSONObject uploadFile(HttpServletRequest request, @RequestParam("pic0") MultipartFile pic0,
			Integer picType) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			String fileName = pic0.getOriginalFilename();
			String fileType = fileName.indexOf(".") >= 0
					? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
					: null;
			if (null == fileType) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92006);
				returnJson.put("RESULTMSG", "文件格式不正确");
				return returnJson;
			}
			if (!"GIF".equals(fileType.toUpperCase()) && !"PNG".equals(fileType.toUpperCase())
					&& !"JPG".equals(fileType.toUpperCase())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92006);
				returnJson.put("RESULTMSG", "文件格式不正确");
				return returnJson;
			}
			if (picType != 1 && picType != 2 && picType != 3) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "参数有误");
				return returnJson;
			}
			String systemp = "";
			String ftppic = "";
			if (picType == 1) {
				systemp = "sys.pictemp.location";
				ftppic = "ftp.pic.location";
			} else if (picType == 3) {
				systemp = "sys.standardpictemp.location";
				ftppic = "ftp.standardpic.location";
			} else if (picType == 2) {
				systemp = "sys.detailpictemp.location";
				ftppic = "ftp.detailpic.location";
			}
			// FTP保存路径
			String localpath = request.getSession().getServletContext().getRealPath("/")
					+ Config.getMallConfig(systemp);
			File localPathCheckFile = new File(localpath);
			if (!localPathCheckFile.exists()) {
				localPathCheckFile.mkdirs();
			}
			// FTP保存的文件名
			String lastFileName = String.valueOf(System.currentTimeMillis()) + StringUtil.randomString(8, true) + "."
					+ fileType;
			pic0.transferTo(new File(localpath + lastFileName));
			String remoteFile = Config.getMallConfig("ftp.default.location") + Config.getMallConfig(ftppic);
			SFtpUtil sftp = SFtpUtil.getConnect();
			BufferedImage image = ImageIO.read(pic0.getInputStream());
			if (image != null) {// 如果image=null 表示上传的不是图片格式
				returnJson.put("IMGSIZE", image.getHeight() + "#" + image.getWidth());
			}
			String uploadResult = sftp.upload(remoteFile, localpath + lastFileName, lastFileName);
			returnJson.put("REQRESULT",
					Config.getMallConfig("ftp.img.path") + Config.getMallConfig(ftppic) + uploadResult);
			returnJson.put("RESULTCODE", Constants.REQUEST_RESULT);

			LOG.warn("新增商品图片, 上传至FTP:[" + remoteFile + "], 结果:[" + uploadResult + "]");
		} catch (Exception e) {
			LOG.error("[商城] 商品模块,新增图片捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
		}
		return returnJson;
	}
}
