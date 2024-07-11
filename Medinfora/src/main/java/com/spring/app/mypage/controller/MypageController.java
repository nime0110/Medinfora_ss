package com.spring.app.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.mypage.service.MypageService;

@Controller
@RequestMapping(value="/mypage/")
public class MypageController {
	
	@Autowired
	private MypageService service;

	@GetMapping("myinfo.bibo")
	public ModelAndView isLogin_myinfo(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		mav.setViewName("mypage/myinfo.info");
		
		return mav;
	}
	
	@PostMapping("updatemember.bibo")
	public ModelAndView isLogin_updatemember(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("userid",request.getParameter("userid"));
		paraMap.put("mobile",request.getParameter("mobile"));
		paraMap.put("address",request.getParameter("address"));
		paraMap.put("detailaddress",request.getParameter("detailaddress"));
		
		if(service.updateinfo(paraMap)) {
			
		}
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	@GetMapping("myreserve.bibo")
	public ModelAndView isLogin_myreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/myreserve.info");
		return mav;
	}
	
}
