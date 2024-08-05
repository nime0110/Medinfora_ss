package com.spring.app.mypage.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;
import com.spring.app.common.Myutil;
import com.spring.app.common.Sha256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;
import com.spring.app.mypage.model.MypageDAO;

@Service
public class MypageService_imple implements MypageService {

	@Autowired
	private MypageDAO dao;
	
	@Autowired
    private AES256 aES256;
	
	// 회원정보 변경
	@Override
	public boolean updateinfo(Map<String, String> paraMap) {
		
		boolean result = false;
		
		try {
			paraMap.put("mobile",aES256.encrypt(paraMap.get("mobile")));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		if(dao.updateinfo(paraMap)==1) {
			result = true;
		}
		
		return result;
	}

	// (비밀번호변경) 현 비밀번호 확인
	@Override
	public boolean nowpwdCheck(Map<String, String> paraMap) {
		
		paraMap.put("pwd",Sha256.encrypt(paraMap.get("pwd")));
		
		String userid = dao.nowpwdCheck(paraMap);
		
		if(userid==null) {
			return false;
		}
		
		return true;
	}

	// (비밀번호변경) 비밀번호 변경하기
	@Override
	public int updatepwd(Map<String, String> paraMap) {
		
		paraMap.put("pwd",Sha256.encrypt(paraMap.get("pwd")));
		
		return dao.updatepwd(paraMap);
	}
	
	// (의료인- 진료예약 열람) 아이디를 통해 병원인덱스 값 찾기
	@Override
	public String Searchhospital(String userid) {
		String hidx = dao.Searchhospital(userid);
		return hidx;
	}

	// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(검색포함)
	@Override
	public List<ReserveDTO> reserveList(Map<String, String> paraMap) {
		List<ReserveDTO> reserveList = null;
		if("전체".equals(paraMap.get("sclist"))) {
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명 검색)
			reserveList = dao.PatientNameList(paraMap);
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료현황 검색)
			reserveList.addAll(dao.ReserveStatusList(paraMap));
			
			try {
				Integer.parseInt(paraMap.get("inputsc"));
				// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색)
				reserveList.addAll(dao.ReserveDateList(paraMap));
			} catch (NumberFormatException e) {
			}

			Set<ReserveDTO> uniqueReserveSet = new HashSet<>(reserveList);	// 중복제거
			reserveList.clear();	// 기존에 있는 값 비우기
			reserveList.addAll(uniqueReserveSet);	// 중복제거한 리스트 넣어주기
			// === 진료일시 기준으로 내림차순 === //
			reserveList.sort(Comparator.comparing(ReserveDTO::getCheckin).reversed());
		}
		else {
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명, 진료현황)
			reserveList = dao.reserveList(paraMap);
		}
		return reserveList;
	}

	// (의료인- 진료예약 열람) 리스트 총 결과 개수
	@Override
	public int reserveListCnt(Map<String, String> paraMap) {
		int n = 0;
		List<ReserveDTO> reserveList = null;
		if("전체".equals(paraMap.get("sclist"))) {
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명 검색 / 페이징 X)
			reserveList = dao.TotalPatientNameList(paraMap);
			
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료현황 검색 / 페이징X)
			reserveList.addAll(dao.TotalReserveStatusList(paraMap));
			
			try {
				Integer.parseInt(paraMap.get("inputsc"));
				// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색 / 페이징 X)
				reserveList.addAll(dao.TotalReserveDateList(paraMap));
			} catch (NumberFormatException e) {
			}

			Set<ReserveDTO> uniqueReserveSet = new HashSet<>(reserveList);	// 중복제거
			reserveList.clear();	// 기존에 있는 값 비우기
			reserveList.addAll(uniqueReserveSet);	// 중복제거한 리스트 넣어주기
			
			n = reserveList.size();
		}
		else {
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트의 개수(환자명, 진료현황)
			n = dao.reserveListCnt(paraMap);
		}
		return n;
	}	
	
	// (의료인- 진료예약 열람) 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
	@Override
	public List<MemberDTO> GetPatientInfo(String patient_id) {
		List<MemberDTO> memberList = dao.GetPatientInfo(patient_id);
		return memberList;
	}
	
	// (의료인- 진료예약 열람) ridx 를 통해 예약 정보 가져오기
	@Override
	public ReserveDTO getRdto(String ridx) {
		ReserveDTO rsdto = dao.getRdto(ridx);
		return rsdto;
	}

	// (의료인- 진료예약 열람) 선택한 진료현황의 예약코드 가져오기
	@Override
	public String GetRcode(String rStatus) {
		String rcode = dao.GetRcode(rStatus);
		return rcode;
	}

	// (의료인- 진료예약 열람) 진료현황 변경해주기
	@Override
	public int ChangeRstatus(Map<String, String> paraMap) {
		int n = dao.ChangeRstatus(paraMap);
		return n;
	}

	// (의료인- 진료예약 열람) 진료완료로 변경하기
	@Override
	public void updatercode(String ridx) {
		dao.updatercode(ridx);
		
	}
	
	// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(검색포함)
	@Override
	public List<ReserveDTO> UserReserveList(Map<String, String> paraMap) {
		List<ReserveDTO> reserveList = null;
		if("전체".equals(paraMap.get("sclist"))) {
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(병원명 검색)
			reserveList = dao.HospitalNameList(paraMap);
			
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료현황 검색)
			reserveList.addAll(dao.UserReserveStatusList(paraMap));
			
			try {
				Integer.parseInt(paraMap.get("inputsc"));
				// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색)
				reserveList.addAll(dao.UserReserveDateList(paraMap));
			} catch (NumberFormatException e) {
			}

			Set<ReserveDTO> uniqueReserveSet = new HashSet<>(reserveList);	// 중복제거
			reserveList.clear();	// 기존에 있는 값 비우기
			reserveList.addAll(uniqueReserveSet);	// 중복제거한 리스트 넣어주기
			
			// === 진료일시 기준으로 내림차순 === //
			reserveList.sort(Comparator.comparing(ReserveDTO::getCheckin).reversed());
		}
		else {
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(병원명, 진료현황)
			reserveList = dao.UserreserveList(paraMap);
		}
		return reserveList;
	}

	// (일반회원- 진료예약 열람) 리스트 총 결과 개수
	@Override
	public int UserReserveListCnt(Map<String, String> paraMap) {
		int n = 0;
		List<ReserveDTO> reserveList = null;
		if("전체".equals(paraMap.get("sclist"))) {
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(병원명 검색 / 페이징 X)
			reserveList = dao.TotalReserveHospitalNameList(paraMap);
			
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료현황 검색 / 페이징X)
			reserveList.addAll(dao.TotalUserReserveStatusList(paraMap));
			
			try {
				Integer.parseInt(paraMap.get("inputsc"));
				// (일반회원- 진료예약 열람) userid 의 현재 예약리스트 가져오기(진료예약일시, 예약신청일 검색 / 페이징 X)
				reserveList.addAll(dao.TotalUserReserveDateList(paraMap));
			} catch (NumberFormatException e) {
			}

			Set<ReserveDTO> uniqueReserveSet = new HashSet<>(reserveList);	// 중복제거
			reserveList.clear();	// 기존에 있는 값 비우기
			reserveList.addAll(uniqueReserveSet);	// 중복제거한 리스트 넣어주기
			
			n = reserveList.size();
		}
		else {
			// (일반회원- 진료예약 열람) userid 의 현재 예약리스트의 개수(병원명, 진료현황)
			n = dao.UserreserveListCnt(paraMap);
		}
		return n;
	}

	// (일반회원- 진료예약 열람) 예약된 병원의 아이디 값을 가지고 이름과 전화번호 알아오기
	@Override
	public List<MemberDTO> GetHidxInfo(String hidx) {
		List<MemberDTO> memberList = dao.GetHidxInfo(hidx);
		return memberList;
	}

	// (일반회원- 진료예약 열람) ridx 를 통해 진료접수 취소하기
	@Override
	public int cancleRdto(String ridx) {
		int n = dao.cancleRdto(ridx);
		return n;
	}

	// (의료인- 진료 일정관리) hidx 의 예약리스트 가져오기
	@Override
	public List<ReserveDTO> TotalreserveList(String hidx) {
		List<ReserveDTO> reserveList = dao.TotalreserveList(hidx);
		return reserveList;
	}

	// (의료인- 진료 일정관리) 병원과 예약일시를 통해 환자아이디 가져오기
	@Override
	public ReserveDTO getPatientd(Map<String, String> paraMap) {
		ReserveDTO rdto = dao.getPatientd(paraMap);
		return rdto;
	}
	
	// (의료인- 진료 일정관리) userid 를 통해 환자 정보 가져오기
	@Override
	public MemberDTO getPatientInfo(String userid) {
		MemberDTO mdto = dao.getPatientInfo(userid);
		return mdto;
	}

	// (검색통계) T0 데이터 가져오기
	@Override
	public Map<String, List<String>> getT0data(Map<String, String> paraMap) {
		
		Map<String,List<String>> resultMap = new HashMap<>();
		
		if(paraMap.get("opr").equals("r0")) { // 1주일
			
			List<String> dayList = new ArrayList<>();
			List<String> sourceList = new ArrayList<>();
	        LocalDate today = LocalDate.now();
	        int todayDayOfWeek = today.getDayOfWeek().getValue();
	        for (int i = 1; i <= 7; i++) {
	            int dayOfWeek = (todayDayOfWeek + i) % 7;
	            dayList.add(Myutil.getDayOfWeekString(dayOfWeek));
	        }
	        
	        resultMap.put("xAxis",dayList);
	        
	        for(int i=0 ; i<7;i++) {
	        	LocalDate date = today.minusDays(6).plusDays(i);
	        	String formattedDate =  date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        	Map<String,String> t0Map = new HashMap<String, String>();
	        	t0Map.put("date",formattedDate);
	        	t0Map.put("opu",paraMap.get("opu"));
	        	int source = dao.getT0data(t0Map);
	        	sourceList.add(String.valueOf(source));
	        }
	        
	        resultMap.put("data",sourceList);
	        
		}else { // 1달
			
			List<String> dayList = new ArrayList<>();
			List<String> sourceList = new ArrayList<>();
	        LocalDate startdate = LocalDate.now().minusDays(30);

	        int interval = 5;

	        for (int i = 0; i <= 6; i++) {
	            LocalDate date = startdate.plusDays(i * interval);
	            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	            Map<String,String> t0Map = new HashMap<String, String>();
	        	t0Map.put("date",formattedDate);
	        	t0Map.put("opu",paraMap.get("opu"));
	            int source = dao.getT0data(t0Map);
	        	sourceList.add(String.valueOf(source));
	            dayList.add(formattedDate);
	        }

	        resultMap.put("xAxis",dayList);
	        resultMap.put("data",sourceList);
			
		}
		
		return resultMap;
	}

	// (검색통계) T1 데이터 가져오기
	@Override
	public List<Map<String, String>> getT1data(Map<String, String> paraMap) {
		
		Map<String,String> t1Map = new HashMap<String, String>();

		LocalDate today = LocalDate.now();
		t1Map.put("today",today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		if(paraMap.get("opr").equals("r0")) {
			LocalDate StartDay = today.minusDays(7);
			t1Map.put("startday",StartDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}else {
			LocalDate StartDay = today.minusDays(30);
			t1Map.put("startday",StartDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		
		t1Map.put("opu", paraMap.get("opu"));
		
		return dao.getT1data(t1Map);
	}

	@Override
	public List<Map<String, String>> getmyslog(MemberDTO loginuser) {
		return dao.getmyslog(loginuser);
	}

	@Override
	public List<CommuBoardDTO> getmyPostList(Map<String, String> paraMap) {
		return dao.getmyPostList(paraMap);
	}
	
	@Override
	public int getmyPostTotalCount(Map<String, String> paraMap) {
		return dao.getmyPostTotalCount(paraMap);
	}
	
	
	@Override
	public List<CommuBoardDTO> getmyBookmarkList(Map<String, String> paraMap) {
		return dao.getmyBookmarkList(paraMap);
	}
	@Override
	public int getBMListTotalCount(Map<String, String> paraMap) {
		return dao.getBMListTotalCount(paraMap);
	}

	@Override
	public List<CommuCommentDTO> getmycommentList(Map<String, String> paraMap) {
		
		List<CommuCommentDTO> cmtList = dao.getmycommentList(paraMap);
		/*
	    for (CommuCommentDTO comment : cmtList) {
	        // URL 템플릿 설정
	    	String cidx = comment.getCidx();
	    	String cmidx = comment.getCmidx();
	        comment.setCommenturl("http://localhost:9099/Medinfora/commu/commuView.bibo?cidx="+ cidx + "#cmt-" + cmidx);
	    }
		*/
		return cmtList;
	}

	@Override
	public int getmycmtTotalCount(Map<String, String> paraMap) {
		
		return dao.getmycmtTotalCount(paraMap);
	}
	
	
}
