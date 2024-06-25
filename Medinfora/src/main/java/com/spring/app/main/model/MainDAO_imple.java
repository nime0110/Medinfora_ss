package com.spring.app.main.model;

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

}
