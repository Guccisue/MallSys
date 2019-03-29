package com.jjn.mall.goods.model;

import java.util.List;

/**
 * 模板详情实体类
 * @author 倪宝亮
 *
 */
public class TempletDetailModel {

	private int id;
	private String name;
	private int parentId;
	private int categoryId;
	private int merchantId;
	private List<AttributeInfoDetailModel> attributeInfoList;
	
	
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
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
	public List<AttributeInfoDetailModel> getAttributeInfoList() {
		return attributeInfoList;
	}
	public void setAttributeInfoList(List<AttributeInfoDetailModel> attributeInfoList) {
		this.attributeInfoList = attributeInfoList;
	}
	@Override
	public String toString() {
		return "TempletDetailModel [id=" + id + ", name=" + name + ", parentId=" + parentId + ", categoryId="
				+ categoryId + ", attributeInfoList=" + attributeInfoList + "]";
	}
	
}
