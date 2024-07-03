package com.spring.app.notice.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;


import com.spring.app.common.FileManager;

import com.spring.app.common.Myutil;
import com.spring.app.main.MemberDTO;
import com.spring.app.main.NoticeDTO;
import com.spring.app.main.service.MainService;

@Controller
public class NoticeController {

	@Autowired
	private MainService service;


	@Autowired
	private FileManager fileManager;


	// 공지사항 글 쓰기 폼 페이지 요청 
	@GetMapping("/notice/noticeWrite.bibo")
	public ModelAndView isLogin_noticeWrite(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {


		mav.setViewName("notice/noticeWrite.tiles");
		return mav;
	}

	@PostMapping("/noticeWriteEnd.bibo")
	public ModelAndView noticeWriteEnd(Map<String, String> paraMap, ModelAndView mav, NoticeDTO noticedto, MultipartHttpServletRequest mrequest) {

		MultipartFile attach = noticedto.getAttach();

		if(attach != null) {
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");

			String path = root + "resources" + File.separator+"files";

			String newFileName = "";

			byte[] bytes = null;

			long fileSize = 0;

			try {

				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();

				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);

				noticedto.setFilename(newFileName);
				noticedto.setOrgname(originalFilename);

				fileSize = attach.getSize();
				noticedto.setFilesize(String.valueOf(fileSize));



			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 	int n = service.noticeWrite(noticedto);

		int n = 0;

		if(attach.isEmpty()) {

			n=service.noticeWrite(noticedto);

		}else {
			n = service.add_noticeWrite(noticedto);
		}

		if(n==1) {
			mav.setViewName("redirect:/notice/noticeList.bibo");
		} 
		else {
			mav.setViewName("mediinfora/views/notice/noticeWrite.tiles");
		}

		paraMap.put("userid", noticedto.getUserid());
		paraMap.put("point", "100");

		return mav;
	}


	// 글 목록 보기
	@GetMapping("/notice/noticeList.bibo")
	 public ModelAndView noticeList(ModelAndView mav, HttpServletRequest request) {

		 HttpSession session = request.getSession();
		    session.setAttribute("readCountPermission", "yes");

		    String searchType = request.getParameter("searchType");
		    String searchWord = request.getParameter("searchWord");
		    String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		    if (searchType == null) {
		        searchType = "";
		    }

		    if (searchWord == null) {
		        searchWord = "";
		    }

		    if (searchWord != null) {
		        searchWord = searchWord.trim();
		    }

		    Map<String, String> paraMap = new HashMap<>();
		    paraMap.put("searchType", searchType);
		    paraMap.put("searchWord", searchWord);

		    int totalCount = service.getTotalCount(paraMap);
		    int sizePerPage = 10;
		    int currentShowPageNo = 1;
		    int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

		    if (str_currentShowPageNo != null) {
		        try {
		            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
		            if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
		                currentShowPageNo = 1;
		            }
		        } catch (NumberFormatException e) {
		            currentShowPageNo = 1;
		        }
		    }

		    int startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
		    int endRno = startRno + sizePerPage - 1;

		    paraMap.put("startRno", String.valueOf(startRno));
		    paraMap.put("endRno", String.valueOf(endRno));

		    List<NoticeDTO> noticeListdto = service.noticeListSearch_withPaging(paraMap);

		    // 디버깅: noticeListdto의 크기와 내용을 로그로 출력
		    System.out.println("noticeListdto size: " + noticeListdto.size());
		    for (NoticeDTO notice : noticeListdto) {
		        System.out.println("Notice: " + notice.getTitle());
		    }

		    mav.addObject("noticeListdto", noticeListdto);
		    mav.addObject("paraMap", paraMap);

		    String pageBar = Myutil.makePageBar(currentShowPageNo, sizePerPage, totalPage, "noticeList.bibo", searchType, searchWord);
		    mav.addObject("pageBar", pageBar);

		    String goBackURL = Myutil.getCurrentURL(request);
		    mav.addObject("goBackURL", goBackURL);

		    mav.setViewName("notice/noticeList.tiles");
		    return mav;
		}

	@PostMapping("/editEnd.bibo")
	public ModelAndView editEnd(ModelAndView mav, NoticeDTO noticedto, HttpServletRequest request) {

		int n = service.edit(noticedto);

		if(n==1) {
			mav.addObject("message", "글 수정 성공!!");
			mav.addObject("loc", request.getContextPath()+"/view.action?seq="+noticedto.getSeq());
			mav.setViewName("msg");
		}
		return mav;
	}


	@GetMapping("/del.bibo")
	public ModelAndView isLogin_del(HttpServletRequest request,HttpServletResponse response ,ModelAndView mav) {
		
		String seq = request.getParameter("seq");

		String message = "";
		
		try {
			Integer.parseInt(seq);
			
			// 글 삭제해야 할 글 1개 내용가져오기
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("seq", seq);
			
			NoticeDTO noticedto = service.getView_no_increase_readCount(paraMap);
			// 글 조회수 증가는 없고 단순히 글 1개만 조회를 해오는 것
			
			if(noticedto == null) {
				message = "글 삭제가 불가합니다.";
			}
			else {
				HttpSession session = request.getSession();
				MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser"); 
				
				if( !loginuser.getUserid().equals(noticedto.getFk_userid()) ) {
					message = "다른 사용자의 글은 삭제가 불가합니다.";
				}
				else {
					// 자신의 글을 삭제할 경우
					// 가져온 1개글을 글삭제할 폼이 있는 view 단으로 보내준다.
					mav.addObject("noticedto", noticedto);
					mav.setViewName("notice/del.tiles1");
					
					return mav;
				}
			}
			
		} catch (NumberFormatException e) {
			message = "글 삭제가 불가합니다.";
		}
		
		String loc = "javascript:history.back()";
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;


}
	
	@PostMapping("/delEnd.action")
	public ModelAndView delEnd(ModelAndView mav, HttpServletRequest request) {
	String seq = request.getParameter("seq");
		
		int n = service.del(seq);
		
		if(n==1) {
			mav.addObject("message", "글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath()+"/noticeList.bibo");
			mav.setViewName("msg");
		}
		
		return mav;
	}
	
	
}