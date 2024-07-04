package com.spring.app.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.NoticeDTO;
import com.spring.app.notice.model.NoticeDAO;

@Service
public class NoticeService_imple implements NoticeService {

	@Autowired
    private NoticeDAO dao;
	
	@Override
	public int noticeWrite(NoticeDTO noticedto) {
        int n = dao.noticeWrite(noticedto);
        return n;
    }

    // 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
    @Override
    public int getTotalCount(Map<String, String> paraMap) {
        int totalCount = dao.getTotalCount(paraMap);
        return totalCount;
    }

    // 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것
    @Override
    public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap) {
        List<NoticeDTO> noticeList = dao.noticeListSearch_withPaging(paraMap);
        return noticeList;

    }

    // 파일 첨부가 있는 글쓰기
	@Override
	public int add_noticeWrite(NoticeDTO noticedto) {
		int n = dao.add_noticeWrite(noticedto);
		 return n;
	}
	
}
