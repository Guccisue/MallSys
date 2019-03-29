package com.jjn.mall.report.dao.pojo;

import java.util.Date;
import java.util.List;

import com.jjn.mall.order.dao.pojo.TOrderRecord;

public class TReportFormInfo {

	private int id;
	private int merchantId;
	private String merchantName;
	private String reportTime;
	private int orderNum;
	private int orderMoney;
	private int hasPayMoney;
	private int hasRefundMoney;
	private int canBalanceBefore;
	private int canBalanceAfter;
	private int status;
	private Date updateTime;
	private List<TOrderRecord> orderList;
	
	
	
	
	public int getCanBalanceBefore() {
		return canBalanceBefore;
	}
	public void setCanBalanceBefore(int canBalanceBefore) {
		this.canBalanceBefore = canBalanceBefore;
	}
	public int getCanBalanceAfter() {
		return canBalanceAfter;
	}
	public void setCanBalanceAfter(int canBalanceAfter) {
		this.canBalanceAfter = canBalanceAfter;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<TOrderRecord> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<TOrderRecord> orderList) {
		this.orderList = orderList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}
	public int getHasPayMoney() {
		return hasPayMoney;
	}
	public void setHasPayMoney(int hasPayMoney) {
		this.hasPayMoney = hasPayMoney;
	}
	public int getHasRefundMoney() {
		return hasRefundMoney;
	}
	public void setHasRefundMoney(int hasRefundMoney) {
		this.hasRefundMoney = hasRefundMoney;
	}
	@Override
	public String toString() {
		return "TReportFormInfo [id=" + id + ", merchantId=" + merchantId + ", merchantName=" + merchantName
				+ ", reportTime=" + reportTime + ", orderNum=" + orderNum + ", orderMoney=" + orderMoney
				+ ", hasPayMoney=" + hasPayMoney + ", hasRefundMoney=" + hasRefundMoney + ", status=" + status
				+ ", updateTime=" + updateTime + ", orderList=" + orderList + "]";
	}
	
	
}
