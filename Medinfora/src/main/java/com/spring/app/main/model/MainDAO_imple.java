package com.spring.app.main.model;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAO_imple implements MainDAO{

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override // 테스트용
	public String daotest() {
		return "pass";
	}
	
	
	// 로그인 처리
	@Override
	public int loginEnd(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("mediinfora.loginEnd", paraMap);
		return n;
	}

}
