package com.spring.app.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.spring.app.domain.NoticeDTO;

public interface NoticeService {

	// ================================= 공지사항 =====================================
     int noticeWrite(NoticeDTO noticedto);

    // 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
     int getTotalCount(Map<String, String> paraMap);

    // 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것
     List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap);

    // 파일첨부가 있는 글쓰기 
	 int add_noticeWrite(NoticeDTO noticedto);

	 // 글 조회수 증가와 함께 글 1개를 조회해 오는 것 
	    NoticeDTO getView(Map<String, Object> paraMap, HttpSession session);

	NoticeDTO getView_no_increase_readCount(Map<String, String> paraMap);

	int edit(NoticeDTO noticedto);


	int del(Map<String, String> paraMap);

	NoticeDTO getPrevNotice(int nidx);

	NoticeDTO getNextNotice(int nidx);





	
}
