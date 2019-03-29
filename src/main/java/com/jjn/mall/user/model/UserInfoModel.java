package com.jjn.mall.user.model;

import java.util.List;

import com.jjn.mall.user.dao.pojo.TUserInfo;

public class UserInfoModel {
	private List<TUserInfo> userInfoList;
	private int startNum;
	private int endNum;
	private int count;
	private int status;
	private String phone;
	private int pageNo;
	private int pageSize;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<TUserInfo> getUserInfoList() {
		return userInfoList;
	}
	public void setUserInfoList(List<TUserInfo> userInfoList) {
		this.userInfoList = userInfoList;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
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
	@Override
	public String toString() {
		return "UserInfoModel [userInfoList=" + userInfoList + ", startNum=" + startNum + ", endNum=" + endNum
				+ ", count=" + count + ", status=" + status + ", phone=" + phone + ", pageNo=" + pageNo + ", pageSize="
				+ pageSize + "]";
	}
	
}
