package com.jjn.mall.goods.dao.pojo;

public class ChanceGoodsList {

	private int id;
	private int goodsId;
	private String goodsName;
	private int standardId;
	private String merchantName;
	private String attributeValues;
	private int numbers;
	private int goldNumber;
	private int chance;
	private int luckyPeoples;
	private int allPeoples;
	
	
	public int getStandardId() {
		return standardId;
	}
	public void setStandardId(int standardId) {
		this.standardId = standardId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(String attributeValues) {
		this.attributeValues = attributeValues;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	public int getGoldNumber() {
		return goldNumber;
	}
	public void setGoldNumber(int goldNumber) {
		this.goldNumber = goldNumber;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	public int getLuckyPeoples() {
		return luckyPeoples;
	}
	public void setLuckyPeoples(int luckyPeoples) {
		this.luckyPeoples = luckyPeoples;
	}
	public int getAllPeoples() {
		return allPeoples;
	}
	public void setAllPeoples(int allPeoples) {
		this.allPeoples = allPeoples;
	}
	@Override
	public String toString() {
		return "ChanceGoodsList [id=" + id + ", goodsId=" + goodsId + ", goodsName=" + goodsName + ", standardId="
				+ standardId + ", merchantName=" + merchantName + ", attributeValues=" + attributeValues + ", numbers="
				+ numbers + ", goldNumber=" + goldNumber + ", chance=" + chance + ", luckyPeoples=" + luckyPeoples
				+ ", allPeoples=" + allPeoples + "]";
	}
	
}
