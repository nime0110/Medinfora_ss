package com.spring.app.mypageList.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spring.app.domain.MemberDTO;

public interface MypageListService {

	   // 회원 목록 가져오기 
    List<MemberDTO> getMemberList(Map<String, Object> paramMap);
    
    // 회원 상세 정보를 가져오기
    MemberDTO getMemberDetail(String userid);
    
  // 회원 정지
	boolean StopMember(String userid);

	int getTotalPage(Map<String, Object> paraMap);

	
}