package com.jjn.mall.goods.dao.pojo;

import java.util.Date;
/**
 * 商品属性实体类
 * @author 倪宝亮
 *
 */
public class TGoodsAttribute {

	private int id;
	private String name;
	private String attributeValue;
	private int goodsId;
	private int creater;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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
		return "TGoodsAttribute [id=" + id + ", name=" + name + ", attributeValue=" + attributeValue + ", goodsId="
				+ goodsId + ", creater=" + creater + ", createTime=" + createTime + "]";
	}
	
}
