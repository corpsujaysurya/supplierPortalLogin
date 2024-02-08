package com.kpmg.te.retail.supplierportal.login.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
@Component
public class LoginUtils {
	
	public Integer generateRandRegNum() {
		Random rand = new Random(); 
		return rand.nextInt(9000000) + 1000000;
	}
	
	public String randomPwdGenerate() {
		char[] possibleCharacters = (new String(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?"))
				.toCharArray();
		String randomStr = RandomStringUtils.random(10, 0, possibleCharacters.length - 1, false, false,
				possibleCharacters, new SecureRandom());
		return randomStr;
	}


}
