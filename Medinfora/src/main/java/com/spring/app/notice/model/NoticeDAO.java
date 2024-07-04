package com.spring.app.notice.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.NoticeDTO;

@Mapper
public interface NoticeDAO {

    // 공지사항
    public int noticeWrite(NoticeDTO noticedto);

    // 총 게시물 건수(totalCount) 구하기 - 검색이 있을 때와 검색이 없을때 로 나뉜다.
    public int getTotalCount(Map<String, String> paraMap);

    // 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것
    public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap);

    // 첨부파일이 있는 글쓰기
	public int add_noticeWrite(NoticeDTO noticedto);
	
}
