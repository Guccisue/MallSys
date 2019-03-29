package com.jjn.mall.merchant.dao.pojo;

import java.util.Date;

/**
 * 商户信息表实体类
 * @author 翟仁元
 *
 */
public class TMerchantInfo {
	private int merchantId;
	private String merchantCode;
	private String name;
	private String unifiedCreditCode;
	private String address;
	private String phone;
	private String contacts;
	private String contactsPhone;
	private int score;
	private int status;
	private int creater;
	private Date createTime;
	private int modifier;
	private Date modifyTime;
	private String qqNumber;
	
	
	
	@Override
	public String toString() {
		return "TMerchantInfo [merchantId=" + merchantId + ", merchantCode=" + merchantCode + ", name=" + name
				+ ", unifiedCreditCode=" + unifiedCreditCode + ", address=" + address + ", phone=" + phone
				+ ", contacts=" + contacts + ", contactsPhone=" + contactsPhone + ", score=" + score + ", status="
				+ status + ", creater=" + creater + ", createTime=" + createTime + ", modifier=" + modifier
				+ ", modifyTime=" + modifyTime + ", qqNumber=" + qqNumber + "]";
	}
	
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnifiedCreditCode() {
		return unifiedCreditCode;
	}
	public void setUnifiedCreditCode(String unifiedCreditCode) {
		this.unifiedCreditCode = unifiedCreditCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getContactsPhone() {
		return contactsPhone;
	}
	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
