package com.jjn.mall.user.dao.pojo;

import java.sql.Date;


/**
 * 用户信息表实体类
 * 
 * @author 翟仁元
 *
 */
public class TUserInfo {
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
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	private String roleName;
	private String departName;
	
	@Override
	public String toString() {
		return "TUserInfo [userId=" + userId + ", account=" + account + ", password=" + password + ", name=" + name
				+ ", phone=" + phone + ", type=" + type + ", merchantId=" + merchantId + ", departmentId="
				+ departmentId + ", roleId=" + roleId + ", status=" + status + ", creater=" + creater + ", createTime="
				+ createTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
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
