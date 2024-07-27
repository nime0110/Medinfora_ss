package com.spring.app.commu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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


}
