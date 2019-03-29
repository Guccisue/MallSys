package com.jjn.mall.goods.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用以接收保存数据
 * @author 倪宝亮
 *
 */
public class GoodsInfoModel {
	
	private int goodsId;
	private int merchantId;
	private String name;
	private String sellingPoint;
	private String description;
	private int categoryId;
	private int isRestrict;
	private String deliveryArea;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date seckillTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private int templetId;
	private int status;
	private int score;
	private int sales;
	private int likeNumber;
	private int shareNumber;
	private String picJson;
	private String standardJson;
	private String attributeJson;
	private int creater;
	private int modifier;
	private int isUpdateGoodsInfo;
	private int isUpdateGoodsPic;
	private int isUpdateGoodsAtt;
	private int isUpdateGoodsStandard;
	
	
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getTempletId() {
		return templetId;
	}
	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}
	public int getIsUpdateGoodsStandard() {
		return isUpdateGoodsStandard;
	}
	public void setIsUpdateGoodsStandard(int isUpdateGoodsStandard) {
		this.isUpdateGoodsStandard = isUpdateGoodsStandard;
	}
	public int getIsUpdateGoodsInfo() {
		return isUpdateGoodsInfo;
	}
	public void setIsUpdateGoodsInfo(int isUpdateGoodsInfo) {
		this.isUpdateGoodsInfo = isUpdateGoodsInfo;
	}
	public int getIsUpdateGoodsPic() {
		return isUpdateGoodsPic;
	}
	public void setIsUpdateGoodsPic(int isUpdateGoodsPic) {
		this.isUpdateGoodsPic = isUpdateGoodsPic;
	}
	public int getIsUpdateGoodsAtt() {
		return isUpdateGoodsAtt;
	}
	public void setIsUpdateGoodsAtt(int isUpdateGoodsAtt) {
		this.isUpdateGoodsAtt = isUpdateGoodsAtt;
	}
	public int getModifier() {
		return modifier;
	}
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}
	public int getShareNumber() {
		return shareNumber;
	}
	public void setShareNumber(int shareNumber) {
		this.shareNumber = shareNumber;
	}
	public int getCreater() {
		return creater;
	}
	public void setCreater(int creater) {
		this.creater = creater;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	
	public Date getSeckillTime() {
		return seckillTime;
	}
	public void setSeckillTime(Date seckillTime) {
		this.seckillTime = seckillTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPicJson() {
		return picJson;
	}
	public void setPicJson(String picJson) {
		this.picJson = picJson;
	}
	public String getStandardJson() {
		return standardJson;
	}
	public void setStandardJson(String standardJson) {
		this.standardJson = standardJson;
	}
	public String getAttributeJson() {
		return attributeJson;
	}
	public void setAttributeJson(String attributeJson) {
		this.attributeJson = attributeJson;
	}
	
	@Override
	public String toString() {
		return "GoodsInfoModel [goodsId=" + goodsId + ", merchantId=" + merchantId + ", name=" + name
				+ ", sellingPoint=" + sellingPoint + ", description=" + description + ", categoryId=" + categoryId
				+ ", isRestrict=" + isRestrict + ", deliveryArea=" + deliveryArea + ", seckillTime=" + seckillTime
				+ ", status=" + status + ", score=" + score + ", sales=" + sales + ", likeNumber=" + likeNumber
				+ ", shareNumber=" + shareNumber + ", picJson=" + picJson + ", standardJson=" + standardJson
				+ ", attributeJson=" + attributeJson + ", creater=" + creater + ", modifier=" + modifier
				+ ", isUpdateGoodsInfo=" + isUpdateGoodsInfo + ", isUpdateGoodsPic=" + isUpdateGoodsPic
				+ ", isUpdateGoodsAtt=" + isUpdateGoodsAtt + ", isUpdateGoodsStandard=" + isUpdateGoodsStandard + "]";
	}
	
}
