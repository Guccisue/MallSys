package com.jjn.mall.order.controll;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.jjn.mall.common.Config;
import com.jjn.mall.common.Constants;
import com.jjn.mall.order.common.OrderConstants;
import com.jjn.mall.order.dao.pojo.TOrderRecord;
import com.jjn.mall.order.model.OrderRecordModel;
import com.jjn.mall.order.service.IOrderRecordService;
import com.jjn.mall.user.common.UserConstants;
import com.jjn.mall.user.model.UserModel;
import com.jjn.mall.utils.AESCoder;
import com.jjn.mall.utils.DateUtils;
import com.jjn.mall.utils.ExcelUtil;
import com.jjn.mall.utils.HttpURLConnectionClient;
import com.jjn.mall.utils.StringUtil;

@RestController
@RequestMapping("/order")
public class OrderControll {
	Log LOG = LogFactory.getLog(this.getClass());
	@Autowired
	private IOrderRecordService orderRecordService;

	/**
	 * 查找订单
	 * 
	 * @param request
	 * @param orderRecordModel
	 * @return
	 */
	@RequestMapping("/getAllOrder")
	public JSONObject getAllOrderRecord(HttpServletRequest request, OrderRecordModel orderRecordModel) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		OrderRecordModel returnOrderRecordModel = new OrderRecordModel();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				orderRecordModel.setMerchantId(userModel.getMerchantId());
			}
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			orderRecordModel.setStartNum((orderRecordModel.getPageNo() - 1) * orderRecordModel.getPageSize());
			orderRecordModel.setEndNum(orderRecordModel.getPageSize());
			returnOrderRecordModel = orderRecordService.getAllOrderRecord(orderRecordModel);
			if (null == returnOrderRecordModel || null == returnOrderRecordModel.getOrderRecordList()
					|| returnOrderRecordModel.getOrderRecordList().size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnOrderRecordModel));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 订单模块,查找订单信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 查找订单
	 * 
	 * @param request
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping("/getOrder")
	public JSONObject getOrderRecord(HttpServletRequest request, TOrderRecord orderRecord) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == orderRecord || orderRecord.getOrderId().equals("")) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				orderRecord.setMerchantId(userModel.getMerchantId());
			}
			List<TOrderRecord> returnOrderList = orderRecordService.getOrderRecord(orderRecord);
			if (null == returnOrderList || returnOrderList.size() < 1) {
				returnJson.put("RESULTJSON", "");
			} else {
				returnJson.put("RESULTJSON", JSONArray.toJSON(returnOrderList.get(0)));
			}
			returnJson.put("RESULTCODE", Constants.SELECT_SUCCESS);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 订单模块,查找订单信息捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 订单发货
	 * 
	 * @param request
	 * @param orderRecord
	 * @return
	 */
	@RequestMapping("/updateDeliver")
	public JSONObject updateOrderRecordDeliver(HttpServletRequest request, TOrderRecord orderRecord) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == orderRecord || "".equals(orderRecord.getOrderId()) || "".equals(orderRecord.getCourier())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				orderRecord.setMerchantId(userModel.getMerchantId());
			}
			orderRecord.setModifier(userModel.getUserId());
			orderRecord.setStatus(OrderConstants.ORDER_STATUS_SENDING);

			LOG.info("[商城] 订单模块,订单确认发货, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			int resultCode = orderRecordService.updateOrderRecordDeliver(orderRecord);
			LOG.info("[商城] 订单模块,订单确认发货结果:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城] 订单模块,订单确认发货捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 退款处理
	 * 
	 * @param request
	 * @param orderRecord
	 * @return
	 */
	@RequestMapping("/updateReturnStatus")
	public JSONObject updateReturnStatus(HttpServletRequest request, TOrderRecord orderRecord) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == orderRecord || "".equals(orderRecord.getOrderId())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				orderRecord.setMerchantId(userModel.getMerchantId());
			}

			LOG.info("[商城] 订单模块,退款处理, user:[" + userModel.getUserId() + "], params:["
					+ StringUtil.getRequestParams(request) + "]");

			// 1:退款退货2:仅退款3:拒绝退款
			if (orderRecord.getAuditStatus() == 1) {
				orderRecord.setRefundStatus(OrderConstants.ORDER_STATUS_RETURNING);
			} else if (orderRecord.getAuditStatus() == 3) {
				orderRecord.setCanRefund(OrderConstants.REFUND_STATUS_AGREE);
				orderRecord.setRefundStatus(OrderConstants.ORDER_STATUS_REJUSEFUNDING);
			} else if (orderRecord.getAuditStatus() == 2) {
				orderRecord.setRefundStatus(OrderConstants.ORDER_STATUS_REFUNDING);
			}
			int resultCode = orderRecordService.updateRefundRtatus(orderRecord);
			LOG.info("[商城]订单模块,退款处理:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城]订单模块,退款处理捕获异常", e);
			returnJson.put("REQRESULT", Constants.REQUEST_EXCEPTION);
			returnJson.put("RESULTMSG", "Server is busy, please try later");
			return returnJson;
		}
	}

	/**
	 * 调取退款接口
	 * 
	 * @param request
	 * @param refundRecord
	 * @return
	 */
	@RequestMapping("/doRefund")
	public JSONObject doRefund(HttpServletRequest request, HttpServletResponse response, TOrderRecord orderRecord) {
		// 返回Json
		JSONObject returnJson = new JSONObject();
		// 1.修改状态 2.调取退款接口
		try {
			returnJson.put("REQRESULT", Constants.REQUEST_RESULT);
			if (null == orderRecord || "".equals(orderRecord.getOrderId())
					|| "".equals(orderRecord.getTransactionId())) {
				returnJson.put("RESULTCODE", Constants.ERROR_CODE_92000);
				returnJson.put("RESULTMSG", URLEncoder.encode("操作参数有误", "UTF-8"));
				return returnJson;
			}
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			if (userModel.getType() == UserConstants.USER_TYPE_MERCHANT) {
				orderRecord.setMerchantId(userModel.getMerchantId());
			}

			TOrderRecord data = orderRecordService.getRefundIdByOrderId(orderRecord);
			List<TOrderRecord> returnOrderList = orderRecordService.getOrderRecord(orderRecord);
			// 调取退款接口
			String merchantId = Config.getConfig("mall.properties", "paysys.trade.merchantid");
			// 交易号
			JSONObject reqJson = new JSONObject();
			reqJson.put("MERCHANTID", merchantId);
			reqJson.put("TRANSACTIONID", orderRecord.getTransactionId());
			reqJson.put("ORDERID", orderRecord.getOrderId());
			reqJson.put("REFUNDID", data.getRefundId());
			reqJson.put("REFUNDFEE", orderRecord.getRealFee());
			reqJson.put("REASON", "正常退款");
			reqJson.put("APPLYUSER", userModel.getUserId());
			reqJson.put("REQIP", Config.getConfig("mall.properties", "req.ip"));
			reqJson.put("TIMESTAMP", System.currentTimeMillis() + "");
			reqJson.put("PLUGVERSION", Config.getConfig("mall.properties", "plug.version"));
			reqJson.put("NOTIFYURL", Config.getConfig("mall.properties", "refund.notify.url"));
			// 加密
			// 改进秘钥获取
			String encryptValue = AESCoder.encrypt(reqJson.toString(), Constants.SECRET_KEY);
			String summary = StringUtil.MD5(encryptValue);
			encryptValue = URLEncoder.encode(encryptValue, "UTF-8");
			// 最终请求json
			JSONObject json = new JSONObject();
			// 生成加密key
			json.put("MERCHANTID", merchantId);
			json.put("ENCRYPT", encryptValue);
			json.put("SUMMARY", summary);

			String param = "REQUEST=" + json.toString();
			HttpURLConnectionClient conn = new HttpURLConnectionClient();
			String res = conn.send("POST", Config.getConfig("mall.properties", "paysys.refund.service"), param,
					"UTF-8");
			JSONObject jsobj = JSONObject.parseObject(res);
			int resultCode = 1;
			if (jsobj.getString("RESULT").equals("0")) {
				orderRecord.setRefundStatus(OrderConstants.ORDER_STATUS_REFUNDED);
				resultCode = orderRecordService.updateRefundRtatus(orderRecord);
				if (returnOrderList.get(0).getStatus() == 1) {// 未发货的订单退款成功后改成已取消
					orderRecord.setStatus(OrderConstants.ORDER_STATUS_CANCEL);
					resultCode = orderRecordService.updateRefundRtatusAfterSuccess(orderRecord);
				}
				if(returnOrderList.get(0).getGoldNumber() > 0) {
					// 退还金吉豆
					boolean returnBean = returnLuckyBean(returnOrderList.get(0).getGoldNumber(),
							returnOrderList.get(0).getUserIdApp(), returnOrderList.get(0).getOrderId(),
							StringUtil.fenToYuan(returnOrderList.get(0).getTotalPrice()), StringUtil.fenToYuan(returnOrderList.get(0).getRealFee()));
					if (returnBean) {
						LOG.info("[商城]订单模块,订单号:" + returnOrderList.get(0).getOrderId() + "退款,"
								+ returnOrderList.get(0).getGoldNumber() + "金吉豆退还成功");
					} else {
						LOG.info("[商城]订单模块,订单号:" + returnOrderList.get(0).getOrderId() + "退款,"
								+ returnOrderList.get(0).getGoldNumber() + "金吉豆退还失败");
					}
				}
				
			}
			LOG.info("[商城]订单模块,退款订单状态处理:[" + resultCode + "]");
			returnJson.put("RESULTCODE", resultCode);
			return returnJson;
		} catch (Exception e) {
			LOG.error("[商城]订单模块,调取退款接口捕获异常", e);
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
		String[] excelHeader = { "下单时间", "商品名称", "订单金额", "数量", "收货人", "手机号", "下单地址", "订单状态" };
		String[] ds_titles = { "createTime", "goodsName", "realFee", "nums", "receiver", "phone", "address", "status" };
		int[] ds_format = { 1, 1, 1, 1, 1, 1, 1, 1 };
		OrderRecordModel returnOrderRecordModel = new OrderRecordModel();
		try {
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("USER_MODEL");
			orderRecordModel.setMerchantId(userModel.getMerchantId());
			returnOrderRecordModel = orderRecordService.getAllOrderRecord(orderRecordModel);
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			if (null != returnOrderRecordModel && null != returnOrderRecordModel.getOrderRecordList()
					&& returnOrderRecordModel.getOrderRecordList().size() > 0) {
				for (TOrderRecord orderRecord : returnOrderRecordModel.getOrderRecordList()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("createTime", DateUtils.formatDate(orderRecord.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					map.put("goodsName", orderRecord.getGoodsName());
					map.put("realFee", (double) orderRecord.getRealFee() / 100 + "元");
					map.put("nums", orderRecord.getNumber());
					map.put("receiver", orderRecord.getReceiver());
					map.put("phone", orderRecord.getPhone());
					map.put("address", orderRecord.getReceiveAddress());
					switch (orderRecord.getStatus()) {
					case 0:
						map.put("status", "待支付");
						break;
					case 1:
						map.put("status", "已支付");
						break;
					case 2:
						map.put("status", "已发货");
						break;
					case 5:
						map.put("status", "已完成");
						break;
					case 6:
						map.put("status", "已取消");
						break;
					default:
						break;
					}
					data.add(map);
				}
			}
			ExcelUtil.export("订单表", "订单信息", excelHeader, ds_titles, ds_format, null, data, request, response);
		} catch (Exception e) {
			LOG.error("[商城] 订单模块,订单Excel导出异常");
		}
	}

	public boolean returnLuckyBean(int goldNumber, String userIdApp, String orderId, String totalPrice, String realPrice) {
		HttpURLConnection conn = null;
		HttpURLConnectionClient client = new HttpURLConnectionClient();
		try {
			conn = client.openConnection("POST", Config.getConfig("mall.properties", "lucky.bean.reduce.url"));
			String param = "number=" + goldNumber + "&userIdApp=" + userIdApp + "&type=" + 5 + "&typeId=" + orderId
					+ "&description=" + "订单金额:￥" + totalPrice + ",退款金额:￥" + realPrice;
			String reduceBeanStr = client.send(conn, param, "utf-8");
			JSONObject reduceBeanResult = JSONObject.parseObject(reduceBeanStr);
			if (Integer.parseInt(reduceBeanResult.get("RESULTCODE").toString()) != 0) {
				LOG.warn("用户:[" + userIdApp + "], 订单号:[" + orderId + "], 金吉豆:[" + goldNumber + "], 退还失败, 错误码:["
						+ reduceBeanResult.get("RESULTCODE") + "]");
				return false;
			} else {
				LOG.warn("用户:[" + userIdApp + "], 订单号:[" + orderId + "], 金吉豆:[" + goldNumber + "], 退还成功");
				return true;
			}
		} catch (IOException e) {
			LOG.warn("用户:[" + userIdApp + "], 订单号:[" + orderId + "], 金吉豆:[" + goldNumber + "], 退还抛出异常", e);
			return false;
		}

	}
}
