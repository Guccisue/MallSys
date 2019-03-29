package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 属性实体类
 * @author 倪宝亮
 *
 */
public class TAttributeInfo {

	private int id;
	private int templetId;
	private String name;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTempletId() {
		return templetId;
	}
	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return "TAttributeInfo [id=" + id + ", templetId=" + templetId + ", name=" + name + ", creater=" + creater
				+ ", createTime=" + createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}
	
}
