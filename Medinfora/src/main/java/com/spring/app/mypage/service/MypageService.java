package com.spring.app.mypage.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;

public interface MypageService {
	
	// 회원정보 변경
	boolean updateinfo(Map<String, String> paraMap);

	// (의료인- 진료예약 열람) 아이디를 통해 병원인덱스 값 찾기
	String Searchhospital(String userid);

	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기
	List<ReserveDTO> reserveList(Map<String, String> paraMap);

	// (의료인- 진료예약 열람) 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
	List<MemberDTO> GetPatientInfo(String patient_id);

}
