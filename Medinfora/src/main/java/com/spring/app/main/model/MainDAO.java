package com.spring.app.main.model;

import java.util.Map;

<<<<<<< HEAD
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;
=======
import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
>>>>>>> parent of 5e6244a ([feat.sh] notice update)

public interface MainDAO {

	// DAO 테스트용 메소드
	public String daotest();

	// 병원정보 API 입력용 메소드
	public int hpApiInputer(HospitalDTO hospitalDTO);

	// 대한민국 행정구역정보 입력용
	public int areaInputer(KoreaAreaVO koreaAreaVO);

	// 회원가입(중복체크)
	public MemberDTO isExistCheck(Map<String, String> paraMap);

	// 로그인 유저 정보 가져오기
	public MemberDTO getLoginuser(Map<String, String> paraMap);

	// 회원코드 변경 (휴먼처리)
	public void updatemIdx(String userid, String idx);

	// 로그인 유저 ip 기록
	public void insert_log(Map<String, String> paraMap);

<<<<<<< HEAD
	// 행정구역 리스트 추출
	public List<String> getcityinfo();

	// 시/군/구 리스트 추출
	public List<String> getlocalinfo(String city);

	// 읍/면/동 리스트 추출
	public List<String> getcountryinfo(KoreaAreaVO inputareavo);

	// 병원 진료과 리스트 추출
	public List<ClasscodeDTO> getclasscode();

=======
	
	
>>>>>>> parent of 5e6244a ([feat.sh] notice update)
}
