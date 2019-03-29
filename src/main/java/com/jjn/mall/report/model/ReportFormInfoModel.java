package com.jjn.mall.report.model;

import java.util.List;

import com.jjn.mall.report.dao.pojo.TReportFormInfo;

public class ReportFormInfoModel {
	private String reportTime;
	private int merchantId;
	private String merchantName;
	private int count;
	private int pageNo;
	private int pageSize;
	private int startNum;
	private int endNum;
	private List<TReportFormInfo> reportFormInfoList;
	
	
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
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
	public List<TReportFormInfo> getReportFormInfoList() {
		return reportFormInfoList;
	}
	public void setReportFormInfoList(List<TReportFormInfo> reportFormInfoList) {
		this.reportFormInfoList = reportFormInfoList;
	}
	@Override
	public String toString() {
		return "ReportFormInfoModel [reportTime=" + reportTime + ", merchantId=" + merchantId + ", merchantName="
				+ merchantName + ", count=" + count + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", startNum="
				+ startNum + ", endNum=" + endNum + ", reportFormInfoList=" + reportFormInfoList + "]";
	}
	
}
