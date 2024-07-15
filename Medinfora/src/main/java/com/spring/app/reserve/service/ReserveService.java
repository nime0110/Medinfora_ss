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

	// 병원의 오픈시간과 마감시간 파악
	HospitalDTO hospitalTime(String hidx);

	// 선택한 날의 예약 개수 파악
	int reserveCnt(Map<String, String> paraMap);
	
	// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
	HospitalDTO dayReserveImpossible(Map<String, String> paraMap);

	// 예약접수
	int insertReserve(Map<String, String> paraMap);

}
