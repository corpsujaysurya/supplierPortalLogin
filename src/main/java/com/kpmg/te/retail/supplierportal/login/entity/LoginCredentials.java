package com.kpmg.te.retail.supplierportal.login.entity;

public class LoginCredentials {
	
	private String supplierId;
	private String passcode;
	private String tempRegistrationId;
	private String registrationStatus;
	
	
	private RegistrationInfo ri;
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getTempRegistrationId() {
		return tempRegistrationId;
	}
	public void setTempRegistrationId(String tempRegistrationId) {
		this.tempRegistrationId = tempRegistrationId;
	}
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	
	public RegistrationInfo getRi() {
		return ri;
	}
	public void setRi(RegistrationInfo ri) {
		this.ri = ri;
	}
	
	public String toString() {
		return "LoginCredentials [supplierId=" + supplierId + ", passcode=" + passcode + ", tempRegistrationId="
				+ tempRegistrationId + ", registrationStatus=" + registrationStatus + ", ri=" + ri + "]";
	}
	
	

}
