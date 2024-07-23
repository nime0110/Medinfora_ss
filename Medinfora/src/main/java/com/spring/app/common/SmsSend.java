package com.spring.app.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.nurigo.java_sdk.api.Message;

@Controller
public class SmsSend {

	@ResponseBody
	@GetMapping(value="/smsSend.bibo", produces="text/plain;charset=UTF-8")
	public String smsSend(HttpServletRequest request) throws Exception {
		
		String api_key = "NCSWDM8RGFFOBXLX";
		String api_secret = "BZTOGVAAFKLZRSUJHGM5WNQCWJIABWXP";
		
		Message coolsms = new Message(api_key, api_secret);
		
		String mobile = request.getParameter("mobile");
	    String smsContent = request.getParameter("smsContent");
	      
	    // == 4개 파라미터(to, from, type, text)는 필수사항 == //
	    HashMap<String, String> paraMap = new HashMap<>();
	    paraMap.put("to", mobile); // 수신번호
	    paraMap.put("from", "01034392566"); // 발신번호
	    paraMap.put("type", "SMS"); // Message type ( SMS(단문), LMS(장문), MMS, ATA )
	    paraMap.put("text", smsContent); // 문자내용  
	    
	    String datetime = request.getParameter("datetime");
	    if(datetime != null) {
	    	paraMap.put("datetime", datetime); // 예약일자및시간
	    }

	    paraMap.put("app_version", "JAVA SDK v2.2"); // application name and version 
	    paraMap.put("mode", "test");	// 테스트 용도
	    
	    JSONObject jsobj = (JSONObject) coolsms.send(paraMap);
	    
		return jsobj.toString();
	}
	
}
