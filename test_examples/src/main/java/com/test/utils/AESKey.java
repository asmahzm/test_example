package com.test.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AESKey {

	private Logger log = LogManager.getLogger(AESKey.class);
	
//	private static String initVector = "RandomInitVector";
//	private static String key = "Random1234567890";
	
	public static String encrypt(String reffno, String key, String initVector, String value) throws Exception {
		 
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		
		byte[] encrypted = cipher.doFinal(value.getBytes());
		String message = Base64.encodeBase64String(encrypted);
//		log.info(reffno + " Encrypted Message : "+message);
		
		return message;
    }

	public static String decrypt(String reffno, String key, String initVector, String value) throws Exception {
		 
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decodeBase64(value));
        String message = new String(original);
//        log.info(reffno + " Decrypted Message : "+"\n"+message);
//        log.info(reffno + " Decrypted Message : "+message.trim().replace("\n", ""));
        
        return message;
    }
}
