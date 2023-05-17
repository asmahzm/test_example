package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.FlowVariables;
import com.test.services.DecryptionAES;
import com.test.services.EncryptionAES;
import com.test.services.ExampleService;
import com.test.services.GenerateQr;
import com.test.utils.UtilHelper;

@RestController
public class ExampleController {

	@Autowired
	UtilHelper util;
	
	@Autowired
	ExampleService service;
	
	@Autowired
	EncryptionAES encryptService;
	
	@Autowired
	DecryptionAES decryptService;
	
	@Autowired
	GenerateQr generateQrService;
	
	private Logger log = LogManager.getLogger(ExampleController.class);
	
	@PostMapping(path = "/example")
	public Map<String, Object> exampleController(HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, 
			@RequestBody Map<String, Object> parameter) {
		
		FlowVariables flowVars = new FlowVariables();
		
		try {
			flowVars.setDataHttpRequest(request);
			flowVars.setDataHeader(header);
			flowVars.setDataParameter(parameter);
			
			service.exampleService(flowVars);
			
			return flowVars.getDataResponse();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping(path = "/encryption")
	public Object EncryptAES (HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameter) {
		
		FlowVariables flowVars = new FlowVariables();
		
		String reffno_log = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
			String datetime = sdf.format(new Date());
			String random = util.getRandomStringInteger(5);
			reffno_log = datetime + random;
			
			log.info(reffno_log + " =============== START ENCRYPTION AES ===============");
			log.info(reffno_log + " Request Message -> "+parameter);
			
			// SET REQUEST
			flowVars.setReffno(reffno_log);
			flowVars.setDataHttpRequest(request);
			flowVars.setDataHeader(header);
			flowVars.setDataParameter(parameter);
			
			// HIT SERVICE
			encryptService.encrypt(flowVars);
			
			log.info(reffno_log + " Response Message -> "+flowVars.getDataResponse());
			log.info(reffno_log + " =============== END PROCESS ===============");
			
			return flowVars.getDataResponse();
			
			
		} catch (Exception e) {
			log.info(reffno_log + " Error Caused By : "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping(path = "/decryption")
	public Object DecryptAES (HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameter) {
		
		FlowVariables flowVars = new FlowVariables();
		
		String reffno_log = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
			String datetime = sdf.format(new Date());
			String random = util.getRandomStringInteger(5);
			reffno_log = datetime + random;
			
			log.info(reffno_log + " =============== START DECRYPTION AES ===============");
			log.info(reffno_log + " Request Message -> "+parameter);
			
			// SET REQUEST
			flowVars.setReffno(reffno_log);
			flowVars.setDataHttpRequest(request);
			flowVars.setDataHeader(header);
			flowVars.setDataParameter(parameter);
			
			// HIT SERVICE
			decryptService.decrypt(flowVars);
			
			log.info(reffno_log + " Response Message -> "+flowVars.getDataResponse());
			log.info(reffno_log + " =============== END PROCESS ===============");
			
			return flowVars.getDataResponse();
			
			
		} catch (Exception e) {
			log.info(reffno_log + " Error Caused By : "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping(path = "/generateQr")
	public Object GenerateQr (HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameter) {
		
		FlowVariables flowVars = new FlowVariables();
		
		String reffno_log = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
			String datetime = sdf.format(new Date());
			String random = util.getRandomStringInteger(5);
			reffno_log = datetime + random;
			
			log.info(reffno_log + " =============== START GENERATE QR ===============");
			log.info(reffno_log + " Request Message -> "+parameter);
			
			// SET REQUEST
			flowVars.setReffno(reffno_log);
			flowVars.setDataHttpRequest(request);
			flowVars.setDataHeader(header);
			flowVars.setDataParameter(parameter);
			
			// HIT SERVICE
			generateQrService.generateQr(flowVars);
			
			log.info(reffno_log + " Response Message -> "+flowVars.getDataResponse());
			log.info(reffno_log + " =============== END PROCESS ===============");
			
			return flowVars.getDataResponse();
			
			
		} catch (Exception e) {
			log.info(reffno_log + " Error Caused By : "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
