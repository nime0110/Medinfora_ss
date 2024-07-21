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

	@Override
	public int getTotalPage(Map<String, Object> paraMap) {

		 return mdao.getTotalPage(paraMap);
	}
	
}
