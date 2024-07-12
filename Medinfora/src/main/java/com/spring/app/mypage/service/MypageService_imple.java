package com.spring.app.mypage.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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
		}
		else {
			// (의료인- 진료예약 열람) hidx 의 현재 예약리스트 가져오기(환자명, 진료현황)
			reserveList = dao.reserveList(paraMap);
		}
		return reserveList;
	}

	// (의료인- 진료예약 열람) 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
	@Override
	public List<MemberDTO> GetPatientInfo(String patient_id) {
		List<MemberDTO> memberList = dao.GetPatientInfo(patient_id);
		return memberList;
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
	
}
