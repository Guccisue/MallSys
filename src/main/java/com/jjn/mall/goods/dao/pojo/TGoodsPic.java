package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 商品图片实体类
 * @author 倪宝亮
 *
 */
public class TGoodsPic {

	private int id;
	private String pic;
	private String picSize;
	private int type;
	private int goodsId;
	private int sequence;
	private int creater;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
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
	
	@Override
	public String toString() {
		return "TGoodPic [id=" + id + ", pic=" + pic + ", picSize=" + picSize + ", type=" + type + ", goodsId="
				+ goodsId + ", sequence=" + sequence + ", creater=" + creater + ", createTime=" + createTime + "]";
	}
	
}
