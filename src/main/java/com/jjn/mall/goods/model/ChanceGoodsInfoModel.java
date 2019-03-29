package com.jjn.mall.goods.model;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TStandardInfo;

public class ChanceGoodsInfoModel {

	private int goodsId;
	private String goodsName;
	private String merchantName;
	private List<TStandardInfo> standardInfoList;
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
	public List<TStandardInfo> getStandardInfoList() {
		return standardInfoList;
	}
	public void setStandardInfoList(List<TStandardInfo> standardInfoList) {
		this.standardInfoList = standardInfoList;
	}
	@Override
	public String toString() {
		return "ChanceGoodsInfoModel [goodsId=" + goodsId + ", goodsName=" + goodsName + ", merchantName="
				+ merchantName + ", standardInfoList=" + standardInfoList + "]";
	}
	
}
