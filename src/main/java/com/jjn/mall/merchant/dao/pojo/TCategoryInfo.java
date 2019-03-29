package com.jjn.mall.merchant.dao.pojo;

import java.util.Date;

/**
 * 类目信息表实体类
 * 
 * @author 翟仁元
 *
 */
public class TCategoryInfo {
	private int categoryId;
	private String name;
	private int level;
	private int parentId;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	
	@Override
	public String toString() {
		return "TCategoryInfo [categoryId=" + categoryId + ", name=" + name + ", level=" + level + ", parentId="
				+ parentId + ", status=" + status + ", creater=" + creater + ", createTime=" + createTime
				+ ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
