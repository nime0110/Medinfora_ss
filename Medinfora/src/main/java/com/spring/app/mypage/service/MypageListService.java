package com.spring.app.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spring.app.domain.MemberDTO;

public interface MypageListService {

	   // 회원 목록 가져오기 
    List<MemberDTO> getMemberList(Map<String, Object> paramMap);
    
    // 회원 상세 정보를 가져오기
    MemberDTO getMemberDetail(String userid);
    
    // 회원 탈퇴 처리
    boolean deleteMember(String userid);

	
}
