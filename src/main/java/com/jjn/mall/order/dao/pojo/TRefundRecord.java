package com.jjn.mall.order.dao.pojo;

import java.util.Date;

/**
 * 退款记录表实体类
 * @author 翟仁元
 *
 */
public class TRefundRecord {
	private String refundId;
	private String orderId;
	private Date applyTime;
	private int type;
	private int number;
	private int fee;
	private String courierNumber;
	private String courier;
	private int approver;
	private Date approveTime;
	private Date compTime;
	private int status;
	private int auditStatus;
	private String reason;
	
	
	
	
	@Override
	public String toString() {
		return "TRefundRecord [refundId=" + refundId + ", orderId=" + orderId + ", applyTime=" + applyTime + ", type="
				+ type + ", number=" + number + ", fee=" + fee + ", courierNumber=" + courierNumber + ", courier="
				+ courier + ", approver=" + approver + ", approveTime=" + approveTime + ", compTime=" + compTime
				+ ", status=" + status + ", auditStatus=" + auditStatus + ", reason=" + reason + "]";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getCourierNumber() {
		return courierNumber;
	}
	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public int getApprover() {
		return approver;
	}
	public void setApprover(int approver) {
		this.approver = approver;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public Date getCompTime() {
		return compTime;
	}
	public void setCompTime(Date compTime) {
		this.compTime = compTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
