package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 模板实体类
 * @author 倪宝亮
 *
 */
public class TTempletInfo {
	private int id;
	private String name;
	private int merchantId;
	private int categoryId;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	
	private String attributeArray;//用来接收前端传过来的属性及属性值,格式如：[{"name":"颜色","value":"红色,蓝色,绿色"},{"name":"尺寸","value":"175,180,185"}]
	
	
	
	public String getAttributeArray() {
		return attributeArray;
	}
	public void setAttributeArray(String attributeArray) {
		this.attributeArray = attributeArray;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
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
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
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
		return "TTempletInfo [id=" + id + ", name=" + name + ", merchantId=" + merchantId + ", categoryId=" + categoryId
				+ ", creater=" + creater + ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime="
				+ modifyTime + ", attributeArray=" + attributeArray + "]";
	}
	
	
}
