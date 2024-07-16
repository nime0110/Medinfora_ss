package com.spring.app.mypage.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;
import com.spring.app.common.Sha256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
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

	// 회원 목록 가져오기
	@Override
	public List<MemberDTO> getMemberList(Map<String, Object> paramMap) {
		List<MemberDTO> selectMemberList = dao.getMemberList(paramMap);
	
		
		// 복호화
        for (MemberDTO member : selectMemberList) {
            try {
                String decryptedMobile = aES256.decrypt(member.getMobile());
                member.setMobile(decryptedMobile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return selectMemberList;
	}
	
	// (일반회원- 진료예약 열람) ridx 를 통해 진료접수 취소하기
	@Override
	public int cancleRdto(String ridx) {
		int n = dao.cancleRdto(ridx);
		return n;
	}
	
}
