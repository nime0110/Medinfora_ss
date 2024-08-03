package com.spring.app.mypageList.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.app.domain.MemberDTO;

public interface MypageListService {

	   // 회원 목록 가져오기 
    List<MemberDTO> getMemberList(Map<String, Object> paramMap);
    
    // 회원 상세 정보를 가져오기
    MemberDTO getMemberDetail(String userid);
    
  // 회원 정지
	boolean StopMember(String userid);

	int getTotalPage(Map<String, Object> paraMap);

	String makePageBar(int parseInt, int totalPage, int totalCount, String subject, String word);

	int getTotalCount(Map<String, Object> paraMap);

	void userid_to_Excel(Map<String, Object> paraMap, Model model);


	
}
