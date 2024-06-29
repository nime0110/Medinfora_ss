package com.spring.app.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.KoreaAreaVO;
import com.spring.app.main.domain.MemberDTO;

public interface MainService {

	// Service 테스트용 메소드
	public String test();
	
	// 병원API 입력용
	public int hpApiInputer(HospitalDTO hospitalDTO);
	
	// 대한민국 행정구역정보 입력용
	public int areaInputer(KoreaAreaVO koreaAreaVO);
	
	// 로그인 처리
	public MemberDTO loginEnd(Map<String, String> paraMap, HttpServletRequest request);

	// 로그아웃 처리
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request, String url);

	
	

	
}
