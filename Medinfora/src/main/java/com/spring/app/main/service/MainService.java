package com.spring.app.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

<<<<<<< HEAD
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
=======
import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
>>>>>>> parent of 5e6244a ([feat.sh] notice update)

public interface MainService {

	// Service 테스트용 메소드
	public String test();

	// 병원API 입력용
	public int hpApiInputer(HospitalDTO hospitalDTO);

	// 대한민국 행정구역정보 입력용
	public int areaInputer(KoreaAreaVO koreaAreaVO);

	// 회원가입(중복체크)
	public MemberDTO isExistCheck(Map<String, String> paraMap);

	// 로그인 처리
	public MemberDTO loginEnd(Map<String, String> paraMap, HttpServletRequest request);

	// 로그아웃 처리
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request, String url);
<<<<<<< HEAD

	// ================================= 공지사항 =====================================
	public int noticeWrite(NoticeDTO noticedto);

	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	public int getTotalCount(Map<String, String> paraMap);

	// 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것
	public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap);
=======
	
>>>>>>> parent of 5e6244a ([feat.sh] notice update)

	// 행정구역 리스트 추출
	public List<String> getcityinfo();

	/**
	 * 시/군/구 리스트 추출 Made By SDH
	 * 
	 * @param {String} 행정구역(city) 이름
	 * @return {List<String>} 시/군/구 리스트 추출됨
	 */
	public List<String> getlocalinfo(String city);

	/**
	 * 읍/면/동 리스트 추출 Made By SDH
	 * 
	 * @param {KoreaAreaVO} (inputareavo) 행정구역 / 시/군/구 정보가 있는 KOREAAREAVO
	 * @return {List<String>} 읍/면/동 리스트 추출됨
	 */
	public List<String> getcountryinfo(KoreaAreaVO inputareavo);

	// 병원 진료과 리스트 추출
	public List<ClasscodeDTO> getclasscode();


}
