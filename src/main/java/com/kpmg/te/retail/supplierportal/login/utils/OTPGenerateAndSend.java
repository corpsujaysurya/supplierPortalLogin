package com.kpmg.te.retail.supplierportal.login.utils;

public class OTPGenerateAndSend {
	
	    public String generateOTP()  { 
	        int randomPin   =(int) (Math.random()*9000)+1000; 
	        String otp  = String.valueOf(randomPin); 
	        return otp; 
	    } 
	    
}
