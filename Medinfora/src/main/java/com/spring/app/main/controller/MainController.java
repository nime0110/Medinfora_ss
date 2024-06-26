package com.spring.app.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.main.domain.MemberDTO;
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
	
	
	@PostMapping("/loginEnd.bibo")
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		
		try {
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			
			String clientip = request.getRemoteAddr();	// 클라이언트 IP 주소 알아옴
			
			// System.out.println(userid);
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			paraMap.put("clientip", clientip);

			mav = service.loginEnd(paraMap, mav, request);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mav;
	}
	
	
	
}