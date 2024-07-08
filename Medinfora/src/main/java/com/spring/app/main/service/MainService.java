package com.spring.app.main.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;

public interface MainService {

	// Service 테스트용 메소드
	public String test();
	
	// 병원API 입력용
	public int hpApiInputer(HospitalDTO hospitalDTO);
	
	// 대한민국 행정구역정보 입력용
	public int areaInputer(KoreaAreaVO koreaAreaVO);
	
	// 회원가입(중복체크)
	public MemberDTO isExistCheck(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 자동검색)
	public List<String> autoWord(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 병원리스트(전체개수))
	public int totalhospital(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 병원리스트(페이징))
	public List<HospitalDTO> hpSearch(Map<String, String> paraMap);
	
	// 회원가입(병원찾기 병원정보 입력)
	public HospitalDTO searchMedicalEnd(Map<String, String> paraMap);
	
	// 로그인 처리
	public MemberDTO loginEnd(Map<String, String> paraMap, HttpServletRequest request);

	// 로그아웃 처리
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request, String url);

	// 행정구역 리스트 추출
	public List<String> getcityinfo();

	/**
	 * 시/군/구 리스트 추출 Made By SDH
	 * @param {String} 행정구역(city) 이름
	 * @return {List<String>} 시/군/구 리스트 추출됨
	 */
	public List<String> getlocalinfo(String city);

	/**
	 * 읍/면/동 리스트 추출 Made By SDH
	 * @param {KoreaAreaVO} (inputareavo) 행정구역 / 시/군/구 정보가 있는 KOREAAREAVO
	 * @return {List<String>} 읍/면/동 리스트 추출됨
	 */
	public List<String> getcountryinfo(KoreaAreaVO inputareavo);
	
	// 병원 진료과 리스트 추출
	public List<ClasscodeDTO> getclasscode();

	// 공휴일 입력용
	public int holidayInputer(HolidayVO holidayVO);
	
	
	
	
	
	
	

	


	

	
	

	
}
