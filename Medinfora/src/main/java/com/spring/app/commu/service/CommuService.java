package com.spring.app.commu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.spring.app.common.FileManager;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.commu.BookmarkDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;
import com.spring.app.domain.commu.CommuFilesDTO;
import com.spring.app.domain.commu.SuggestionDTO;

public interface CommuService {


	// 시퀀스 채번하기
	String getSeqCommu();

	// 글 등록
	int add(CommuBoardDTO cbdto);

	// 첨부파일 등록
	void add_File(CommuFilesDTO cfdto);

	// 커뮤니티 게시판 
	List<CommuBoardDTO> getCommuBoardList(Map<String, String> paraMap);

	// 커뮤니티 게시판 전체수 
	int getCBListTotalCount(Map<String, String> paraMap);

	//첨부파일 유무 알기 위해 DB select
	List<String> getfileSeqList();

	//조회수 증가 포함 글 상세 select 
	CommuBoardDTO getCommuDetail(String cidx);

	//첨부파일 가져오기
	List<CommuFilesDTO> getAttachfiles(String cidx);

	//조회수 증가 없이 글 상세 select
	CommuBoardDTO getCommuDetail_no_increase_viewCnt(String cidx);

	//글 수정하기
	int edit(CommuBoardDTO cbdto);

	//첨부파일수정하기
	int edit_file(CommuFilesDTO cfdto);

	//폴더 내 첨부파일 삭제
	void folderFileDel(Map<String, String> paraMap);

	//테이블에서 첨부파일 삭제
	int fileDel(Map<String, String> paraMap);

	//테이블에서 모든 첨부파일 삭제
	int fileDelAll(String cidx);

	//게시글 삭제
	int del(String cidx);

	// 댓글쓰기와 함께 commu 테이블의 해당 글 댓글수 칼럼 증가
	int addComment(CommuCommentDTO cmtdto);

	// 댓글 조회
	List<CommuCommentDTO> getCommentList(Map<String, String> paraMap);

	// 댓글 페이징 처리 관련 카운트
	int getCommentTotalCount(String cmidx);

	// 댓글 수정
	int updateComment(CommuCommentDTO cmtdto);

	// 댓글 삭제
	int deleteComment(String cmidx);

	//이미 추천된 게 있는지 조회
	int checkSuggestion(SuggestionDTO sdto);

	// 추천테이블 삽입
	int suggestionPost(SuggestionDTO sdto);

	// 추천시 글테이블 추천 1증가
	int postSuggestionUpdate(SuggestionDTO sdto);

 	//이미 북마크된게 있는지 조회
	int alreadyMarking(BookmarkDTO bdto);

	//북마크 테이블에 삽입
	int bookmarkPost(BookmarkDTO bdto);

	//북마크 해제
	int delBookMark(BookmarkDTO bdto);

	//댓글 위치 
	int getCommentPage(CommuCommentDTO cmtdto);




}
