package com.spring.app.main.service;

import java.util.Map;

public interface MainService {

	// Service 테스트용 메소드
	public String test();
	
	
	// 로그인 처리
	public int loginEnd(Map<String, String> paraMap);
	
}
