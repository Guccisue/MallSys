package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TTempletInfo;

public class TempletInfoModel {
	private int startNum;
	private int endNum;
	private int count;
	private String name;
	private int merchantId;
	private List<TTempletInfo> templetInfoList;
	private int pageNo;
	private int pageSize;
	private int categoryId;
	
	
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
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
	
	public List<TTempletInfo> getTempletInfoList() {
		return templetInfoList;
	}
	public void setTempletInfoList(List<TTempletInfo> templetInfoList) {
		this.templetInfoList = templetInfoList;
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
		return "TempletInfoModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", name=" + name
				+ ", merchantId=" + merchantId + ", templetInfoList=" + templetInfoList + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", categoryId=" + categoryId + "]";
	}
	
}
