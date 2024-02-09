package com.kpmg.te.retail.supplierportal.login.service;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kpmg.te.retail.supplierportal.login.dao.SupplierPortalLoginDao;
import com.kpmg.te.retail.supplierportal.login.entity.LoginCredentials;
import com.kpmg.te.retail.supplierportal.login.entity.ProvisionalCredentials;
import com.kpmg.te.retail.supplierportal.login.entity.RegistrationInfo;
import com.kpmg.te.retail.supplierportal.login.entity.SupplierOnboarding;
import com.kpmg.te.retail.supplierportal.login.manager.SupplierPortalLoginManager;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/login/service")
public class SupplierPortalLoginService {
	
	@Autowired
	SupplierPortalLoginManager supplierPortalLoginManager;

	@Autowired
	SupplierPortalLoginDao supplierPortalLoginDao;
	
	private static final Logger logger = Logger.getLogger(SupplierPortalLoginService.class.getName());
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - TEMPORARY LOGIN REST END-POINTS                                                                     */
	/**************************************************************************************************************************************************************************/	
	@PostMapping("/doFirstLogin")
    @ResponseBody
    public ProvisionalCredentials loginUser(@Valid @RequestBody ProvisionalCredentials pCred) throws SQLException{
		return supplierPortalLoginManager.doFirstLogin(pCred);
	}
	
	@PostMapping("/doMandatoryPwdReset")
    @ResponseBody
    public String resetPassword(@Valid @RequestBody LoginCredentials lc) throws SQLException{
		return supplierPortalLoginManager.resetPwd(lc);
	}
	
	
    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void registerUser(@Valid @RequestBody RegistrationInfo ri) throws ClassNotFoundException, SQLException {
		 supplierPortalLoginManager.registerUser(ri);
	}
	
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - FORGOT PASSWORD REST END-POINTS                                                                     */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/forgotpwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String forgotPwd(@RequestParam String supplierId) throws ClassNotFoundException, SQLException {
		return supplierPortalLoginManager.forgotPwd(supplierId);
		 
	}
	
	@RequestMapping(path = "/validateOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean validateOTP(@RequestParam Integer otp,@RequestParam String supplierId) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.validateOtp(otp,supplierId);
	}
	
	@RequestMapping(path = "/setNewPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean setNewPassword(@RequestParam String supplierId,@RequestParam String password) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.setNewPassword(supplierId,password);
	}
	
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - CONTINUE REGISTRATION REST END-POINTS                                                                     */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/continueRegistration", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String continueApplication(@RequestParam String registrationId) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.continueRegistration(registrationId);
	}
	
	@RequestMapping(path = "/validateOtpContinueReg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String validateOtpContinueReg(@RequestParam Integer otp,@RequestParam String registrationId) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.validateOtpContRegis(otp,registrationId);
	}
	
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - CONTINUE ONBOARDING                                                                  */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/submitOnboardingDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String submitOnboardingDetails(@Valid @RequestBody SupplierOnboarding so) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.saveCustomerOnboardingDetails(so);
	}
	
	@RequestMapping(path = "/viewOnboardingDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public SupplierOnboarding viewOnboardingDetails(@Valid @RequestParam String registrationId) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.retriveCustomerOnboardingDetails(registrationId);
	}
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - UPDATE ONBOARDING TO SUCCESS STATUS                                                                 */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/updateOnboardingSuccess", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public SupplierOnboarding updateOnboardingSuccess(@Valid @RequestParam String registrationId,@RequestParam String supplierEmail) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.updateSupplierOnboardingStatus(registrationId,supplierEmail);
	}
	
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - UPDATE ONBOARDING MESSAGE			                                                               */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/updateOnboardingMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOnboardingMessage(@Valid @RequestParam String registrationId,@RequestParam String onboardingMessage) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.updateSupplierOnboardingMessage(registrationId,onboardingMessage);
	}
	
	@RequestMapping(path = "/updateOnboardingData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public SupplierOnboarding updateOnboardingData(@Valid @RequestParam String supplierId,@RequestParam String supplierEmail) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.updateSupplierOnboardingStatus(supplierId,supplierEmail);
		 //TO-DO PENDING - UPDATE ONBOARDING TABLE 
	}
	
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - NORMAL LOGIN							                                                               */
	/**************************************************************************************************************************************************************************/
	@RequestMapping(path = "/regularLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String regularLogin(@Valid @RequestParam String supplierId,@RequestParam String pwd) throws ClassNotFoundException, SQLException {
		 return supplierPortalLoginManager.regularLogin(supplierId,pwd);
	}
}
