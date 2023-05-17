package com.test.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.utils.AESKey;
import com.test.utils.UtilHelper;
import com.test.model.FlowVariables;

@Service
public class DecryptionAES {

	@Autowired
	UtilHelper util;
	
	private Logger log = LogManager.getLogger(DecryptionAES.class);
	
	public Map<String, Object> decrypt (FlowVariables flowVars) {
		
		String reffno_log = flowVars.getReffno();
		Map<String, Object> header = flowVars.getDataHeader();
		Map<String, Object> payload = flowVars.getDataParameter();
		
		// DECRYPT SECURITY AES
		String key = "Random1234567890";
		String iv = "RandomInitVector";
		
		String DecryptAes = "";
		JSONParser parser = new JSONParser();
		JSONObject reqMessage = new JSONObject();
		Map<String, Object> DataMapping = new HashMap<String, Object>();
		try {
			DecryptAes = AESKey.decrypt(reffno_log, key, iv, payload.get("msg").toString());
			log.info(reffno_log + " Decrypted Message : "+DecryptAes.trim().replace("\n", ""));
			
			reqMessage = (JSONObject) parser.parse(DecryptAes);
			DataMapping = util.toMap(reqMessage);
			
			flowVars.setDataResponse(DataMapping);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
