package com.jjn.mall.goods.model;

import java.util.List;
import com.jjn.mall.goods.dao.pojo.TBeanGoods;

public class BeanGoodsModel {
	private int startNum;
	private int endNum;
	private int count;
	private String name;
	private List<TBeanGoods> goodsInfoList;
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

	public List<TBeanGoods> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setGoodsInfoList(List<TBeanGoods> goodsInfoList) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BeanGoodsModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", goodsInfoList="
				+ goodsInfoList + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}

}
