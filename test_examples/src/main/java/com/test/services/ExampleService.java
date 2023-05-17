package com.test.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.FlowVariables;
import com.test.utils.UtilHelper;

@Service
public class ExampleService {
	
//	@Autowired
//	ExampleRepository db;
	
	@Autowired
	UtilHelper util;
	
	public Map<String, Object> exampleService(FlowVariables flowVars) {
		
		Map<String, Object> header = flowVars.getDataHeader();
		Map<String, Object> parameter = flowVars.getDataParameter();
		
		try {
//			int id = (int) parameter.get("id");
//			String name = (String) parameter.get("name");
//			int age = (int) parameter.get("age");
//			String job = (String) parameter.get("job");
//			
//			// get data list
//			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//			dataList = db.getDataList();
//			
//			// get data by id
//			Map<String, Object> data = new HashMap<String, Object>();
//			data = db.getDataById(id);
//			
//			// insert data from request
//			int statusInsert = db.insertData(id, name, age, job);
//			
//			// update data by name
//			int statusUpdate = db.updateDataJob(job, name);
//			
//			// set response data
//			flowVars.setDataResponse(data);
//			
//			
//			// get datetime
//			String format = "YYYY-MM-dd HH:mm:sss";
//			int dayChange = 0;
//			String datetime = util.getDateTimeFormat(format, dayChange);
//			
//			// get parsing QR
//			String qr_value = "00020101021240440014ID.PERMATA.WWW010893600013021012345678975204482953033605405105005802ID5906IVOYAS6007JAKARTA61051031062380804DMCT992600020001164ac57af5142a8a456304E686";
//			Map<String, Object> parsingQr = util.parseQR(qr_value);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
