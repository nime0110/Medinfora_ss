package com.spring.app.mypage.model;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.MemberDTO;

public interface MypageListDAO {

	// 회원 목록 가져오기 
	List<MemberDTO> getMemberList(Map<String, Object> paramMap);

	// 회원 상세 정보 가져오기 
	MemberDTO getMemberDetails(String userid);

	int deleteMember(String userid);

}
