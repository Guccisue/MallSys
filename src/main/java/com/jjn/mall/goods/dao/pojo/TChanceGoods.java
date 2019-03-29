package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 抽奖奖品实体类
 * @author nibaoliang
 *
 */
public class TChanceGoods {

	private int id;
	private int goodsId;
	private int standardId;
	private String attributeValues;
	private int price;
	private int numbers;
	private int goldNumber;
	private int chance;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValue(String attributeValues) {
		this.attributeValues = attributeValues;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStandardId() {
		return standardId;
	}
	public void setStandardId(int standardId) {
		this.standardId = standardId;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
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
	public int getCreater() {
		return creater;
	}
	public void setCreater(int creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getModifier() {
		return modifier;
	}
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "TChanceGoods [id=" + id + ", goodsId=" + goodsId + ", standardId=" + standardId + ", numbers=" + numbers
				+ ", goldNumber=" + goldNumber + ", chance=" + chance + ", status=" + status + ", creater=" + creater
				+ ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}
	
}
