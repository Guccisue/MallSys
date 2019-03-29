package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TGoodsAttribute;

public class GoodsAttributeModel {

	private String name;
	private List<TGoodsAttribute> valueList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TGoodsAttribute> getValueList() {
		return valueList;
	}
	public void setValueList(List<TGoodsAttribute> valueList) {
		this.valueList = valueList;
	}
	@Override
	public String toString() {
		return "GoodsAttributeModel [name=" + name + ", valueList=" + valueList + "]";
	}
	
	
}
