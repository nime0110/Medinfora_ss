package com.spring.app.reserve.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.HospitalDTO;

@Mapper
public interface ReserveDAO {

	// 회원가입된 병원 개수
	int getTotalCnt(Map<String, String> paraMap);
	
	// 회원가입된 병원 리스트 가져오기 (클라스코드있음)
	int getTotalCntClass(Map<String, String> paraMap);

	// 회원가입된 병원 리스트 가져오기
	List<HospitalDTO> mbHospitalList(Map<String, String> paraMap);
	
	// 회원가입된 병원 리스트 가져오기 (클라스코드있음)
	List<HospitalDTO> mbHospitalListClass(Map<String, String> paraMap);

	// 날짜가 공휴일인지 체크
	int holidayCheck(String day);

	// 병원의 오픈시간과 마감시간 파악
	HospitalDTO hospitalTime(String hidx);

	// 선택한 날의 예약 개수 파악
	int reserveCnt(Map<String, String> paraMap);
	
	// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
	HospitalDTO dayReserveImpossible(Map<String, String> paraMap);


}
