
package com.kpmg.te.retail.supplierportal.login.manager;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.kpmg.te.retail.supplierportal.login.dao.SupplierPortalLoginDao;
import com.kpmg.te.retail.supplierportal.login.entity.AdminCred;

//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;

@Component
@CrossOrigin
public class EmailServiceUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private OtpService otpService;

	SupplierPortalLoginDao supplierPortalLoginDao = new SupplierPortalLoginDao();

	@Value("${spring.mail.username}")
	private String sender;

	private static final Logger logger = Logger.getLogger(EmailServiceUtils.class.getName());

	public String generateOtpViaEmail(String email) {
		try {
			logger.info("[C]:EmailServiceUtils - " + email.toString());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(email);
			int otp = otpService.generateOTP();
			logger.info("[C]:EmailServiceUtils - OTP " + otp);
			mailMessage.setText("OTP :-" + otp);
			mailMessage.setSubject("OTP for Verification....");
			supplierPortalLoginDao.saveOtpToDb(otp, email);
			logger.info("[C]:EmailServiceUtils - mailmessage " + mailMessage.toString());
			javaMailSender.send(mailMessage);

			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	public String sendOtpviamail(String supplierEmail) {
		try {
			logger.info("[C]:EmailServiceUtils - " + supplierEmail.toString());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(supplierEmail);
			int otp = otpService.generateOTP();
			logger.info("[C]:EmailServiceUtils - OTP " + otp);
			mailMessage.setText("OTP :-" + otp);
			mailMessage.setSubject("OTP for Verification....");
			supplierPortalLoginDao.saveOtpToDb(otp, supplierEmail);
			logger.info("[C]:EmailServiceUtils - mailmessage " + mailMessage.toString());
			javaMailSender.send(mailMessage);

			return "Mail Sent Successfully...";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while Sending Mail";
		}
	}

	public String sendAdminEmailDetails(AdminCred adminCred) {
		try {
			logger.info("[C]:EmailServiceUtils[M]sendAdminEmailDetails - " + adminCred.toString());
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(adminCred.getSupplierEmailId());
			int otp = otpService.generateOTP();
			logger.info("[C]:EmailServiceUtils - OTP " + otp);
			mailMessage.setText("The USER NAME IS:- " + adminCred.getUserName() + "THE TEMP PASSWORD IS:-" + adminCred.getAdminTempPwd());
			mailMessage.setSubject("SUPPLIER PORTAL: TEMPORARY ADMIN CREDENTIALS");
			logger.info("[C]:EmailServiceUtils - mailmessage " + mailMessage.toString());
			javaMailSender.send(mailMessage);

			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail";
		}
		
	}

	public String sendRegIdToUser(String supplierId, String regNum, String email) {
		try {
			logger.info("[C]:EmailServiceUtils[M]sendRegIdToUser");
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(email);
			mailMessage.setText("The SUPPLIER ID IS:- " + supplierId + "THE REGISTRATION ID IS:-" + regNum);
			mailMessage.setSubject("SUPPLIER PORTAL: REGISTRATION DETAILS");
			logger.info("[C]:EmailServiceUtils - mailmessage " + mailMessage.toString());
			javaMailSender.send(mailMessage);

			return "Mail Sent Successfully...";
		} catch (Exception e) {
			return "Error while Sending Mail";
		}
		
	}

}
