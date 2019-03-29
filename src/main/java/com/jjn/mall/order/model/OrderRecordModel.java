package com.jjn.mall.order.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.jjn.mall.order.dao.pojo.TOrderRecord;

public class OrderRecordModel {
	private int startNum;
	private int endNum;
	private int count;
	private int status;
	private int merchantId;
	private String userIdApp;
	private int payType;
	private String startTime;
	private String endTime;
	private String phone;
	private String orderId;
	private String goodsName;
	private int refundStatus;
	private List<TOrderRecord> orderRecordList;
	private int pageNo;
	private int pageSize;
	private String receiver;
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date queryTime;
	private int type;
	private double deductPoint;
	
	
	
	@Override
	public String toString() {
		return "OrderRecordModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", status="
				+ status + ", merchantId=" + merchantId + ", userIdApp=" + userIdApp + ", payType=" + payType
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", phone=" + phone + ", orderId=" + orderId
				+ ", goodsName=" + goodsName + ", orderRecordList=" + orderRecordList + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", receiver=" + receiver + ", queryTime=" + queryTime + "]";
	}


	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getDeductPoint() {
		return deductPoint;
	}
	public void setDeductPoint(double deductPoint) {
		this.deductPoint = deductPoint;
	}
	public int getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Date getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getUserIdApp() {
		return userIdApp;
	}
	public void setUserIdApp(String userIdApp) {
		this.userIdApp = userIdApp;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public List<TOrderRecord> getOrderRecordList() {
		return orderRecordList;
	}
	public void setOrderRecordList(List<TOrderRecord> orderRecordList) {
		this.orderRecordList = orderRecordList;
	}
}
