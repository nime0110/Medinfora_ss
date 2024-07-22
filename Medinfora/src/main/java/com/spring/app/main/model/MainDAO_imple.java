package com.spring.app.main.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;

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
	
	// 회원가입(병원찾기 병원정보 입력)
	@Override
	public HospitalDTO searchMedicalEnd(Map<String, String> paraMap) {
		HospitalDTO hpdto = sqlsession.selectOne("medinfora.searchMedicalEnd", paraMap);
		return hpdto;
	}
	
	// 회원가입하기
	@Override
	public int registerEnd(Map<String, String> paraMap) {
		int n = sqlsession.insert("medinfora.registerEnd", paraMap);
		return n;
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

	// 공휴일 입력용
	@Override
	public int holidayInputer(HolidayVO holidayVO) {
		return sqlsession.insert("medinfora.holidayInputer",holidayVO);
	}
	
	// 의료 ClassCode 리스트 추출
	@Override
	public List<String> getclassCodeList(Map<String, String> paraMap) {
		System.out.println(paraMap.get("hpname"));
		System.out.println(paraMap.get("hpaddr"));
		return sqlsession.selectList("medinfora.getclassCodeList", paraMap);
	}

	// CLASSMET 입력용
	@Override
	public boolean classcodeMetInput(Map<String, String> inputparaMap) {
		
		boolean result = false;
		
		if(1==sqlsession.insert("medinfora.classcodeMetInput", inputparaMap)) {
			result = true;
		}
		
		return result;
	}

	// 인덱스 화면 공지 불러오기
	@Override
	public List<NoticeDTO> getIdxNdtoList() {
		return sqlsession.selectList("medinfora.getIdxNdtoList");
	}

	// 병원 중복가입 체크
	@Override
	public boolean checkhidx(String hidx) {
		
		boolean result = true;
		
		String userid = sqlsession.selectOne("medinfora.checkhidx",hidx);
		
		if(userid != null) {
			result = false;
		}
		
		return result;
	}

	// (검색) 답변 리스트 검색
	@Override
	public List<MediQDTO> getmqList(String search) {
		
		return sqlsession.selectList("medinfora.getmqList",search);
	}

	// (검색) 질문 리스트 검색
	@Override
	public List<MediQDTO> getmaList(String search) {

		return sqlsession.selectList("medinfora.getmaList",search);
	}

	// (검색) 공지 리스트 검색
	@Override
	public List<NoticeDTO> getndtoList(String search) {
		return sqlsession.selectList("medinfora.getndtoList",search);
	}

	// (검색) 검색 로그 작성하기
	@Override
	public void writeSearchlog(Map<String, String> paraMap) {
		sqlsession.insert("medinfora.writeSearchlog", paraMap);
	}

	// (검색) 가입된 병원회원 여부 검사
	@Override
	public boolean isMediMember(int hidx) {
		
		boolean result = false;
		
		if(sqlsession.selectList("medinfora.isMediMember",hidx).size()>0) {
			result = true;
		}
		
		return result;
	}

	// 인덱스 인기 검색어 불러오기
	@Override
	public List<String> getPopwordList() {
		return sqlsession.selectList("medinfora.getPopwordList");
	}

	// (검색) 회원 병원 리스트 선 검색
	@Override
	public List<HospitalDTO> gethdtoOurlist(String search) {
		return sqlsession.selectList("medinfora.gethdtoOurlist",search);
	}

	// (검색) 병원 검색
	@Override
	public HospitalDTO gethdto(Map<String, String> paraMap) {
		return sqlsession.selectOne("medinfora.gethdto",paraMap);
	}
	
	// (검색) hidx 가져오기
	@Override
	public int gethidx(Map<String, String> paraMap) {
		return sqlsession.selectOne("medinfora.gethidx",paraMap);
	}

	// (검색) 총 병원 검색수
	@Override
	public int hcnt(String search) {
		return sqlsession.selectOne("medinfora.hcnt",search);
	}


	

}
