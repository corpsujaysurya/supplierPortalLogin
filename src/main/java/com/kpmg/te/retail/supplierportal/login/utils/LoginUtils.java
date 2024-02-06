package com.kpmg.te.retail.supplierportal.login.utils;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class LoginUtils {
	
	public Integer generateRandRegNum() {
		Random rand = new Random(); 
		return rand.nextInt(9000000) + 1000000;
	}

}
