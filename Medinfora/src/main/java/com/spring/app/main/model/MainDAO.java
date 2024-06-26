package com.spring.app.main.model;

import java.util.Map;

import com.spring.app.main.domain.MemberDTO;

public interface MainDAO {

	// DAO 테스트용 메소드
	public String daotest();


	// 로그인 유저 정보 가져오기
	public MemberDTO getLoginuser(Map<String, String> paraMap);

	// 회원코드 변경 (휴먼처리)
	public void updatemIdx(String userid, String idx);

	// 로그인 유저 ip 기록
	public void insert_log(Map<String, String> paraMap);
	
}
