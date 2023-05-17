package com.test.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.FlowVariables;
import com.test.utils.UtilHelper;

@Service
public class GenerateQr {

	@Autowired
	UtilHelper util;
	
	private Logger log = LogManager.getLogger(DecryptionAES.class);
	
	public Map<String, Object> generateQr (FlowVariables flowVars) {
		
		String reffno_log = flowVars.getReffno();
		Map<String, Object> header = flowVars.getDataHeader();
		Map<String, Object> payload = flowVars.getDataParameter();
		
		try {
			String sof = payload.get("source_credit").toString();
			String sof_type = payload.get("source_credit_type").toString();
			String domain = payload.get("reverse_domain").toString().toUpperCase();
			String nns_code = payload.get("beneficiary_nns_code").toString();
			
			String channel_user_id = payload.get("channel_user_id") == null ? "" : payload.get("channel_user_id").toString();	//hanya di by user
			channel_user_id = channel_user_id.substring(channel_user_id.length()-4) + util.getRandomInteger(8);
			String mid = payload.get("mid") == null ? "" : payload.get("mid").toString();										//hanya di by merchant
			
			String criteria = payload.get("merch_criteria") == null ? "" : payload.get("merch_criteria").toString();			//hanya di by merchant NEW UPDATE!
			String bic = payload.get("bank_identifier_code") == null ? "" : payload.get("bank_identifier_code").toString();		//hanya di by user NEW UPDATE!
			
			String global_domain = payload.get("global_reverse_domain") == null ? "" : payload.get("global_reverse_domain").toString();	//hanya di by merchant
			String nmid = payload.get("national_mid") == null ? "" : payload.get("national_mid").toString();							//hanya di by merchant
			
			String mcc = payload.get("merch_category_code").toString();
			String currency = payload.get("curr_code").toString();
			String amount = payload.get("amount") == null ? "" : payload.get("amount").toString();
			String country_code = payload.get("country_code").toString();
			String name = payload.get("beneficiary_name").toString().toUpperCase();
			String city = payload.get("beneficiary_city").toString().toUpperCase();
			String postal_code = payload.get("post_code").toString();
			String terminal_label = payload.get("terminal_label") == null ? "" : payload.get("terminal_label").toString();
			String purpose_trx = "CDPT";
			
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			long timestamp = ts.getTime();
			String qr_id = String.valueOf(timestamp);
			
			// GENERATE QR PROCESS
			String qr_value = "";
			String value = "";
			
			String rnd = util.getDateTimeFormat("ddHHmmsss", 0);
			String pan = nns_code + sof_type + rnd;
			
			String tag00 = "00" + util.getLengthAndValue("01");								// payload format indicator(M)
			String tag01 = "01" + util.getLengthAndValue("12");								// point of initiation method(M)
			String tag40_subtag00 = "00" + util.getLengthAndValue(domain);					// reverse domain(M)
			String tag40_subtag01 = "01" + util.getLengthAndValue(pan);						// customer PAN(M)
			String tag40_subtag02 = "02" + util.getLengthAndValue(channel_user_id);			// customer ID(M)
			String tag40_subtag04 = "";
			if (!bic.isEmpty()) {
				tag40_subtag04 = "04" + util.getLengthAndValue(bic);						// bank_identifier_code(O) NEW UPDATE!
			}
			String tag40 = "40" + util.getLengthAndValue(tag40_subtag00 + tag40_subtag01 + tag40_subtag02 + tag40_subtag04); // sender account information(M)
			String tag52 = "52" + util.getLengthAndValue(mcc);								// merchant category code(M) -> 4829
			String tag53 = "53" + util.getLengthAndValue(currency);							// transaction currency(M) -> 360
			String tag54 = "54" + util.getLengthAndValue(amount);							// transaction amount(C)
			String tag58 = "58" + util.getLengthAndValue(country_code);						// country code(M) -> ID
			String tag59 = "59" + util.getLengthAndValue(name);								// beneficiary / merchant name(M)
			String tag60 = "60" + util.getLengthAndValue(city);								// beneficiary / merchant city(M)
			String tag61 = "61" + util.getLengthAndValue(postal_code);						// postal code(M)
			String tag62_subtag07 = "";
			if (!terminal_label.isEmpty()) {
				tag62_subtag07 = "07" + util.getLengthAndValue(terminal_label);				// terminal label(O)
			}
			String tag62_subtag08 = "08" + util.getLengthAndValue(purpose_trx);				// purpose of transaction(M) -> CDPT
			String tag62_subtag99_subsubtag00 = "00" + util.getLengthAndValue("00");		// default value(M)
			String tag62_subtag99_subsubtag01 = "01" + util.getLengthAndValue(qr_id);		// unique data(M)
			String tag62_subtag99 = "99" + util.getLengthAndValue(tag62_subtag99_subsubtag00 + tag62_subtag99_subsubtag01);	//unique per generated(M)
			String tag62 = "62" + util.getLengthAndValue(tag62_subtag07 + tag62_subtag08 + tag62_subtag99); // additional data field template(M)
			String tag63 = "6304";															// CRC(M)
			
			value = tag00 + tag01 + tag40 + tag52 + tag53 + tag54 + tag58 + tag59 + tag60 + tag61 + tag62 + tag63;
			qr_value = value + util.crc16(value);
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("qr_id", qr_id);
			response.put("qr_value", qr_value);
			
			flowVars.setDataResponse(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
