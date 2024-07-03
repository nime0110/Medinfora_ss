package com.spring.app.main.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
import com.spring.app.main.domain.NoticeDTO;

@Repository
public class MainDAO_imple implements MainDAO{

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override // 테스트용
	public String daotest() {
		return "pass";
	}
	
	
	// 로그인 유저 정보
	@Override
	public MemberDTO getLoginuser(Map<String, String> paraMap) {
		MemberDTO loginuser = sqlsession.selectOne("mediinfora.getLoginuser", paraMap);
		return loginuser;
	}
	
	
	// 회원코드 변경 (휴먼처리)
	@Override
	public void updatemIdx(String userid, String idx) {
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("idx", idx);
		
		sqlsession.update("mediinfora.updatemIdx", paraMap);
	}

	// 로그인 유저 ip 기록
	@Override
	public void insert_log(Map<String, String> paraMap) {
		sqlsession.insert("mediinfora.insert_log", paraMap);
		
	}

	// 병원정보 API 입력용 메소드
	@Override
	public int hpApiInputer(HospitalDTO hospitalDTO) {
		return sqlsession.insert("hpApiInputer",hospitalDTO);
	}


	// 공지사항 
	@Override
	public int noticeWrite(NoticeDTO noticedto) {
			int n = sqlsession.insert("mediinfora.noticeWrite", noticedto);
		return n;
	}


	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int totalCount = sqlsession.selectOne("mediinfora.getTotalCount", paraMap);
		return totalCount;
	}


	// 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것 
	@Override
	public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap) {
		List<NoticeDTO> noticeList = sqlsession.selectList("mediinfora.noticeListSearch_withPaging", paraMap);
		return noticeList;
	}
	
	
	

}
