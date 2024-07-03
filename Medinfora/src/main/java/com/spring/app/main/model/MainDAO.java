package com.spring.app.main.model;

import java.util.List;
import java.util.Map;

import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
import com.spring.app.main.domain.NoticeDTO;

public interface MainDAO {

	// DAO 테스트용 메소드
	public String daotest();
	
	// 병원정보 API 입력용 메소드 
	public int hpApiInputer(HospitalDTO hospitalDTO);


	// 로그인 유저 정보 가져오기
	public MemberDTO getLoginuser(Map<String, String> paraMap);

	// 회원코드 변경 (휴먼처리)
	public void updatemIdx(String userid, String idx);

	// 로그인 유저 ip 기록
	public void insert_log(Map<String, String> paraMap);

	// 공지사항 
	public int noticeWrite(NoticeDTO noticedto);

	// 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
	public int getTotalCount(Map<String, String> paraMap);
	
	// 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것 
	public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap);

	
	
}
