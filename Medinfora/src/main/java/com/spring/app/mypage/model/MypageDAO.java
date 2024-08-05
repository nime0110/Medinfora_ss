package com.spring.app.mypage.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;

@Mapper
public interface MypageDAO {

	// (내정보수정) 내정보 수정하기 (통합)
	int updateinfo(Map<String, String> paraMap);

	// (비밀번호변경) 사용중인 비밀번호 확인하기
	String nowpwdCheck(Map<String, String> paraMap);

	// (비밀번호변경) 비밀번호 변경하기
	int updatepwd(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) 아이디를 통해 병원인덱스 값 찾기
	String Searchhospital(String userid);

	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명 검색)
	List<ReserveDTO> PatientNameList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료현황 검색)
	List<ReserveDTO> ReserveStatusList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색)
	List<ReserveDTO> ReserveDateList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명, 진료현황)
	List<ReserveDTO> reserveList(Map<String, String> paraMap);

	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명 검색 / 페이징 X)
	List<ReserveDTO> TotalPatientNameList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료현황 검색 / 페이징X)
	List<ReserveDTO> TotalReserveStatusList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색 / 페이징 X)
	List<ReserveDTO> TotalReserveDateList(Map<String, String> paraMap);
	
	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트의 개수(환자명, 진료현황)
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
	
	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(병원명 검색)
	List<ReserveDTO> HospitalNameList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료현황 검색)
	List<ReserveDTO> UserReserveStatusList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색)
	List<ReserveDTO> UserReserveDateList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(	병원명, 진료현황)
	List<ReserveDTO> UserreserveList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(병원명 검색 / 페이징 X)
	List<ReserveDTO> TotalReserveHospitalNameList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료현황 검색 / 페이징X)
	List<ReserveDTO> TotalUserReserveStatusList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색 / 페이징 X)
	List<ReserveDTO> TotalUserReserveDateList(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트의 개수(병원명, 진료현황)
	int UserreserveListCnt(Map<String, String> paraMap);

	// (일반회원- 진료예약 열람) 예약된 병원의 아이디 값을 가지고 이름과 전화번호 알아오기
	List<MemberDTO> GetHidxInfo(String hidx);

	// (일반회원- 진료예약 열람) ridx 를 통해 진료접수 취소하기
	int cancleRdto(String ridx);

	// (의료인- 진료 일정관리) hidx 의 예약리스트 가져오기
	List<ReserveDTO> TotalreserveList(String hidx);

	// (의료인- 진료 일정관리) 병원과 예약일시를 통해 환자아이디 가져오기
	ReserveDTO getPatientd(Map<String, String> paraMap);
	
	// (의료인- 진료 일정관리) userid 를 통해 환자 정보 가져오기
	MemberDTO getPatientInfo(String userid);

	// (검색통계) T0 데이터 가져오기
	int getT0data(Map<String, String> t0Map);

	// (검색통계) T1 데이터 가져오기
	List<Map<String, String>> getT1data(Map<String, String> paraMap);

	List<Map<String, String>> getmyslog(MemberDTO loginuser);

	// (마이페이지) 내가쓴글 리스트 가져오기
	List<CommuBoardDTO> getmyPostList(Map<String, String> paraMap);

	// (마이페이지) 내가쓴글 전체갯수
	int getmyPostTotalCount(Map<String, String> paraMap);

	// (마이페이지) 북마크 리스트 가져오기
	List<CommuBoardDTO> getmyBookmarkList(Map<String, String> paraMap);

	// (마이페이지) 북마크 전체개수
	int getBMListTotalCount(Map<String, String> paraMap);

	// (마이페이지) 내가 쓴 댓글 리스트 가져오기
	List<CommuCommentDTO> getmycommentList(Map<String, String> paraMap);

	// (마이페이지) 내가 쓴 댓글 전체갯수
	int getmycmtTotalCount(Map<String, String> paraMap);


	
}
