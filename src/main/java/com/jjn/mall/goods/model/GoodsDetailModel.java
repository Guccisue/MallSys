package com.jjn.mall.goods.model;

import java.util.Date;
import java.util.List;

import com.jjn.mall.common.Config;
import com.jjn.mall.goods.dao.pojo.TGoodsPic;
import com.jjn.mall.goods.dao.pojo.TStandardInfo;

public class GoodsDetailModel {

	private int goodsId;
	private String name;
	private int status;
	private int parentId;
	private int categoryId;
	private int templetId;
	private int isRestrict;
	private String deliveryArea;
	private String sellingPoint;
	private String description;
	private Date seckillTime;
	private Date endTime;
	private List<TGoodsPic> picList;
	private List<GoodsAttributeModel> attributeList;
	private List<TStandardInfo> standardList;
	
	private int seckillCategoryId = Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"));;
	
	
	public int getSeckillCategoryId() {
		return seckillCategoryId;
	}
	public void setSeckillCategoryId(int seckillCategoryId) {
		this.seckillCategoryId = seckillCategoryId;
	}
	public int getTempletId() {
		return templetId;
	}
	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}
	public Date getSeckillTime() {
		return seckillTime;
	}
	public void setSeckillTime(Date seckillTime) {
		this.seckillTime = seckillTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getIsRestrict() {
		return isRestrict;
	}
	public void setIsRestrict(int isRestrict) {
		this.isRestrict = isRestrict;
	}
	public String getDeliveryArea() {
		return deliveryArea;
	}
	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getSellingPoint() {
		return sellingPoint;
	}
	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<TGoodsPic> getPicList() {
		return picList;
	}
	public void setPicList(List<TGoodsPic> picList) {
		this.picList = picList;
	}
	public List<GoodsAttributeModel> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(List<GoodsAttributeModel> attributeList) {
		this.attributeList = attributeList;
	}
	public List<TStandardInfo> getStandardList() {
		return standardList;
	}
	public void setStandardList(List<TStandardInfo> standardList) {
		this.standardList = standardList;
	}
	
	@Override
	public String toString() {
		return "GoodsDetailModel [goodsId=" + goodsId + ", name=" + name + ", status=" + status + ", parentId="
				+ parentId + ", categoryId=" + categoryId + ", templetId=" + templetId + ", isRestrict=" + isRestrict
				+ ", deliveryArea=" + deliveryArea + ", sellingPoint=" + sellingPoint + ", description=" + description
				+ ", seckillTime=" + seckillTime + ", endTime=" + endTime + ", picList=" + picList + ", attributeList="
				+ attributeList + ", standardList=" + standardList + "]";
	}
	
}
