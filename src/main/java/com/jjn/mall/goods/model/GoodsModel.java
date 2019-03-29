package com.jjn.mall.goods.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.jjn.mall.goods.dao.pojo.TGoodsInfo;

public class GoodsModel {
	private int startNum;
	private int endNum;
	private int count;
	private int status;
	private int merchantId;
	private String merchantName;
	private String name;
	private int goodsId;
	private int categoryId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private List<TGoodsInfo> goodsInfoList;
	private int pageNo;
	private int pageSize;
	
	
	@Override
	public String toString() {
		return "GoodsModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", status=" + status
				+ ", merchantId=" + merchantId + ", name=" + name + ", goodsInfoList=" + goodsInfoList + ", pageNo="
				+ pageNo + ", pageSize=" + pageSize + "]";
	}
	
	

	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TGoodsInfo> getGoodsInfoList() {
		return goodsInfoList;
	}
	public void setGoodsInfoList(List<TGoodsInfo> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
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
