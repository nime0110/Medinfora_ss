package com.spring.app.mypage.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;

@Service
public class MypageService_imple implements MypageService {

	@Autowired
    private AES256 aES256;
	
	@Override
	public boolean updateinfo(Map<String, String> paraMap) {
		
		try {
			paraMap.put("mobile",aES256.encrypt(paraMap.get("mobile")));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println(paraMap.get("mobile"));
		
		return false;
	}
	
	
}
