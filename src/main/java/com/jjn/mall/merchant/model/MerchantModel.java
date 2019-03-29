package com.jjn.mall.merchant.model;

import java.util.List;

import com.jjn.mall.merchant.dao.pojo.TMerchantInfo;

public class MerchantModel {
	private int startNum;
	private int endNum;
	private String name;
	private int count;
	private List<TMerchantInfo> merchantInfoList;
	private int pageNo;
	private int pageSize;
	private int merchantId;
	private String qqNumber;
	
	
	
	
	
	@Override
	public String toString() {
		return "MerchantModel [startNum=" + startNum + ", endNum=" + endNum + ", name=" + name + ", count=" + count
				+ ", merchantInfoList=" + merchantInfoList + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", merchantId=" + merchantId + ", qqNumber=" + qqNumber + "]";
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<TMerchantInfo> getMerchantInfoList() {
		return merchantInfoList;
	}
	public void setMerchantInfoList(List<TMerchantInfo> merchantInfoList) {
		this.merchantInfoList = merchantInfoList;
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
	
}
