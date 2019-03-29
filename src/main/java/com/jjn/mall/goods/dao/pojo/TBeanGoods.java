package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TBeanGoods {

	private int id;
	private int goodsId;
	private String name;
	private String merchantName;
	private int type;
	private int sequence;
	private int status;
	private int creater;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private int modifier;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifytime;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

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

	@Override
	public String toString() {
		return "TBeanGoods [id=" + id + ", goodsId=" + goodsId + ", type=" + type + ", sequence=" + sequence
				+ ", status=" + status + ", creater=" + creater + ", createTime=" + createTime + ", modifier="
				+ modifier + ", modifytime=" + modifytime + "]";
	}

}
