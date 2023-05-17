package com.test.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UtilHelper {

	public String getDateTimeFormat(String format, int dayChange) {
		
		SimpleDateFormat sdf =  new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, dayChange);
		String datetime = sdf.format(cal.getTime());
		return datetime;
	}
	
	public String getRandomStringInteger(int many) throws Exception {
		
        String RANDOMCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder random_text = new StringBuilder();
        Random rnd = new Random();
        while (random_text.length() < many) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOMCHARS.length());
            random_text.append(RANDOMCHARS.charAt(index));
        }
        String randStr = random_text.toString();
        return randStr;
    }
	
	public String getRandomInteger(int many) throws Exception {
		
        String RANDOMCHARS = "1234567890";
        StringBuilder random_text = new StringBuilder();
        Random rnd = new Random();
        while (random_text.length() < many) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOMCHARS.length());
            random_text.append(RANDOMCHARS.charAt(index));
        }
        String randStr = random_text.toString();
        return randStr;
    }
	
	public List<HashMap<String, Object>> stringJsonToArray(String json) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> datalistmap = new ArrayList<HashMap<String,Object>>();
		datalistmap = mapper.readValue(json, new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		return datalistmap;
	}
	
	public HashMap<String, Object> stringJsonToMap(String json) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> datahashmap = new HashMap<String, Object>();
		datahashmap = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
		return datahashmap;
	}
	
	public Map<String, Object> stringMapToMap(String map) throws Exception {

		HashMap<String, Object> datahashmap = new HashMap<String, Object>();
		String[] pairs = map.split(", ");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");
			datahashmap.put(keyValue[0], keyValue[1]);
		}
		return datahashmap;
	}
	
	public byte[] serialize(Object data) throws IOException {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	    byte[] responseByte = ow.writeValueAsBytes(data);
	    return responseByte;
	}
	
	public String toJson(Object data) throws IOException {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String JSONmsg = ow.writeValueAsString(data);
		JSONmsg = JSONmsg.replace("\r\n", "");
	    return JSONmsg;
	}
	
	public Map<String, Object> toMap(JSONObject data) throws IOException {
		
		Map<String, Object> DataMapping = 
				new ObjectMapper().readValue(data.toString(), new TypeReference<Map<String, Object>>() {});
		return DataMapping;
	}
		
	public String getLengthAndValue(String word){
		
		String a = word;
		Integer b = a.length();
		String c = b.toString();
		String d = StringUtils.leftPad(c, 2, "0");
		String emp = d + a ;
		return emp;
	}

	public String crc16(String data){
		int crc = 0xFFFF;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12) 
        String result = "";
        
        // byte[] testBytes = "123456789".getBytes("ASCII");
        byte[] bytes = data.getBytes();

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }

        crc &= 0xffff;
        result = StringUtils.leftPad(Integer.toHexString(crc),4,"0");
        
        return result.toUpperCase();
	}
	
	public Map<String, Object> parseQR(String qr_value) {
		
		Map<String, Object> all_tag_value = new HashMap<String, Object>();
		Map<String, Object> all_subtag_value = new HashMap<String, Object>();
		
		try {
			int tag_start = 0;
			int tag_length = 0;
			int qr_value_length = qr_value.length();
			
			for (int i = 0; i < 100; i++) {
				if (tag_start == qr_value_length) {
					break;
				}
				String tag = qr_value.substring(tag_start, tag_start+2);
					tag_length = Integer.parseInt(qr_value.substring(tag_start+2, tag_start+4));
				String tag_value = qr_value.substring(tag_start+4, tag_start+4+tag_length);
				
				System.out.println("PARSE -> " + "tag = "+tag + "length = "+tag_length + "value = "+tag_value);
				
				tag_start = tag_start+4+tag_length;
				
				if (tag.equals("40")) {
					
					int subtag_start = 0;
					int subtag_length = 0;
					int tag_value_length = tag_value.length();
					
					for (int j = 0; j < 10; j++) {
						if (subtag_start == tag_value_length) {
							break;
						}
						String subtag = qr_value.substring(subtag_start, subtag_start+2);
							subtag_length = Integer.parseInt(qr_value.substring(subtag_start+2, subtag_start+4));
						String subtag_value = qr_value.substring(subtag_start+4, subtag_start+4+subtag_length);
						
						System.out.println("PARSE -> " + "subtag = "+subtag + "length = "+subtag_length + "value = "+subtag_value);
						
						subtag_start = subtag_start+4+subtag_length;
						
						all_subtag_value.put("tag"+tag+"_subtag"+subtag, subtag_value);
					}
				}
				
				all_tag_value.put("tag"+tag, tag_value);
				all_tag_value.putAll(all_subtag_value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return all_tag_value;
	}
}
