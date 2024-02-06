package com.kpmg.te.retail.supplierportal.login.constants;

public class SQLConstants {
	
	public static final String myDriver = "com.mysql.cj.jdbc.Driver";
	public static final String myUrl = "jdbc:mysql://localhost/supplier_portal";

	public static final String mySQL_ID = "root";
	public static final String mySQL_pass = "sujay";

	public static final String all_scale_param_query = "SELECT param_name FROM generic_master_params";
	public static final String dist_scale_param_query = "SELECT param_name FROM generic_master_params where param_scale = 'dist'";
	public static final String sales_potential_param_query = "SELECT param_name FROM generic_master_params where param_scale = 'sales'";
	
	//public static final String countQuery = "SELECT COUNT(*) AS COUNT_NUMBER FROM genericfiltereddatatable";
	public static final String distributorIDListQuery = "select trader_id from genericfiltereddatatable;";
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String TEMPLOGINFAIL_MESSAGE = "FAIL";
	public static final String EMAIL_SENDER = "corpsujaysurya@gmail.com";
	public static final String EMAIL_ERROR_MSG = "Error while Sending Mail";

}
