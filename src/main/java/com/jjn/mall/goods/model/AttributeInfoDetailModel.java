package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeValue;

public class AttributeInfoDetailModel {

	private int id;
	private String name;
	private int templetId;
	private List<TAttributeValue> valueList;
	
	
	public int getTempletId() {
		return templetId;
	}
	public void setTempletId(int templetId) {
		this.templetId = templetId;
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
	public List<TAttributeValue> getValueList() {
		return valueList;
	}
	public void setValueList(List<TAttributeValue> valueList) {
		this.valueList = valueList;
	}
	
	@Override
	public String toString() {
		return "AttributeInfoDetailModel [id=" + id + ", name=" + name + ", templetId=" + templetId + ", valueList="
				+ valueList + "]";
	}
	
	
}
