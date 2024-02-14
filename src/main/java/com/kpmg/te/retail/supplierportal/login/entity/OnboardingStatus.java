package com.kpmg.te.retail.supplierportal.login.entity;

public class OnboardingStatus {

	private String otpValidationStatus;
	private String onboardingStatus;
	private String registrationId;
	private String onboardingMsg;

	public String getOtpValidationStatus() {
		return otpValidationStatus;
	}

	public void setOtpValidationStatus(String otpValidationStatus) {
		this.otpValidationStatus = otpValidationStatus;
	}

	public String getOnboardingStatus() {
		return onboardingStatus;
	}

	public void setOnboardingStatus(String onboardingStatus) {
		this.onboardingStatus = onboardingStatus;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getOnboardingMsg() {
		return onboardingMsg;
	}

	public void setOnboardingMsg(String onboardingMsg) {
		this.onboardingMsg = onboardingMsg;
	}

	@Override
	public String toString() {
		return "OnboardingStatus [otpValidationStatus=" + otpValidationStatus + ", onboardingStatus=" + onboardingStatus
				+ ", registrationId=" + registrationId + ", onboardingMsg=" + onboardingMsg + "]";
	}

}
