package com.spring.app.mypageList.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.MemberDTO;

@Mapper
public interface MypageListDAO {
    // 회원 목록 가져오기 
    List<MemberDTO> getMemberList(Map<String, Object> paramMap);
    
    // 회원 상세 정보 가져오기 
    MemberDTO getMemberDetails(String userid);
    
    // 회원 삭제하기
    int deleteMember(String userid);

    // 회원 정지 
	int updateMemberStatusToStopped(String userid);

	int getTotalPage(Map<String, Object> paraMap);

	int getTotalCount(Map<String, Object> paraMap);

	//int getTotalCount(Map<String, Object> paraMap);

}
