package com.spring.app.main.model;

import java.util.Map;

public interface MainDAO {

	// DAO 테스트용 메소드
	public String daotest();
	
	// 로그인 처리
	public int loginEnd(Map<String, String> paraMap);
	
}
