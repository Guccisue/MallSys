package com.jjn.mall.user.model;

import java.util.List;

public class UserModel {
	private int userId;
	private String account;
	private String password;
	private String name;
	private String phone;
	private int type;
	private int merchantId;
	private int departmentId;
	private int roleId;
	private int status;
	@SuppressWarnings("rawtypes")
	private List authInfoList;
	
	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", account=" + account + ", password=" + password + ", name=" + name
				+ ", phone=" + phone + ", type=" + type + ", merchantId=" + merchantId + ", departmentId="
				+ departmentId + ", roleId=" + roleId + ", status=" + status + ", authInfoList=" + authInfoList + "]";
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@SuppressWarnings("rawtypes")
	public List getAuthInfoList() {
		return authInfoList;
	}
	@SuppressWarnings("rawtypes")
	public void setAuthInfoList(List authInfoList) {
		this.authInfoList = authInfoList;
	}
}
