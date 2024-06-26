package com.spring.app.main.model;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.main.domain.MemberDTO;

@Repository
public class MainDAO_imple implements MainDAO{

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override // 테스트용
	public String daotest() {
		return "pass";
	}
	
	
	// 로그인 유저 정보
	@Override
	public MemberDTO getLoginuser(Map<String, String> paraMap) {
		MemberDTO loginuser = sqlsession.selectOne("mediinfora.getLoginuser", paraMap);
		return loginuser;
	}
	
	
	// 회원코드 변경 (휴먼처리)
	@Override
	public void updatemIdx(String userid, String idx) {
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("idx", idx);
		
		sqlsession.update("mediinfora.updatemIdx", paraMap);
	}

	// 로그인 유저 ip 기록
	@Override
	public void insert_log(Map<String, String> paraMap) {
		sqlsession.insert("mediinfora.insert_log", paraMap);
		
	}
	
	
	

}
