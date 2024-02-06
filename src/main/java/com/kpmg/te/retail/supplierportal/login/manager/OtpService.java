package com.kpmg.te.retail.supplierportal.login.manager;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpService {
	
	public int generateOTP(){
		 Random rnd = new Random();
		   return  rnd.nextInt(999999);
		 }
		
		
}
