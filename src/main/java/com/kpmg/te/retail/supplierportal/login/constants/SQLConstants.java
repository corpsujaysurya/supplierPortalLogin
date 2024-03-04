package com.kpmg.te.retail.supplierportal.login.constants;

public class SQLConstants {
	
	//DB Details
	public static final String myDriver = "com.mysql.cj.jdbc.Driver";
	public static final String myUrl = "jdbc:mysql://localhost/supplier_portal";
	public static final String mySQL_ID = "root";
	public static final String mySQL_pass = "sujay";
	
	//TABLE DETAILS
	public static final String SCHEMA_NAME = "SUPPLIER_PORTAL";
	public static final String LOGIN_CREDENTIALS_TABLE = "LOGIN_CREDENTIALS";
	public static final String SUPPLIER_ONBOARDING_TABLE = "SUPPLIER_ONBOARDING";
	public static final String SUPPLIER_USER_MASTER_TABLE = "SUPPLIER_USER_MASTER";
	public static final String PROVISIONAL_CREDENTIALS_TABLE = "PROVISIONAL_CREDENTIALS";


	//MESSAGE DETAILS
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String TEMPLOGINFAIL_MESSAGE = "FAIL";
	public static final String EMAIL_SENDER = "corpsujaysurya@gmail.com";
	public static final String EMAIL_ERROR_MSG = "Error while Sending Mail";

}
