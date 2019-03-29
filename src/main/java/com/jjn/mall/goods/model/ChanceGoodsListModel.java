package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.ChanceGoodsList;

public class ChanceGoodsListModel {

	private int startNum;
	private int endNum;
	private int count;
	private String goodsName;
	private List<ChanceGoodsList> chanceGoodsList;
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
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public List<ChanceGoodsList> getChanceGoodsList() {
		return chanceGoodsList;
	}
	public void setChanceGoodsList(List<ChanceGoodsList> chanceGoodsList) {
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
		return "ChanceGoodsListModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", goodsName="
				+ goodsName + ", chanceGoodsList=" + chanceGoodsList + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ "]";
	}
	
}
