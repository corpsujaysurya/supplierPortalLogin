package com.kpmg.te.retail.supplierportal.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kpmg.te.retail.supplierportal.login.constants.SQLConstants;
import com.kpmg.te.retail.supplierportal.login.entity.ProvisionalCredentials;
import com.kpmg.te.retail.supplierportal.login.entity.SupplierOnboarding;
import com.kpmg.te.retail.supplierportal.login.manager.EmailServiceUtils;

import jakarta.validation.Valid;

@Component
public class SupplierPortalLoginDao {
	
	@Autowired
	EmailServiceUtils emailServiceUtils;

	private static final Logger logger = Logger.getLogger(SupplierPortalLoginDao.class.getName());
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - CORE CONNECTION DAO				                                                                   */
	/**************************************************************************************************************************************************************************/	
	public Connection getConnectioDetails() throws ClassNotFoundException, SQLException {
		String myDriver = SQLConstants.myDriver;
		String myUrl = SQLConstants.myUrl;
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, SQLConstants.mySQL_ID, SQLConstants.mySQL_pass);
		return conn;
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public ProvisionalCredentials getTemporaryRegistrationDetails(String supplierId) {
		ProvisionalCredentials pc = new ProvisionalCredentials();
		try {
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  * FROM SUPPLIER_PORTAL.PROVISIONAL_CREDENTIALS WHERE SUPPLIERID = '" + supplierId
					+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				pc.setSupplierid(rs.getString("supplierid"));
				pc.setTemppwd(rs.getString("temppwd"));
				pc.setActiveflag(rs.getString("activeflag"));
			}
			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		logger.info(pc.toString());
		return pc;
	}

	public void updateProvCredTable(String supplierId, String message, String newPwdSetFlag) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.PROVISIONAL_CREDENTIALS SET ACTIVEFLAG = ?, MESSAGE=?, NEWPWDSETFLAG=? WHERE SUPPLIERID = ?  ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "N");
			pstmt.setString(2, message);
			pstmt.setString(3, newPwdSetFlag);
			pstmt.setString(4, supplierId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}



	public void updateProvisionalCredPos(String supplierId) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.PROVISIONAL_CREDENTIALS SET NEWPWDSETFLAG = ? WHERE SUPPLIERID = ?  ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "Y");
			pstmt.setString(2, supplierId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void updateProvisionalCredNeg(String supplierId) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.PROVISIONAL_CREDENTIALS SET NEWPWDSETFLAG = ?,ACTIVEFLAG = ?, MESSAGE=? WHERE SUPPLIERID = ?  ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "N");
			pstmt.setString(2, "Y");
			pstmt.setString(3, SQLConstants.TEMPLOGINFAIL_MESSAGE);
			pstmt.setString(4, supplierId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void setLoginCred(String supplierId, String encodedPwd, String regStatus) throws SQLException {
			Connection conn = null;
			try {
				conn = getConnectioDetails();
		        PreparedStatement pstmt = conn.prepareStatement( "INSERT INTO SUPPLIER_PORTAL.LOGIN_CREDENTIALS (SUPPLIERID,PASSCODE,REGISTRATIONSTATUS)" +" VALUES(?,?,?)");
		        	 pstmt.setString(1, supplierId);
		             pstmt.setString(2, encodedPwd);
		             pstmt.setString(3, regStatus);
		        pstmt.execute();
			}catch ( Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection(conn);
			}
		}

	public void updateRegistrationInfo(String supplierId, String supplierName, String emailId, Long landlineNum,
			Long mobileNum, String regNum, String regStatus) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.LOGIN_CREDENTIALS SET SUPPLIERNAME = ?,SUPPLIEREMAIL = ?, LANDLINENUM=? , MOBILENUM = ?,REGISTRATIONID = ?, REGISTRATIONSTATUS = ? WHERE SUPPLIERID = ? ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, supplierName);
			pstmt.setString(2, emailId);
			pstmt.setLong(3, landlineNum);
			pstmt.setLong(4, mobileNum);
			pstmt.setString(5, regNum);
			pstmt.setString(6, regStatus);
			pstmt.setString(7, supplierId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - FORGOT PASSWORD DAO                                                                                 */
	/**************************************************************************************************************************************************************************/
	public String checkUser(String supplierId) {
		String suppleirEmail = null;
		try {
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  * FROM SUPPLIER_PORTAL.LOGIN_CREDENTIALS WHERE SUPPLIERID = '" + supplierId
					+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getString("passcode") !=null) {
					suppleirEmail = rs.getString("supplieremail");
				}
			}
			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		return suppleirEmail;
	}

	public void saveOtpToDb(int otp, String email) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.LOGIN_CREDENTIALS SET TEMP_OTP = ? WHERE SUPPLIEREMAIL = ? ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, otp);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public boolean validateOtp(Integer otp, String supplierId) {
		boolean validationStatus = false;
		try {
			long otpFromDb = 0;
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  TEMP_OTP FROM SUPPLIER_PORTAL.LOGIN_CREDENTIALS WHERE SUPPLIERID = '" + supplierId
					+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				otpFromDb = rs.getLong("TEMP_OTP");
			}
			if (otpFromDb == otp) {
				validationStatus = true;
			}
			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		return validationStatus;
	}
	
	public boolean setNewPwd(String supplierId, String encodedPwd) throws SQLException {
		Connection conn = null;
		boolean passcodeSetStatus = false;
		String updateStatus;
		try {
			conn = getConnectioDetails();
			String query = "UPDATE SUPPLIER_PORTAL.LOGIN_CREDENTIALS SET PASSCODE = ? WHERE SUPPLIERID = ? ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, encodedPwd);
			pstmt.setString(2, supplierId);
			int updateStatusCode = pstmt.executeUpdate();
			pstmt.close();
			updateStatus = (updateStatusCode == 1) ? ("SUCCESS") : ("FAILURE");
			passcodeSetStatus = (updateStatus == "SUCCESS") ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return passcodeSetStatus;
	}
	/************************************************************************************************************************************************************************** */
	/*													LOGIN MANAGEMENT - CONTINUE REGISTRATION DAO                                                                           */
	/**************************************************************************************************************************************************************************/
	public String checkIfRegisteredUser(String registrationId) {
		String registrationStatusMsg = null;
		String supplierId = null;
		String supplierEmail = null;
		String registrationstatus = null;
		try {
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  SUPPLIERID,REGISTRATIONSTATUS,SUPPLIEREMAIL FROM SUPPLIER_PORTAL.LOGIN_CREDENTIALS WHERE REGISTRATIONID = '" + registrationId+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				supplierId = rs.getString("SUPPLIERID");
				supplierEmail = rs.getString("supplieremail");
				if (supplierId != null && supplierId.trim() != "") {
					registrationstatus = rs.getString("REGISTRATIONSTATUS");
					logger.info(registrationstatus);
					registrationStatusMsg = (registrationstatus.equalsIgnoreCase("IN-PROGRESS") ? ("2-Proceed to OTP Verification")
							: ("3-Registration Complete - Proceed with login"));
				} else {
					registrationStatusMsg = "1-Invalid Registration ID provided";
				}
			}
			if(registrationstatus.equalsIgnoreCase("IN-PROGRESS")) {
				String mailSentMsg = emailServiceUtils.generateOtpViaEmail(supplierEmail);
				logger.info("[C]SupplierPortalLoginDao::[M]checkIfRegisteredUser::-> The email send-status message is:->"+mailSentMsg);
			}
			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		logger.info("[C]SupplierPortalLoginDao::[M]checkIfRegisteredUser::-> The registration status message is:->"+ registrationStatusMsg);
		return registrationStatusMsg;
	}
	
	public String validateOtpForContRegis(Integer otp, String registrationId) {
		String validationStatus = null;
		String onBoardingStatus = null;
		String finalStatus = null;
		try {
			long otpFromDb = 0;
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  TEMP_OTP,ONBOARDINGSTATUS FROM SUPPLIER_PORTAL.LOGIN_CREDENTIALS WHERE REGISTRATIONID = '" + registrationId+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				otpFromDb = rs.getLong("TEMP_OTP");
				onBoardingStatus = rs.getNString("ONBOARDINGSTATUS");
			}
			if (otpFromDb == otp) {
				validationStatus = "true";
			}
			finalStatus = validationStatus.concat("-").concat(onBoardingStatus).concat("-").concat(registrationId);
;			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		return finalStatus;
	}

	public String saveCustomerOnboardingDetails(@Valid SupplierOnboarding so) throws SQLException {
		Connection conn = null;
		String status = new String();
		try {
			conn = getConnectioDetails();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO SUPPLIER_PORTAL.SUPPLIER_ONBOARDING (COMPANY_NAME,COMPANY_CONTACT_NO,GSTIN,TIN,CURRENCY,REGISTERED_ADDR,CORRESPONDANCE_ADDR,SPOC_NAME,SPOC_TITLE,SPOC_CONTACT,SPOC_EMAIL,LICENSE_DOC,INSURANCE_DOC,REGULATORY_DOC,LITIGATION_DOC,CREDIT_INFO_DOC,NDA_DOC,SUSTAINABILITY_DOC,BUSINESS_LICENSING_DOC,CANCELLED_CHEQUE_DOC,SUBCONTRACTOR_INFO_DOC,IFSC_CODE,ACCOUNT_NUMBER,BANK_NAME,CITY,STATE,PAYMENT_MODE,BRANCH_NAME,ONBOARDING_STATUS,REGSITRATION_ID,REGSITRATION_PWD)"
							+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, so.getCompany_name());
			pstmt.setString(2, so.getCompany_contact_no());
			pstmt.setString(3, so.getGstin());
			pstmt.setString(4, so.getTin());
			pstmt.setString(5, so.getCurrency());
			pstmt.setString(6, so.getRegistered_addr());
			pstmt.setString(7, so.getCorrespondance_addr());
			pstmt.setString(8, so.getSpoc_name());
			pstmt.setString(9, so.getSpoc_title());
			pstmt.setString(10, so.getSpoc_contact());
			pstmt.setString(11, so.getSpoc_email());
			pstmt.setString(12, so.getLicense_doc());
			pstmt.setString(13, so.getInsurance_doc());
			pstmt.setString(14, so.getRegulatory_doc());
			pstmt.setString(15, so.getLitigation_doc());
			pstmt.setString(16, so.getCredit_info_doc());
			pstmt.setString(17, so.getNda_doc());
			pstmt.setString(18, so.getSustainability_doc());
			pstmt.setString(19, so.getBusiness_licensing_doc());
			pstmt.setString(20, so.getCancelled_cheque_doc());
			pstmt.setString(21, so.getSubcontractor_info_doc());
			pstmt.setString(22, so.getIfscCode());
			pstmt.setString(23, so.getAccountNo());
			pstmt.setString(24, so.getBankName());
			pstmt.setString(25, so.getCity());
			pstmt.setString(26, so.getState());
			pstmt.setString(27, so.getPaymentMode());
			pstmt.setString(28, so.getBranchName());
			pstmt.setString(29, so.getOnboarding_status());
			pstmt.setString(30, so.getRegsitration_id());
			pstmt.setString(31, so.getRegsitration_pwd());
			int updateStatusCode =pstmt.executeUpdate();
			status = (updateStatusCode==1 ? ("Record inserted in DB Successfully")
					: ("Onboarding failed"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return status;
	}

	public void updateOnboardingStatus(String regsitration_id) throws SQLException {
		Connection conn = null;
		String updateStatus;
		try {
			conn = getConnectioDetails();
			String onboardingStatus = "IN-PROGRESS";
			String query = "UPDATE SUPPLIER_PORTAL.LOGIN_CREDENTIALS SET ONBOARDINGSTATUS =' " + onboardingStatus + "' WHERE REGISTRATIONID = ? ";
			logger.info(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, regsitration_id);
			int updateStatusCode = pstmt.executeUpdate();
			pstmt.close();
			updateStatus = (updateStatusCode == 1) ? ("SUCCESS") : ("FAILURE");
			logger.info("[C]SupplierPortalLoginDao::[M]updateOnboardingStatus::-> The onboarding status is set to:->"+ updateStatus);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public SupplierOnboarding getSupplierOnboardingDetails(@Valid String registrationId) {
		SupplierOnboarding so = new SupplierOnboarding();
		try {
			Connection conn = getConnectioDetails();
			Statement st = conn.createStatement();
			String query = "SELECT  * FROM SUPPLIER_PORTAL.SUPPLIER_ONBOARDING WHERE REGSITRATION_ID = '" + registrationId+ '\'';
			logger.info(query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				so.setCompany_name( rs.getString("COMPANY_NAME"));
				so.setCompany_contact_no( rs.getString("COMPANY_CONTACT_NO"));
				so.setGstin( rs.getString("GSTIN"));
				so.setTin( rs.getString("TIN"));
				so.setCurrency( rs.getString("CURRENCY"));
				so.setRegistered_addr( rs.getString("REGISTERED_ADDR"));
				so.setCorrespondance_addr( rs.getString("CORRESPONDANCE_ADDR"));
				so.setSpoc_name( rs.getString("SPOC_NAME"));
				so.setSpoc_title( rs.getString("SPOC_TITLE"));
				so.setSpoc_contact( rs.getString("SPOC_CONTACT"));
				so.setSpoc_email( rs.getString("SPOC_EMAIL"));
				so.setLicense_doc( rs.getString("LICENSE_DOC"));
				so.setInsurance_doc( rs.getString("INSURANCE_DOC"));
				so.setRegulatory_doc( rs.getString("REGULATORY_DOC"));
				so.setLitigation_doc( rs.getString("LITIGATION_DOC"));
				so.setCredit_info_doc( rs.getString("CREDIT_INFO_DOC"));
				so.setNda_doc( rs.getString("NDA_DOC"));
				so.setSustainability_doc( rs.getString("SUSTAINABILITY_DOC"));
				so.setBusiness_licensing_doc( rs.getString("BUSINESS_LICENSING_DOC"));
				so.setCancelled_cheque_doc( rs.getString("CANCELLED_CHEQUE_DOC"));
				so.setSubcontractor_info_doc( rs.getString("SUBCONTRACTOR_INFO_DOC"));
				so.setIfscCode( rs.getString("IFSC_CODE"));
				so.setAccountNo( rs.getString("ACCOUNT_NUMBER"));
				so.setBankName( rs.getString("BANK_NAME"));
				so.setCity( rs.getString("CITY"));
				so.setState( rs.getString("STATE"));
				so.setPaymentMode( rs.getString("PAYMENT_MODE"));
				so.setBranchName( rs.getString("BRANCH_NAME"));
				so.setOnboarding_status( rs.getString("ONBOARDING_STATUS"));
				so.setRegsitration_id( rs.getString("REGSITRATION_ID"));
				so.setRegsitration_pwd( rs.getString("REGSITRATION_PWD"));
			}
;			st.close();
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Got an exception! ");
			e.printStackTrace();
		}
		return so;
	}
	
}
