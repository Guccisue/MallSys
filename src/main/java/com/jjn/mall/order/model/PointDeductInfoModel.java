package com.jjn.mall.order.model;

import java.util.List;

import com.jjn.mall.order.dao.pojo.TPointDeductInfo;

public class PointDeductInfoModel {
	private int target;
	private int startNum;
	private int endNum;
	private int count;
	private String name;
	private String merchantName;
	private List<TPointDeductInfo> pointDeductInfoList;
	private int pageNo;
	private int pageSize;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
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
	public List<TPointDeductInfo> getPointDeductInfoList() {
		return pointDeductInfoList;
	}
	public void setPointDeductInfoList(List<TPointDeductInfo> pointDeductInfoList) {
		this.pointDeductInfoList = pointDeductInfoList;
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
		return "PointDeductInfoModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count
				+ ", pointDeductInfoList=" + pointDeductInfoList + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ "]";
	}
	
}
