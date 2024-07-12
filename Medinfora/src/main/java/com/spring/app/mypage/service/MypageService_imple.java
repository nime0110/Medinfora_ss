package com.spring.app.mypage.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;
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
		
		try {
			paraMap.put("mobile",aES256.encrypt(paraMap.get("mobile")));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		return false;
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
		List<ReserveDTO> reserveList = dao.reserveList(paraMap);
		return reserveList;
	}

	// (의료인- 진료예약 열람) 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
	@Override
	public List<MemberDTO> GetPatientInfo(String patient_id) {
		List<MemberDTO> memberList = dao.GetPatientInfo(patient_id);
		return memberList;
	}	
	
}
