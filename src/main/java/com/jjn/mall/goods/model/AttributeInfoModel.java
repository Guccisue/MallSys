package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeInfo;

public class AttributeInfoModel {
	private int startNum;
	private int endNum;
	private int count;
	private String name;
	private List<TAttributeInfo> attributeInfoList;
	private int pageNo;
	private int pageSize;
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TAttributeInfo> getAttributeInfoList() {
		return attributeInfoList;
	}
	public void setAttributeInfoList(List<TAttributeInfo> attributeInfoList) {
		this.attributeInfoList = attributeInfoList;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	public String toString() {
		return "AttributeInfoModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", name=" + name
				+ ", attributeInfoList=" + attributeInfoList + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
}
