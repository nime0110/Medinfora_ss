package com.spring.app.main.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;

@Repository
public class MainDAO_imple implements MainDAO{

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override // 테스트용
	public String daotest() {
		return "pass";
	}

	// 회원가입(중복체크)
	@Override
	public MemberDTO isExistCheck(Map<String, String> paraMap) {
		MemberDTO isExist = sqlsession.selectOne("medinfora.isExistCheck", paraMap);
		return isExist;
	}
	
	// 회원가입(병원찾기 자동검색)
	@Override
	public List<String> autoWord(Map<String, String> paraMap) {
		List<String> autoWordList = sqlsession.selectList("medinfora.autoWord", paraMap);
		return autoWordList;
	}
	
	
	// 회원가입(병원찾기 병원리스트(전체개수))
	@Override
	public int totalhospital(Map<String, String> paraMap) {
		int totalCount = sqlsession.selectOne("medinfora.totalhospital", paraMap);
		return totalCount;
	}
	
	// 회원가입(병원찾기 병원검색리스트)
	@Override
	public List<HospitalDTO> hpSearch(Map<String, String> paraMap) {
		List<HospitalDTO> hpList = sqlsession.selectList("medinfora.hpSearch", paraMap);
		return hpList;
	}
	
	// 로그인 유저 정보
	@Override
	public MemberDTO getLoginuser(Map<String, String> paraMap) {
		MemberDTO loginuser = sqlsession.selectOne("medinfora.getLoginuser", paraMap);
		return loginuser;
	}
	
	
	// 회원코드 변경 (휴먼처리)
	@Override
	public void updatemIdx(String userid, String idx) {
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("medinfora.userid", userid);
		paraMap.put("medinfora.idx", idx);
		
		sqlsession.update("medinfora.updatemIdx", paraMap);
	}

	// 로그인 유저 ip 기록
	@Override
	public void insert_log(Map<String, String> paraMap) {
		sqlsession.insert("medinfora.insert_log", paraMap);
		
	}

	// 병원정보 API 입력용 메소드
	@Override
	public int hpApiInputer(HospitalDTO hospitalDTO) {
		return sqlsession.insert("medinfora.hpApiInputer",hospitalDTO);
	}

	// 대한민국 행정구역정보 입력용
	@Override
	public int areaInputer(KoreaAreaVO koreaAreaVO) {
		return sqlsession.insert("medinfora.areaInputer",koreaAreaVO);
	}

	// 행정구역 리스트 추출
	@Override
	public List<String> getcityinfo() {
		
		List<String> getcityinfo = sqlsession.selectList("medinfora.getcityinfo");
		
		return getcityinfo;
	}

	// 시/군/구 리스트 추출
	@Override
	public List<String> getlocalinfo(String city) {
		
		return sqlsession.selectList("medinfora.getlocalinfo",city);
	}
	
	// 읍/면/동 리스트 추출
	@Override
	public List<String> getcountryinfo(KoreaAreaVO inputareavo) {
		return sqlsession.selectList("medinfora.getcountryinfo",inputareavo);
	}

	// 병원 진료과 리스트 추출
	@Override
	public List<ClasscodeDTO> getclasscode() {
		return sqlsession.selectList("medinfora.getclasscode");
	}

	
	
	

	
	
}
