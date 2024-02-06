package com.kpmg.te.retail.supplierportal.login.entity;

public class RegistrationInfo {
	
	private String supplierId;
	private String supplierName;
	private String emailId;
	private Long landlineNum;
	private Long phoneNum;
	
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getLandlineNum() {
		return landlineNum;
	}
	public void setLandlineNum(Long landlineNum) {
		this.landlineNum = landlineNum;
	}
	public Long getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(Long phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	@Override
	public String toString() {
		return "RegistrationInfo [supplierId=" + supplierId + ", supplierName=" + supplierName + ", emailId=" + emailId
				+ ", landlineNum=" + landlineNum + ", phoneNum=" + phoneNum + "]";
	}

	
	

	
	
	

}
