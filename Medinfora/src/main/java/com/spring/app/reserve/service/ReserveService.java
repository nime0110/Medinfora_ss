package com.spring.app.reserve.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.ReserveDTO;

public interface ReserveService {

	// 회원가입된 병원 개수
	int getmbHospitalCnt(Map<String, String> paraMap);

	// 회원가입된 병원 리스트 가져오기
	List<HospitalDTO> mbHospitalList(Map<String, String> paraMap);

	// 날짜가 공휴일인지 체크
	int holidayCheck(String day);

	// 병원과 요일 파악하여, 오늘(현재시간 이후) 진료예약 가능한 업무시간 파악하기
	List<HospitalDTO> todayReserveAvailable(Map<String, String> paraMap);

}
