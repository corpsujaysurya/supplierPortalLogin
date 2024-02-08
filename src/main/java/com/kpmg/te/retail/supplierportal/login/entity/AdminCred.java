package com.kpmg.te.retail.supplierportal.login.entity;

public class AdminCred {

	private String userName;
	private String adminTempPwd;
	private String supplierEmailId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAdminTempPwd() {
		return adminTempPwd;
	}

	public void setAdminTempPwd(String adminTempPwd) {
		this.adminTempPwd = adminTempPwd;
	}

	public String getSupplierEmailId() {
		return supplierEmailId;
	}

	public void setSupplierEmailId(String supplierEmailId) {
		this.supplierEmailId = supplierEmailId;
	}

	@Override
	public String toString() {
		return "AdminCred [userName=" + userName + ", adminTempPwd=" + adminTempPwd + ", supplierEmailId="
				+ supplierEmailId + "]";
	}

}
