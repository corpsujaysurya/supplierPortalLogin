package com.kpmg.te.retail.supplierportal.login.manager;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kpmg.te.retail.supplierportal.login.constants.SQLConstants;
import com.kpmg.te.retail.supplierportal.login.dao.SupplierPortalLoginDao;
import com.kpmg.te.retail.supplierportal.login.entity.LoginCredentials;
import com.kpmg.te.retail.supplierportal.login.entity.ProvisionalCredentials;
import com.kpmg.te.retail.supplierportal.login.entity.RegistrationInfo;
import com.kpmg.te.retail.supplierportal.login.entity.SupplierOnboarding;
import com.kpmg.te.retail.supplierportal.login.utils.LoginUtils;

import jakarta.validation.Valid;

@Component
public class SupplierPortalLoginManager {
	
	@Autowired
	SupplierPortalLoginDao supplierPortalLoginDao;
	
	@Autowired
	LoginUtils loginUtils;
	
	@Autowired
	EmailServiceUtils emailServiceUtils;

	private static final Logger logger = Logger.getLogger(SupplierPortalLoginManager.class.getName());
	
	public ProvisionalCredentials doFirstLogin(@Valid ProvisionalCredentials pCred) throws SQLException {
		ProvisionalCredentials pc = new ProvisionalCredentials();
		logger.info("SupplierPortalLoginManager: -> Begin First Login Process");
		String supplierId = pCred.getSupplierid();
		logger.info("pcred -> data from UI " + pCred.toString());
		pc = supplierPortalLoginDao.getTemporaryRegistrationDetails(supplierId);
		String decodedPwd = new String(Base64.getDecoder().decode(pc.getTemppwd()), StandardCharsets.UTF_8);
		logger.info(decodedPwd);
		if (supplierId.equals(pc.getSupplierid()) && pCred.getTemppwd().equals(decodedPwd)
				&& pc.getActiveflag().equalsIgnoreCase("Y")) {
			supplierPortalLoginDao.updateProvCredTable(supplierId,SQLConstants.SUCCESS_MESSAGE,"N");
			pc.setMessage(SQLConstants.SUCCESS_MESSAGE);
			pc.setNewPwdSetFlag("N");
			pc.setActiveflag("N");
		} else {
			pc.setMessage(SQLConstants.TEMPLOGINFAIL_MESSAGE);
			pc.setNewPwdSetFlag("N");
		}
		return pc;
	}
	
	public String resetPwd(LoginCredentials lc) throws SQLException {
		logger.info("SupplierPortalLoginManager: -> Begin Password Reset Flow" + lc);
		String encodedPwd = Base64.getEncoder().encodeToString(lc.getPasscode().getBytes());
		supplierPortalLoginDao.updateProvisionalCredPos(lc.getSupplierId());
		
		supplierPortalLoginDao.setLoginCred(lc.getSupplierId(),encodedPwd,"INACTIVE");
		return SQLConstants.SUCCESS_MESSAGE;
	}


	public String registerUser(RegistrationInfo ri) throws SQLException {
		logger.info("SupplierPortalLoginManager: -> Begin User Registration Flow" + ri);
		String regNum = "REG-"+loginUtils.generateRandRegNum();
		supplierPortalLoginDao.updateRegistrationInfo(ri.getSupplierId(),ri.getSupplierName(),ri.getEmailId(),ri.getLandlineNum(),ri.getPhoneNum(),regNum,"IN-PROGRESS");
		return regNum;
	}
	
	

	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - FORGOT PASSWORD BUSINESS LOGIC                                                                      */
	/**************************************************************************************************************************************************************************/
	public void forgotPwd(String supplierId) {
		logger.info("SupplierPortalLoginManager: -> The supplier ID is: " + supplierId);
		String email = checkIfValidUser(supplierId);
		if (email.trim() != "" && email != null) {
			emailServiceUtils.sendOtpviamail(email);
		}
	}

	private String checkIfValidUser(String supplierId) {
		return supplierPortalLoginDao.checkUser(supplierId);
	}

	public boolean validateOtp(Integer otp, String supplierId) {
		boolean otpValidationStatus;
		logger.info("SupplierPortalLoginManager: -> OTP Validation begin");
		otpValidationStatus = supplierPortalLoginDao.validateOtp(otp, supplierId);
		return otpValidationStatus;
	}

	public boolean setNewPassword(String supplierId, String encodedPasscode) throws SQLException {
		boolean passcodeSetStatus;
		String encodedPwd = Base64.getEncoder().encodeToString(encodedPasscode.getBytes());
		logger.info("SupplierPortalLoginManager: -> OTP Validation begin");
		passcodeSetStatus = supplierPortalLoginDao.setNewPwd(supplierId, encodedPwd);
		return passcodeSetStatus;
	}
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - CONTINUE REGISTRATION BUSINESS LOGIC                                                                */
	/**************************************************************************************************************************************************************************/
	public String continueRegistration(String registrationId) {
		String msg = supplierPortalLoginDao.checkIfRegisteredUser(registrationId);
		logger.info("[C]SupplierPortalLoginManager::[M]continueRegistration::->"+ msg);
		return msg;
	}
	
	public String validateOtpContRegis(Integer otp, String registrationId) {
		String finalStatus;
		logger.info("SupplierPortalLoginManager: -> OTP Validation begin");
		finalStatus = supplierPortalLoginDao.validateOtpForContRegis(otp, registrationId);
		logger.info("[C]SupplierPortalLoginManager::[M]validateOtpContRegis::->"+ finalStatus);
		return finalStatus;
	}

	public String saveCustomerOnboardingDetails(@Valid SupplierOnboarding so) throws SQLException {
		String status = null;
		status = supplierPortalLoginDao.saveCustomerOnboardingDetails(so);
		supplierPortalLoginDao.updateOnboardingStatus(so.getRegsitration_id());
		return status;
	}

	public SupplierOnboarding retriveCustomerOnboardingDetails(@Valid String registrationId) {
		SupplierOnboarding so = new SupplierOnboarding();
		so = supplierPortalLoginDao.getSupplierOnboardingDetails(registrationId);
		return so;
	}

}
