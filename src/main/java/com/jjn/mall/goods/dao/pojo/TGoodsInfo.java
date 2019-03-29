package com.jjn.mall.goods.dao.pojo;

import java.util.Date;
/**
 * 商品信息实体类
 * @author 倪宝亮
 *
 */
public class TGoodsInfo {

	private int goodsId;
	private String goodsCode;
	private int merchantId;
	private String merchantName;
	private String name;
	private int type;
	private String sellingPoint;
	private String description;
	private int categoryId;
	private String categoryName;
	private int score;
	private int sales;
	private int likeNumber;
	private int shareNumber;
	private int isRestrict;
	private String deliveryArea;
	private Date seckillTime;
	private Date endTime;
	private int templetId;
	private int status;
	private String rejectReason;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
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
	public TGoodsInfo() {
		super();
	}		
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
		return "TGoodsInfo [goodsId=" + goodsId + ", goodsCode=" + goodsCode + ", merchantId=" + merchantId
				+ ", merchantName=" + merchantName + ", name=" + name + ", type=" + type + ", sellingPoint="
				+ sellingPoint + ", description=" + description + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", score=" + score + ", sales=" + sales + ", likeNumber=" + likeNumber
				+ ", shareNumber=" + shareNumber + ", isRestrict=" + isRestrict + ", deliveryArea=" + deliveryArea
				+ ", seckillTime=" + seckillTime + ", status=" + status + ", creater=" + creater + ", createTime="
				+ createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}
}
