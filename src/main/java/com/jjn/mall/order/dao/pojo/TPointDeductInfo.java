package com.jjn.mall.order.dao.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TPointDeductInfo {

	private int id;
	private int merchantId;
	private String merchantName;
	private int target;
	private int type;
	private int goodsId;
	private String name;
	private int categoryId;
	private double deductPoint;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	private int parentId;
	private String parentName;
	
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public double getDeductPoint() {
		return deductPoint;
	}
	public void setDeductPoint(double deductPoint) {
		this.deductPoint = deductPoint;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getCreater() {
		return creater;
	}
	public void setCreater(int creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getModifier() {
		return modifier;
	}
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "TPointDeductInfo [id=" + id + ", merchantId=" + merchantId + ", merchantName=" + merchantName
				+ ", type=" + type + ", goodsId=" + goodsId + ", name=" + name + ", categoryId=" + categoryId
				+ ", deductPoint=" + deductPoint + ", beginTime=" + beginTime + ", endTime=" + endTime + ", status="
				+ status + ", creater=" + creater + ", createTime=" + createTime + ", modifier=" + modifier
				+ ", modifyTime=" + modifyTime + "]";
	}
	
}
