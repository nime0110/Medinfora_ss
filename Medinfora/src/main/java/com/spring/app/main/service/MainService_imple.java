package com.spring.app.main.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.main.model.MainDAO;

@Service
public class MainService_imple implements MainService {

	@Autowired
	private MainDAO dao;

	@Override
	public String test() {
		return dao.daotest();
	}
	
	
	// 로그인 처리
	@Override
	public int loginEnd(Map<String, String> paraMap) {
		int n = dao.loginEnd(paraMap);
		return n;
	}
	
	
}
