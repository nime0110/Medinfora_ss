package com.spring.app.commu.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.commu.BookmarkDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;
import com.spring.app.domain.commu.CommuFilesDTO;
import com.spring.app.domain.commu.SuggestionDTO;


@Mapper
public interface CommuDAO {

	String getSeqCommu();

	int add(CommuBoardDTO cbdto);

	void add_File(CommuFilesDTO cfdto);

	List<CommuBoardDTO> getCommuBoardList(Map<String, String> paraMap);

	int getCBListTotalCount(Map<String, String> paraMap);

	List<String> getfileSeqList();

	CommuBoardDTO getCommuDetail(String cidx);
	
	int viewCntIncrease(String cidx);

	List<CommuFilesDTO> getAttachfiles(String cidx);

	CommuBoardDTO getCommuDetail_no_increase_viewCnt(String cidx);

	int edit(CommuBoardDTO cbdto);

	int edit_file(CommuFilesDTO cfdto);

	int fileDel(Map<String, String> paraMap);

	int fileDelAll(String cidx);

	int del(String cidx);

	int addComment(CommuCommentDTO cmtdto);

	int updateCommentCount(String cidx);

	int getGroupnoMax();

	List<CommuCommentDTO> getCommentList(Map<String, String> paraMap);

	int getCommentTotalCount(String cmidx);

	int allCommentDel(String cidx);

	int updateComment(CommuCommentDTO cmtdto);

	int deleteComment(String cmidx);

	int suggestionPost(SuggestionDTO sdto);

	int postSuggestionUpdate(SuggestionDTO sdto);

	int checkSuggestion(SuggestionDTO sdto);

	int alreadyMarking(BookmarkDTO bdto);

	int bookmarkPost(BookmarkDTO bdto);

	int delBookMark(BookmarkDTO bdto);


}
