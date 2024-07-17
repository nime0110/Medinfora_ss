package com.spring.app.main.model;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;

public interface MainDAO {

	// DAO 테스트용 메소드
	public String daotest();
	
	// 병원정보 API 입력용 메소드 
	public int hpApiInputer(HospitalDTO hospitalDTO);

	// 대한민국 행정구역정보 입력용
	public int areaInputer(KoreaAreaVO koreaAreaVO);

	// 회원가입(중복체크)
	public MemberDTO isExistCheck(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 자동검색)
	public List<String> autoWord(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 검색)
	public List<HospitalDTO> hpSearch(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 병원정보 입력)
	public HospitalDTO searchMedicalEnd(Map<String, String> paraMap);
	
	// 회원가입하기
	public int registerEnd(Map<String, String> paraMap);

	// 로그인 유저 정보 가져오기
	public MemberDTO getLoginuser(Map<String, String> paraMap);

	// 회원코드 변경 (휴먼처리)
	public void updatemIdx(String userid, String idx);

	// 로그인 유저 ip 기록
	public void insert_log(Map<String, String> paraMap);

	// 행정구역 리스트 추출
	public List<String> getcityinfo();

	// 시/군/구 리스트 추출
	public List<String> getlocalinfo(String city);

	// 읍/면/동 리스트 추출
	public List<String> getcountryinfo(KoreaAreaVO inputareavo);

	// 병원 진료과 리스트 추출
	public List<ClasscodeDTO> getclasscode();
	
	// 회원가입(병원찾기 병원리스트(전체개수))
	public int totalhospital(Map<String, String> paraMap);

	// 공휴일 입력용
	public int holidayInputer(HolidayVO holidayVO);

	// 의료 CLASSCODE 리스트 추출
	public List<String> getclassCodeList(Map<String, String> paraMap);

	// CLASSMET 입력용
	public boolean classcodeMetInput(Map<String, String> inputparaMap);

	// 인덱스 화면 공지 불러오기
	public List<NoticeDTO> getIdxNdtoList();

	// 병원 중복가입 체크
	public boolean checkhidx(String hidx);

	// (검색) 병원 리스트 검색
	public List<HospitalDTO> gethdtolist(String search);

	// (검색) 질문 리스트 검색
	public List<MediQDTO> getmqList(String search);

	// (검색) 답변 리스트 검색
	public List<MediQDTO> getmaList(String search);

	// (검색) 공지 리스트 검색
	public List<NoticeDTO> getndtoList(String search);

	// (검색) 검색 로그 작성하기
	public void writeSearchlog(Map<String, String> paraMap);

	// (검색) 가입된 병원회원 여부 검사
	public boolean isMediMember(int hidx);

	// 인덱스 인기 검색어 불러오기
	public List<String> getPopwordList();

	
	
}
