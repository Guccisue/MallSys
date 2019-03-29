package com.jjn.mall.order.dao.pojo;

import java.util.Date;

/**
 * 订单记录表实体类
 * 
 * @author 翟仁元
 *
 */
public class TOrderRecord {
	private String orderId;
	private String userIdApp;
	private String receiver;
	private String receiveAddress;
	private String phone;
	private int merchantId;
	private String merchantName;
	private int goodsId;
	private String goodsName;
	private int type;
	private double deductPoint;
	private int standardId;
	private String standardName;
	private int price;
	private int number;
	private int totalPrice;
	private String couponId;
	private int couponFee;
	private int realFee;
	private int goldNumber;
	private String remark;
	private int status;
	private Date createTime;
	private int payType;
	private Date payTime;
	private Date deliverTime;
	private String courier;
	private String courierNumber;
	private Date receiveTime;
	private int refundTimes;
	private int modifier;
	private String transactionId;
	private int refundStatus;
	private int auditStatus;
	private int fee;
	private String reason;
	private String refundId;
	private int canRefund;
	private int hasRefundMoney;
    private int count;
	private int canBalanceBefore;
	private int canBalanceAfter;

	@Override
	public String toString() {
		return "TOrderRecord [orderId=" + orderId + ", userIdApp=" + userIdApp + ", receiver=" + receiver
				+ ", receiveAddress=" + receiveAddress + ", phone=" + phone + ", merchantId=" + merchantId
				+ ", merchantName=" + merchantName + ", goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", standardId=" + standardId + ", standardName=" + standardName + ", price=" + price + ", number="
				+ number + ", totalPrice=" + totalPrice + ", couponId=" + couponId + ", couponFee=" + couponFee
				+ ", realFee=" + realFee + ", remark=" + remark + ", status=" + status + ", createTime=" + createTime
				+ ", payType=" + payType + ", payTime=" + payTime + ", deliverTime=" + deliverTime + ", courier="
				+ courier + ", courierNumber=" + courierNumber + ", receiveTime=" + receiveTime + ", refundTimes="
				+ refundTimes + ", modifier=" + modifier + ", transactionId=" + transactionId + ", refundStatus="
				+ refundStatus + ", auditStatus=" + auditStatus + ", fee=" + fee + ", reason=" + reason + ", refundId="
				+ refundId + ", canRefund=" + canRefund + "]";
	}


	

	public int getGoldNumber() {
		return goldNumber;
	}
	public void setGoldNumber(int goldNumber) {
		this.goldNumber = goldNumber;
	}
	public int getCanBalanceAfter() {
		return canBalanceAfter;
	}
	public void setCanBalanceAfter(int canBalanceAfter) {
		this.canBalanceAfter = canBalanceAfter;
	}
	public int getCanBalanceBefore() {
		return canBalanceBefore;
	}
	public void setCanBalanceBefore(int canBalanceBefore) {
		this.canBalanceBefore = canBalanceBefore;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getHasRefundMoney() {
		return hasRefundMoney;
	}
    public void setHasRefundMoney(int hasRefundMoney) {
		this.hasRefundMoney = hasRefundMoney;
	}
	public int getCanRefund() {
		return canRefund;
	}
	public void setCanRefund(int canRefund) {
		this.canRefund = canRefund;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public int getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserIdApp() {
		return userIdApp;
	}
	public void setUserIdApp(String userIdApp) {
		this.userIdApp = userIdApp;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getStandardId() {
		return standardId;
	}
	public void setStandardId(int standardId) {
		this.standardId = standardId;
	}
	public String getStandardName() {
		return standardName;
	}
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public int getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(int couponFee) {
		this.couponFee = couponFee;
	}
	public int getRealFee() {
		return realFee;
	}
	public void setRealFee(int realFee) {
		this.realFee = realFee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getCourierNumber() {
		return courierNumber;
	}
	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public int getRefundTimes() {
		return refundTimes;
	}
	public void setRefundTimes(int refundTimes) {
		this.refundTimes = refundTimes;
	}
	public int getModifier() {
		return modifier;
	}
	public void setModifier(int modifier) {
		this.modifier = modifier;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
