package com.jjn.mall.user.model;

import java.util.List;

import com.jjn.mall.user.dao.pojo.TDepartmentInfo;

public class DepartmentModel {
	private List<TDepartmentInfo> departmentInfoList;
	private int startNum;
	private int endNum;
	private int count;
	private String name;
	
	@Override
	public String toString() {
		return "DepartmentModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", name=" + name
				+ "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<TDepartmentInfo> getDepartmentInfoList() {
		return departmentInfoList;
	}
	public void setDepartmentInfoList(List<TDepartmentInfo> departmentInfoList) {
		this.departmentInfoList = departmentInfoList;
	}
}
