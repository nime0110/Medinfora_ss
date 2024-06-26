package com.spring.app.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface MainService {

	// Service 테스트용 메소드
	public String test();
	
	
	// 로그인 처리
	public ModelAndView loginEnd(Map<String, String> paraMap, ModelAndView mav, HttpServletRequest request);
	
}
