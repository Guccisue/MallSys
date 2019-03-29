package com.jjn.mall.report.model;

public class ReportModel {

	private int merchantId;
	private int sumCanBalanceBefore;
	private int sumCanBalanceAfter;
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public int getSumCanBalanceBefore() {
		return sumCanBalanceBefore;
	}
	public void setSumCanBalanceBefore(int sumCanBalanceBefore) {
		this.sumCanBalanceBefore = sumCanBalanceBefore;
	}
	public int getSumCanBalanceAfter() {
		return sumCanBalanceAfter;
	}
	public void setSumCanBalanceAfter(int sumCanBalanceAfter) {
		this.sumCanBalanceAfter = sumCanBalanceAfter;
	}
	@Override
	public String toString() {
		return "ReportModel [merchantId=" + merchantId + ", sumCanBalanceBefore=" + sumCanBalanceBefore
				+ ", sumCanBalanceAfter=" + sumCanBalanceAfter + "]";
	}
	
}
