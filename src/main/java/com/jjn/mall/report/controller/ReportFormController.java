package com.jjn.mall.report.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjn.mall.common.Constants;
import com.jjn.mall.order.dao.pojo.TOrderRecord;
import com.jjn.mall.order.model.OrderRecordModel;
import com.jjn.mall.order.service.IOrderRecordService;
import com.jjn.mall.report.model.ReportFormInfoModel;
import com.jjn.mall.report.service.IReportFormService;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.DateUtils;
import com.jjn.mall.utils.ExcelUtil;

@RestController
@RequestMapping("/report")
public class ReportFormController {
	Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private IReportFormService reportFormService;

	@Autowired
	private IOrderRecordService orderRecordService;

	/**
	 * 分页查询所有报表信息
	 * 
	 * @param request
	 * @param reportFormInfoModel
	 * @return
	 */
	@RequestMapping("/getAllReportFormInfo")
	public JSONObject getAllReportFormInfo(HttpServletRequest request, ReportFormInfoModel reportFormInfoModel) {
		JSONObject returnJson = new JSONObject();
		reportFormInfoModel.setStartNum((reportFormInfoModel.getPageNo() - 1) * reportFormInfoModel.getPageSize());
		reportFormInfoModel.setEndNum(reportFormInfoModel.getPageSize());
		HttpSession session = request.getSession();
		UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
		reportFormInfoModel.setMerchantId(userModel.getMerchantId());
		ReportFormInfoModel returnReportFormModel = new ReportFormInfoModel();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnReportFormModel = reportFormService.getSumRecordById(reportFormInfoModel);
			if (null == returnReportFormModel || null == returnReportFormModel.getReportFormInfoList()
					|| returnReportFormModel.getReportFormInfoList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnReportFormModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 报表模块,查找报表信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查询订单明细
	 * 
	 * @param request
	 * @param reportFormInfoModel
	 * @return
	 */
	@RequestMapping("/getReportOrderDetail")
	public JSONObject getReportOrderDetail(HttpServletRequest request, OrderRecordModel orderRecordModel) {
		JSONObject returnJson = new JSONObject();
		orderRecordModel.setStartNum((orderRecordModel.getPageNo() - 1) * orderRecordModel.getPageSize());
		orderRecordModel.setEndNum(orderRecordModel.getPageSize());
		OrderRecordModel returnOrderRecordModel = new OrderRecordModel();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			returnOrderRecordModel = orderRecordService.getAllHaveFinished(orderRecordModel);
			if (null == returnOrderRecordModel || null == returnOrderRecordModel.getOrderRecordList()
					|| returnOrderRecordModel.getOrderRecordList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnOrderRecordModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 报表模块,查找订单明细信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * <p>
	 * 导出excel。
	 * </p>
	 */
	@RequestMapping("/export")
	public void exprotRecruitInfoList(HttpServletRequest request, HttpServletResponse response,
			OrderRecordModel orderRecordModel) {
		String[] excelHeader = { "订单号", "订单完成时间", "商品名称", "规格", "结算模式", "结算比例", "订单总金额", "现金支付金额", "优惠券抵扣金额",
				"售后退款总额","可结算金额(未扣除佣金)", "可结算金额(已扣除佣金)" };
		String[] ds_titles = { "orderId", "receiveTime", "goodsName", "standardName", "type", "deductPoint", "totalPrice",
				"realFee", "couponFee", "hasRefundMoney", "canBalanceBefore", "canBalanceAfter" };
		int[] ds_format = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		OrderRecordModel returnOrderRecordModel = new OrderRecordModel();
		try {
			if (orderRecordModel.getQueryTime() == null) {
				orderRecordModel.setQueryTime(new Date());
			}
			returnOrderRecordModel = orderRecordService.getAllHaveFinished(orderRecordModel);
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			if (null != returnOrderRecordModel && null != returnOrderRecordModel.getOrderRecordList()
					&& returnOrderRecordModel.getOrderRecordList().size() > 0) {
				for (TOrderRecord orderRecord : returnOrderRecordModel.getOrderRecordList()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", orderRecord.getOrderId());
					map.put("receiveTime", DateUtils.formatDate(orderRecord.getReceiveTime(), "yyyy-MM-dd HH:mm:ss"));
					map.put("goodsName", orderRecord.getGoodsName());
					map.put("type",orderRecord.getType() == 1 ? "按比例":(orderRecord.getType()== 2 ? "按金额":"未设置结算") );
					map.put("standardName", orderRecord.getStandardName());
					map.put("deductPoint", orderRecord.getDeductPoint() + (orderRecord.getType()== 1 ? "%":"元"));
					map.put("totalPrice", (double) orderRecord.getTotalPrice() / 100 + "元");
					map.put("realFee", (double) orderRecord.getRealFee() / 100 + "元");
					map.put("couponFee", (double) orderRecord.getCouponFee() / 100 + "元");
					map.put("hasRefundMoney", (double) orderRecord.getHasRefundMoney() / 100 + "元");
					map.put("canBalanceBefore", (double) orderRecord.getCanBalanceBefore() / 100 + "元");
					map.put("canBalanceAfter", (double) orderRecord.getCanBalanceAfter() / 100 + "元");
					data.add(map);
				}
			}
			ExcelUtil.export("对账清单表", "对账清单信息", excelHeader, ds_titles, ds_format, null, data, request, response);
		} catch (Exception e) {
			LOG.error("[商城] 报表模块,对账清单Excel导出异常");
		}
	}
}
