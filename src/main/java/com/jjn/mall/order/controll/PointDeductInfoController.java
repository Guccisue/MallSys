package com.jjn.mall.order.controll;

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
import com.jjn.mall.common.Constants;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.order.dao.pojo.TPointDeductInfo;
import com.jjn.mall.order.model.PointDeductInfoModel;
import com.jjn.mall.order.service.IPointDeductInfoService;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/pointdeduct")
public class PointDeductInfoController {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private IPointDeductInfoService pointDeductInfoService;

	/**
	 * 分页查询扣点
	 * 
	 * @param pointDeductInfoModel
	 * @return
	 */
	@RequestMapping("/getAllPointDeductInfo")
	public JSONObject getAllPointDeductInfo(PointDeductInfoModel pointDeductInfoModel) {
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			pointDeductInfoModel
					.setStartNum((pointDeductInfoModel.getPageNo() - 1) * pointDeductInfoModel.getPageSize());
			pointDeductInfoModel.setEndNum(pointDeductInfoModel.getPageSize());
			pointDeductInfoModel = pointDeductInfoService.getAllPointDeductInfo(pointDeductInfoModel);
			if (null == pointDeductInfoModel.getPointDeductInfoList()
					|| pointDeductInfoModel.getPointDeductInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(pointDeductInfoModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 查找扣点列表捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询扣点详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPointDeductInfo")
	public JSONObject getPointDeductInfo(HttpServletRequest request, int id) {
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (id < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "入参有误");
				return returnJson;
			}
			TPointDeductInfo data = pointDeductInfoService.getPointDeductInfo(id);
			if (null == data) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(data));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 查找扣点详情捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 新增扣点
	 * 
	 * @param pointDeductInfo
	 * @return
	 */
	@RequestMapping("/addPointDeductInfo")
	public JSONObject addPointDeductInfo(HttpServletRequest request, TPointDeductInfo pointDeductInfo) {
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == pointDeductInfo || pointDeductInfo.getType() < 1
				|| pointDeductInfo.getDeductPoint() < 0 || null == pointDeductInfo.getBeginTime() 
				|| null == pointDeductInfo.getEndTime() || pointDeductInfo.getTarget() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "入参有误");
				return returnJson;
			}
			if(pointDeductInfo.getBeginTime().getTime() > pointDeductInfo.getEndTime().getTime()) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92012);
				returnJson.put("RESULTMSG", "生效时间不能大于结束时间");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			pointDeductInfo.setCreater(userModel.getUserId());

			int nums = pointDeductInfoService.checkTimeRepeat(pointDeductInfo);
			if(nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92011);
				returnJson.put("RESULTMSG", "时间有重合");
				return returnJson;
			}
			log.info("[扣点] 新增扣点, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");
			int addResult = pointDeductInfoService.addPointDeductInfo(pointDeductInfo);
			if (addResult == Constants.ADD_SUCCESS) {
				returnJson.put("RESULTCODE", Constants.ADD_SUCCESS);
			} else {
				returnJson.put("RESULTCODE", Constants.ADD_FAIL);
				returnJson.put("RESULTMSG", "新增失败");
			}
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 新增扣点捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 删除扣点
	 * 
	 * @param pointDeductInfo
	 * @return
	 */
	@RequestMapping("/deletePointDeductInfo")
	public JSONObject deletePointDeductInfo(HttpServletRequest request, TPointDeductInfo pointDeductInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (pointDeductInfo.getId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "入参有误");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			pointDeductInfo.setModifier(1);
			log.info("[扣点] 扣点删除, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");
			
			int updateResult = pointDeductInfoService.deletePointDeductInfo(pointDeductInfo);
			if (updateResult == Constants.DELETE_SUCCESS) {
				returnJson.put("RESULTCODE", Constants.DELETE_SUCCESS);
			} else {
				returnJson.put("RESULTCODE", Constants.DELETE_FAIL);
				returnJson.put("RESULTMSG", "删除失败");
			}
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 扣点删除捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 修改扣点
	 * 
	 * @param pointDeductInfo
	 * @return
	 */
	@RequestMapping("/updatePointDeductInfo")
	public JSONObject updatePointDeductInfo(HttpServletRequest request, TPointDeductInfo pointDeductInfo) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == pointDeductInfo || pointDeductInfo.getId() < 1) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", "入参有误");
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			pointDeductInfo.setModifier(userModel.getUserId());
			if(pointDeductInfo.getBeginTime().getTime() > pointDeductInfo.getEndTime().getTime()) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92012);
				returnJson.put("RESULTMSG", "生效时间不能大于结束时间");
				return returnJson;
			}
			int nums = pointDeductInfoService.checkTimeRepeat(pointDeductInfo);
			if(nums > 0) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92011);
				returnJson.put("RESULTMSG", "时间有重合");
				return returnJson;
			}
			log.info("[扣点] 扣点修改, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");
			int updateResult = pointDeductInfoService.updatePointDeductInfo(pointDeductInfo);
			if (updateResult == Constants.UPDATE_SUCCESS) {
				returnJson.put("RESULTCODE", Constants.UPDATE_SUCCESS);
			} else {
				returnJson.put("RESULTCODE", Constants.UPDATE_FAIL);
				returnJson.put("RESULTMSG", "修改失败");
			}
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 扣点修改捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
	
	
	
	/**
	 * 根据商户ID查询类目
	 * 
	 * @param pointDeductInfoModel
	 * @return
	 */
	@RequestMapping("/getAllCategoryIdByMerchantId")
	public JSONObject getAllCategoryIdByMerchantId(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			List<CategoryModel> list = pointDeductInfoService.getAllCategoryIdByMerchantId(userModel.getMerchantId());
			if (null == list || list.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(list));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			log.error("[扣点] 根据商户ID查询类目捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}
}
