package com.spring.app.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping(value="/")
	public ModelAndView commom(ModelAndView mav) {
		
		mav.setViewName("redirect:index.bibo");
		
		return mav;
	}
	
	@RequestMapping(value="/index.bibo")
	public ModelAndView index(ModelAndView mav) {
		
		mav.setViewName("index.tiles");
		
		return mav;
	}
	
	// 로그인 창 띄우기
	@RequestMapping(value="/login.bibo")
	public ModelAndView login(ModelAndView mav) {
		
		mav.setViewName("login");
		
		return mav;
	}
	
	
	@ResponseBody
	@PostMapping("/loginEnd.bibo")
	public String loginEnd(HttpServletRequest request) {
		
		int n = 0;
		JSONObject jsonObj = new JSONObject();
		try {
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			
			System.out.println(userid);
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			
			
			n = service.loginEnd(paraMap);
			System.out.println("확인용 "+n);
			
			jsonObj.put("n", n);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return jsonObj.toString();
	}
	
	
	
}