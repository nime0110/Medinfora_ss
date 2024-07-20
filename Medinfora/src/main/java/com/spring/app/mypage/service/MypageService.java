package com.spring.app.mypage.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;

public interface MypageService {
	
	// 회원정보 변경
	boolean updateinfo(Map<String, String> paraMap);

	// (비밀번호변경) 현 비밀번호 확인
	boolean nowpwdCheck(Map<String, String> paraMap);

	// (비밀번호변경) 비밀번호 변경하기
	int updatepwd(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) 아이디를 통해 병원인덱스 값 찾기
	String Searchhospital(String userid);

	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기 (검색포함)
	List<ReserveDTO> reserveList(Map<String, String> paraMap);

	// (의료인- 진료예약 열람) 리스트 총 결과 개수
	int reserveListCnt(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
	List<MemberDTO> GetPatientInfo(String patient_id);
	
	// (의료인- 진료예약 열람) ridx 를 통해 예약 정보 가져오기
	ReserveDTO getRdto(String ridx);

	// (의료인- 진료예약 열람) 선택한 진료현황의 예약코드 가져오기
	String GetRcode(String rStatus);

	// (의료인- 진료예약 열람) 진료현황 변경해주기
	int ChangeRstatus(Map<String, String> paraMap);

	// (의료인- 진료예약 열람) 진료완료로 변경하기
	void updatercode(String ridx);
	
	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(검색포함)
	List<ReserveDTO> UserReserveList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) 리스트 총 결과 개수
	int UserReserveListCnt(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) 예약된 병원의 아이디 값을 가지고 이름과 전화번호 알아오기
	List<MemberDTO> GetHidxInfo(String hidx);

	// (일반회원- 진료예약 열람) ridx 를 통해 진료접수 취소하기
	int cancleRdto(String ridx);

	// (의료인- 진료 일정관리) hidx 의 예약리스트 가져오기
	List<ReserveDTO> TotalreserveList(String hidx);

}
