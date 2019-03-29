package com.jjn.mall.merchant.model;

import java.util.List;

import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;

public class CategoryModel {
	private int pageNo;
	private int pageSize;
	private int startNum;
	private int count;
	private String name;
	private List<TCategoryInfo> categoryInfoList;
	private int level;
	private int parentId;
	
	@Override
	public String toString() {
		return "CategoryModel [pageNo=" + pageNo + ", pageSize=" + pageSize + ", startNum=" + startNum + ", count="
				+ count + ", name=" + name + ", categoryInfoList=" + categoryInfoList + "]";
	}
	
	
	public int getParentId() {
		return parentId;
	}


	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
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
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
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
	public List<TCategoryInfo> getCategoryInfoList() {
		return categoryInfoList;
	}
	public void setCategoryInfoList(List<TCategoryInfo> categoryInfoList) {
		this.categoryInfoList = categoryInfoList;
	}
}
