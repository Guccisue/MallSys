package com.jjn.mall.user.dao.pojo;

/**
 * 角色权限关系表实体类
 * 
 * @author 翟仁元
 *
 */
public class TRoleAuth {
	private int id;
	private int roleId;
	private int authId;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
}
