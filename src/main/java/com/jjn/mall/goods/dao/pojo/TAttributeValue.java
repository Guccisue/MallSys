package com.jjn.mall.goods.dao.pojo;

import java.util.Date;

/**
 * 属性值实体类
 * @author 倪宝亮
 *
 */
public class TAttributeValue {

	private int id;
	private int attributeId;
	private String attributeValue;
	private int creater;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
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
	
	@Override
	public String toString() {
		return "TAttributeValue [id=" + id + ", attributeId=" + attributeId + ", attributeValue=" + attributeValue
				+ ", creater=" + creater + ", createTime=" + createTime + "]";
	}
	
}
