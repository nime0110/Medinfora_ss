package com.spring.app.notice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.common.FileManager;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.notice.model.NoticeDAO;

@Service
public class NoticeService_imple implements NoticeService {

	@Autowired
	private NoticeDAO dao;
	
	// === #186. 첨부파일 삭제를 위한 것 === //
	 	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
		private FileManager fileManager;

	@Override
	public int noticeWrite(NoticeDTO noticedto) {
		int n = dao.noticeWrite(noticedto);
		System.out.println("gc : "+n);
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
		
	
		int n = dao.add_noticeWrite(noticedto); // 첨부파일이 있는 경우
		return n;
	}

	@Override
	public NoticeDTO getView(Map<String, String> paraMap, HttpSession session) {

		MemberDTO mIdx = null;
		int plz = 1; // 세션값이 있을 때만 값을 가져오려고 만
		
		if(session!=null && session.getAttribute("mIdx") != null) {
			mIdx = (MemberDTO) session.getAttribute("mIdx");
			if (mIdx != null) {
				plz = mIdx.getmIdx();
			}
		}
		NoticeDTO noticedto = dao.getView(paraMap);
//System.out.println("noticedto : " + noticedto.getNidx());
	if(plz != 0) { // 관리자 외의 계정으로 로그인 했을 경우 

	//if(mIdx.getmIdx() != 0) { // 관리자 외의 계정으로 로그인 했을 경우 
		int nidx = noticedto.getNidx();
			int n = dao.increase_readCount(nidx);

			if(n==1) { 
				noticedto.setViewcnt(noticedto.getViewcnt()+1);
			}// end of if 

		} // end of if
		
		return noticedto;
	} //end of public NoticeDTO getView(Map<String, String> paraMap, HttpSession session)

	// 임시 
	@Override
	public NoticeDTO getView_no_increase_readCount(Map<String, String> paraMap) {
		NoticeDTO noticedto = dao.getView1(paraMap);
		  if (noticedto == null) {
	            System.out.println("NoticeService_imple.getView_no_increase_readCount() - noticedto is null");
	            return null;		
	        }
		return noticedto;
	}

	
	@Override
	public int edit(NoticeDTO noticedto) {
		int n = dao.edit(noticedto);
		return n;
	}

	@Override
	public int del(Map<String, String> paraMap) {
		int n = dao.del(paraMap.get("seq"));
		
		if(n==1) {
			String path = paraMap.get("path");
			String fileName = paraMap.get("fileName");
			
			if(fileName != null && !"".equals(fileName)) {
				try {
					fileManager.doFileDelete(fileName, path);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return n;
	}

	

} // end of public class NoticeService_imple implements NoticeService 

