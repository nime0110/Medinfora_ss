package com.spring.app.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;
import com.spring.app.common.Sha256;
import com.spring.app.domain.MemberDTO;

import com.spring.app.mypage.model.MypageListDAO;

@Service

public class MypageListService_imple implements MypageListService {
	//////////////////////////////////////////////////
	@Autowired
	private AES256 aES256;
	
	@Autowired
	private MypageListDAO mdao;
	// 회원 목록 가져오기
	@Override
	public List<MemberDTO> getMemberList(Map<String, Object> paraMap) {
		List<MemberDTO> selectMemberList = mdao.getMemberList(paraMap);

		return selectMemberList;
	}

	// 회원 정보 상세 정보 가져오기
	@Override
	public MemberDTO getMemberDetail(Map<String, String> paraMap, HttpServletRequest request) {

		if(paraMap.get("loginmethod") == "0"){
			paraMap.put("pw", Sha256.encrypt(paraMap.get("pwd")));
		}
		String userid = "";
		MemberDTO member = mdao.getMemberDetails(userid);
		
		if (member != null) {
			try {
				member.setMobile(aES256.decrypt(member.getMobile()));
				member.setEmail(aES256.decrypt(member.getEmail()));
			} catch (Exception e) {
				// 로깅 추가
				e.printStackTrace();
			}
		}
		return member;
	}

	@Override
	public boolean deleteMember(String userid) {
		return mdao.deleteMember(userid) == 1;

	}
}
