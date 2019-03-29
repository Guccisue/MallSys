package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 商品规格信息实体类
 * @author 倪宝亮
 *
 */
public class TStandardInfo {
	private int standardId;
	private int goodsId;
	private String attributeIds;
	private String attributeValues;
	private int marketPrice;
	private int price;
	private int goldNumber;
	private int stock;
	private String pic;
	private int creater;
	private Date createTime;
	
	
	public int getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getGoldNumber() {
		return goldNumber;
	}
	public void setGoldNumber(int goldNumber) {
		this.goldNumber = goldNumber;
	}
	public String getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(String attributeValues) {
		this.attributeValues = attributeValues;
	}
	public int getStandardId() {
		return standardId;
	}
	public void setStandardId(int standardId) {
		this.standardId = standardId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getAttributeIds() {
		return attributeIds;
	}
	public void setAttributeIds(String attributeIds) {
		this.attributeIds = attributeIds;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
	@Override
	public String toString() {
		return "TStandardInfo [standardId=" + standardId + ", goodsId=" + goodsId + ", attributeIds=" + attributeIds
				+ ", attributeValues=" + attributeValues + ", price=" + price + ", stock=" + stock + ", pic=" + pic
				+ ", creater=" + creater + ", createTime=" + createTime + "]";
	}
	
}
