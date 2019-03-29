package com.jjn.mall.user.dao.pojo;

import java.util.Date;

/**
 * 部门信息表实体类
 * 
 * @author 翟仁元
 *
 */
public class TDepartmentInfo {
	private int departmentId;
	private String name;
	private int parentId;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	
	@Override
	public String toString() {
		return "TDepartmentInfo [departmentId=" + departmentId + ", name=" + name + ", parentId=" + parentId
				+ ", status=" + status + ", creater=" + creater + ", createTime=" + createTime + ", modifier="
				+ modifier + ", modifyTime=" + modifyTime + "]";
	}
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
