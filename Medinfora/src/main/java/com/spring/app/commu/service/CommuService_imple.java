package com.spring.app.commu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.common.FileManager;
import com.spring.app.commu.model.CommuDAO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.commu.BookmarkDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;
import com.spring.app.domain.commu.CommuFilesDTO;
import com.spring.app.domain.commu.SuggestionDTO;
import com.spring.app.hpsearch.model.HpsearchDAO;
import com.spring.app.main.model.MainDAO;

@Service
public class CommuService_imple implements CommuService {
	
	//의존객체 주입 DI ----- 
	@Autowired
	private CommuDAO cmdao;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public String getSeqCommu() {
		return cmdao.getSeqCommu();
	}

	@Override
	public int add(CommuBoardDTO cbdto) {
		return cmdao.add(cbdto);
	}

	@Override
	public void add_File(CommuFilesDTO cfdto) {
		cmdao.add_File(cfdto);
	}

	@Override
	public List<CommuBoardDTO> getCommuBoardList(Map<String, String> paraMap) {
		return cmdao.getCommuBoardList(paraMap);
	}

	@Override
	public int getCBListTotalCount(Map<String, String> paraMap) {
		return cmdao.getCBListTotalCount(paraMap);
	}

	@Override
	public List<String> getfileSeqList() {
		return cmdao.getfileSeqList();
	}

	@Override
	public CommuBoardDTO getCommuDetail(String cidx) {
		CommuBoardDTO cdto = cmdao.getCommuDetail(cidx);
		int n = cmdao.viewCntIncrease(cidx); //글조회수 증가 - db에서 업데이트 
		if(n == 1) {
			//boardvo 는 1이 올라가기 전 상태(select 안해와서)-> 어차피 db에 업데이트됨 -> set 해주면 됨
			cdto.setViewcnt(String.valueOf(Integer.parseInt(cdto.getViewcnt())+1));
		}
		return cdto;
	}

	@Override
	public List<CommuFilesDTO> getAttachfiles(String cidx) {
		return cmdao.getAttachfiles(cidx);
	}

	@Override
	public CommuBoardDTO getCommuDetail_no_increase_viewCnt(String cidx) {
		CommuBoardDTO cdto = cmdao.getCommuDetail(cidx);
		return cdto;
	}

	@Override
	public int edit(CommuBoardDTO cbdto) {
		return cmdao.edit(cbdto);
	}

	@Override
	public int edit_file(CommuFilesDTO cfdto) {
		return cmdao.edit_file(cfdto);
	}

	@Override
	public void folderFileDel(Map<String, String> paraMap) {
		
		String path = paraMap.get("path");
		String fileName = paraMap.get("fileName");
		if(fileName != null && !"".equals(fileName)) {
			try {
				fileManager.doFileDelete(fileName, path);
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
		
	}

	@Override
	public int fileDel(Map<String, String> paraMap) {
		return cmdao.fileDel(paraMap);
	}

	@Override
	public int fileDelAll(String cidx) {
		return cmdao.fileDelAll(cidx);
	}

	@Override
	public int del(String cidx) {
		return cmdao.del(cidx);
	}

	// 댓글쓰기와 함께 commu 테이블의 해당 글 댓글수 칼럼 증가
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int addComment(CommuCommentDTO cmtdto) {
	    int n1 = 0, n2 = 0, result = 0;
	    
	    //댓글이 댓글인지 답댓글인지 구분해야함, 원댓글쓰기의 경우(즉 fk_cmidx가 없을 경우)
	    if("".equals(cmtdto.getFk_cmidx())) { 
			int groupno = cmdao.getGroupnoMax()+1;  //그룹번호 세팅
			cmtdto.setGroupno(String.valueOf(groupno)); 
	    }
	    
	    
	    n1 = cmdao.addComment(cmtdto); // 댓글쓰기
	    
	    if(n1 == 1) { //댓글쓴다음 
	        n2 = cmdao.updateCommentCount(cmtdto.getCidx()); // commu 테이블에 commentCount 컬럼이 1증가(update) =>cidx = 원글번호
	    }
	    
	    if(n2 == 1) {
	        result = 1; // 두 작업이 모두 성공했을 때만 결과를 1로 설정
	    }
	    
	    return result;
	}

	//댓글 조회
	@Override
	public List<CommuCommentDTO> getCommentList(Map<String, String> paraMap) {
		return cmdao.getCommentList(paraMap);
	}
	
	//댓글 페이징 처리용 총 갯수
	@Override
	public int getCommentTotalCount(String cmidx) {
		return cmdao.getCommentTotalCount(cmidx);
	}

	//댓글 수정 
	@Override
	public int updateComment(CommuCommentDTO cmtdto) {
		return cmdao.updateComment(cmtdto);
	}

	@Override
	public int deleteComment(String cmidx) {
		return cmdao.deleteComment(cmidx);
	}

	@Override
	public int suggestionPost(SuggestionDTO sdto) {
		return cmdao.suggestionPost(sdto);
	}

	@Override
	public int postSuggestionUpdate(SuggestionDTO sdto) {
		return cmdao.postSuggestionUpdate(sdto);
	}

	@Override
	public int checkSuggestion(SuggestionDTO sdto) {
		return cmdao.checkSuggestion(sdto);
	}

	@Override
	public int alreadyMarking(BookmarkDTO bdto) {
		return cmdao.alreadyMarking(bdto);
	}

	@Override
	public int bookmarkPost(BookmarkDTO bdto) {
		return cmdao.bookmarkPost(bdto);
	}

	@Override
	public int delBookMark(BookmarkDTO bdto) {
		return cmdao.delBookMark(bdto);
	}

	@Override
	public int getCommentPage(CommuCommentDTO cmtdto) {
		return cmdao.getCommentPage(cmtdto);
	}






}
