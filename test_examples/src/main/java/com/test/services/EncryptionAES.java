package com.test.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.FlowVariables;
import com.test.utils.AESKey;
import com.test.utils.UtilHelper;

@Service
public class EncryptionAES {

	@Autowired
	UtilHelper util;
	
	private Logger log = LogManager.getLogger(DecryptionAES.class);
	
	public Map<String, Object> encrypt (FlowVariables flowVars) {
		
		String reffno_log = flowVars.getReffno();
		Map<String, Object> header = flowVars.getDataHeader();
		Map<String, Object> payload = flowVars.getDataParameter();
		
		// DECRYPT SECURITY AES
		String key = "Random1234567890";
		String iv = "RandomInitVector";
		
		Map<String, Object> DataMapping = new HashMap<String, Object>();
		try {
			String EncryptAes = AESKey.encrypt(reffno_log, key, iv, util.toJson(payload));
			log.info(reffno_log + " Encrypted Message : "+EncryptAes);
			
			DataMapping.put("data", EncryptAes);
			flowVars.setDataResponse(DataMapping);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
