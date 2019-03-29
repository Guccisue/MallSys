package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TChanceGoods;

public class ChanceGoodsModel {
	private int startNum;
	private int endNum;
	private int count;
	private int goodsId;
	private String goodsName;
	private String merchantName;
	private List<TChanceGoods> chanceGoodsList;
	private int pageNo;
	private int pageSize;
	
	
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public List<TChanceGoods> getChanceGoodsList() {
		return chanceGoodsList;
	}
	public void setChanceGoodsList(List<TChanceGoods> chanceGoodsList) {
		this.chanceGoodsList = chanceGoodsList;
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
		return "ChanceGoodsModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", goodsId="
				+ goodsId + ", goodsName=" + goodsName + ", merchantName=" + merchantName + ", chanceGoodsList="
				+ chanceGoodsList + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
}
