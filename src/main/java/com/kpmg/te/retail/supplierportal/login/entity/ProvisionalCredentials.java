package com.kpmg.te.retail.supplierportal.login.entity;


public class ProvisionalCredentials {

    private String supplierid;
    private String temppwd;
    private String activeflag;
    private String newPwdSetFlag;
    private String message;
    
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getTemppwd() {
		return temppwd;
	}
	public void setTemppwd(String temppwd) {
		this.temppwd = temppwd;
	}
	public String getActiveflag() {
		return activeflag;
	}
	public void setActiveflag(String activeflag) {
		this.activeflag = activeflag;
	}
	
	public String getNewPwdSetFlag() {
		return newPwdSetFlag;
	}
	public void setNewPwdSetFlag(String newPwdSetFlag) {
		this.newPwdSetFlag = newPwdSetFlag;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ProvisionalCredentials [supplierid=" + supplierid + ", temppwd=" + temppwd + ", activeflag="
				+ activeflag + ", newPwdSetFlag=" + newPwdSetFlag + ", message=" + message + "]";
	}
    
	
    
}
