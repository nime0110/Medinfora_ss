package com.spring.app.commu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.spring.app.common.FileManager;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuFilesDTO;

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



}
