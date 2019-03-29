package com.jjn.mall.user.model;

import java.util.List;

import com.jjn.mall.user.dao.pojo.TRoleInfo;

public class RoleModel {
	private List<TRoleInfo> roleInfoList;
	private int startNum;
	private int endNum;
	private int count;
	private int status;
	private int pageNo;
	private int pageSize;
	
	
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<TRoleInfo> getRoleInfoList() {
		return roleInfoList;
	}
	public void setRoleInfoList(List<TRoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}
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
	@Override
	public String toString() {
		return "RoleModel [roleInfoList=" + roleInfoList + ", startNum=" + startNum + ", endNum=" + endNum + ", count="
				+ count + ", status=" + status + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
}
