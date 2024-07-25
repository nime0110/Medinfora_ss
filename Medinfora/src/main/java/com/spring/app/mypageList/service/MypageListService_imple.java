package com.spring.app.mypageList.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.AES256;
import com.spring.app.common.Sha256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.mypageList.model.MypageListDAO;

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
		/*
		 * // 페이지 번호와 페이지 크기 가져오기 int pageNo = (int)paraMap.get("pageNo"); int pageSize
		 * = (int) paraMap.get("pageSize");
		 * 
		 * // 검색 조건 처리 String searchType = (String) paraMap.get("searchType"); String
		 * searchKeyword = (String) paraMap.get("searchKeyword");
		 */	
    	return mdao.getMemberList(paraMap);
    }
    
    // 회원 정보 상세 정보 가져오기
    @Override
    public MemberDTO getMemberDetail(String userid) {
        if (userid == null || userid.trim().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다.");
        }
        
        MemberDTO member = mdao.getMemberDetails(userid);
        
        if (member != null) {
            try {
                member.setMobile(aES256.decrypt(member.getMobile()));
                member.setEmail(aES256.decrypt(member.getEmail()));
            } catch (Exception e) {
                // 로깅 추가
                throw new RuntimeException("회원 정보 복호화 중 오류가 발생했습니다.", e);
            }
        }
        
        return member;
    }
    
   // 회원 정지 
	@Override
	public boolean StopMember(String userid) {
	
		 try {
	            int result = mdao.updateMemberStatusToStopped(userid);
	            return result > 0;
	        } catch (Exception e) {
	            // 로그 추가
	            e.printStackTrace();
	            return false;
	        }
	    }

	// 페이지 보여줄 레코드 수 
	@Override
	public int getTotalPage(Map<String, Object> paraMap) {
		int totalCount = getTotalCount(paraMap);
	    int pageSize = 10; // 페이지당 보여줄 레코드 수
	    return (int) Math.ceil((double) totalCount / pageSize);
		
	}
// 페이지바 
	@Override
	public String makePageBar(int currentShowPageNo, int totalPage, int totalCount, String subject, String word) {
		    
		   if (totalCount == 0 || totalPage <= 1) {
		        return "1"; // 결과가 없거나 1페이지뿐일 때는 페이지 바를 생성하지 않음
		    }
		
			int pageNo = 1;
		    int blockSize = 10;
		    int loop = 1;
		    
		    String pageBar = "<ul class='pagination hj_pagebar nanum-n size-s'>";
		    
		    if(pageNo != 1) {
		        pageBar += "<li class='page-item'>" 
		                 + "    <a class='page-link' href='javascript:Page("+(pageNo-1)+")'>" 
		                 + "          <span aria-hidden='true'>&laquo;</span>" 
		                 + "       </a>" 
		                 + "</li>";
		    }
		    
		    while(!(loop>blockSize || pageNo > totalPage)) {
		        if(pageNo == currentShowPageNo) {
		            pageBar += "<li class='page-item'>"
		                    + "      <a class='page-link nowPage'>"+pageNo+"</a>" 
		                    + "</li>";
		        }
		        else{
		            pageBar += "<li class='page-item'>"
		                    + "      <a class='page-link' href='javascript:Page("+pageNo+")'>" +pageNo+"</a>" 
		                    + "</li>";
		        }
		        loop++;
		        pageNo++;
		    }
		    
		    if(pageNo <= totalPage) {
		        pageBar += "<li class='page-item'>"
		                 + "      <a class='page-link' href='javascript:Page("+pageNo+")'>"
		                 + "          <span aria-hidden='true'>&raquo;</span>"
		                 + "       </a>"
		                 + "</li>";
		    }
		    
		    pageBar += "</ul>";
		    
		    return pageBar;
		}

	@Override
	public int getTotalCount(Map<String, Object> paraMap) {
	   /* int totalCount = 0;
	    try {
	        totalCount = mdao.getTotalPage(paraMap);
	       
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalCount;	
	    }*/

		return mdao.getTotalCount(paraMap);

	}
}
