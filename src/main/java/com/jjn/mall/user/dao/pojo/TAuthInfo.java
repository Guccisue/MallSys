package com.jjn.mall.user.dao.pojo;

import java.util.Date;

/**
 * 权限信息表实体类
 * 
 * @author 翟仁元
 *
 */
public class TAuthInfo {
	private int authId;
	private int parentId;
	private String name;
	private String url;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	private int isHide;
	
	
	@Override
	public String toString() {
		return "TAuthInfo [authId=" + authId + ", parentId=" + parentId + ", name=" + name + ", url=" + url
				+ ", status=" + status + ", creater=" + creater + ", createTime=" + createTime + ", modifier="
				+ modifier + ", modifyTime=" + modifyTime + ", isHide=" + isHide + "]";
	}
	
	
	public int getIsHide() {
		return isHide;
	}


	public void setIsHide(int isHide) {
		this.isHide = isHide;
	}


	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
