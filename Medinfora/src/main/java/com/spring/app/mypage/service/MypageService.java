package com.spring.app.mypage.service;

import java.util.Map;

public interface MypageService {
	
	// 회원정보 변경
	boolean updateinfo(Map<String, String> paraMap);

}
