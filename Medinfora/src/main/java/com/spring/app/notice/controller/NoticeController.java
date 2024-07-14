package com.spring.app.notice.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.spring.app.common.FileManager;
import com.spring.app.common.Myutil;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.notice.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;

	@Autowired
	private FileManager fileManager;

	@GetMapping("/notice/notice.bibo")
	public String notice() {

		return "/notice/notice";
	}

	// 공지사항 글 쓰기 폼 페이지 요청
	@GetMapping("/notice/noticeWrite.bibo")
	public ModelAndView noticeWrite(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		mav.setViewName("notice/noticeWrite.tiles");
		return mav;
	}

	@PostMapping("/notice/noticeWriteEnd.bibo")
	public ModelAndView isadmin_noticeWriteEnd(ModelAndView mav, HttpServletRequest request, HttpServletResponse response, 
	        NoticeDTO noticedto, MultipartHttpServletRequest mrequest)  {

	    HttpSession session = request.getSession();
	    MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
	    
		MultipartFile attach = noticedto.getAttach();

		if (attach != null && !attach.isEmpty()) {
		//	HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			String newFileName = "";

			try {
				byte[] bytes = attach.getBytes();
				String originalFilename = attach.getOriginalFilename();

				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);

				noticedto.setFilename(newFileName); // 파일 이름을 설정
				noticedto.setOrgname(originalFilename); // 원본 파일 이름을 설정
				long fileSize = attach.getSize();
				noticedto.setFilesize(String.valueOf(fileSize)); // 파일 크기를 설정
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int n;

	//	HttpSession session = request.getSession();
	//	MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

		noticedto.setUserid(loginuser.getUserid());

		if (attach == null || attach.isEmpty()) {
			n = service.noticeWrite(noticedto);
		} else {
			n = service.add_noticeWrite(noticedto);
		}

		if (n == 1) {
			mav.setViewName("redirect:/notice/noticeList.bibo");
		} else {
			mav.addObject("message", "공지사항 작성에 실패했습니다.");
			mav.setViewName("notice/noticeWrite.tiles");
		}

		return mav;
	}

	@GetMapping("/notice/noticeList.bibo")
	public ModelAndView noticeList(ModelAndView mav, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		Map<String, String> paraMap = new HashMap<>();
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

		mav.addObject("noticeListdto", noticeListdto);
		mav.addObject("paraMap", paraMap);

		String pageBar = Myutil.makePageBar(currentShowPageNo, sizePerPage, totalPage,
				request.getContextPath() + "/notice/noticeList.bibo");
		mav.addObject("pageBar", pageBar);

		String goBackURL = Myutil.getCurrentURL(request);
		mav.addObject("goBackURL", goBackURL);

		mav.setViewName("notice/noticeList.tiles");
		return mav;
	}

	// === #62. 글1개를 보여주는 페이지 요청 === //
	// @GetMapping("/view.action")
	@RequestMapping("/notice/view.bibo") // === #133. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함.
	public ModelAndView getView(ModelAndView mav, HttpServletRequest request) {

		try {
			int nidx = Integer.parseInt(request.getParameter("nidx"));
			// System.out.println("확인용 nidx" + nidx);
			// Map<String, String> paraMap = new HashMap<>();

			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("nidx", String.valueOf(nidx));
			HttpSession session = request.getSession();

			// 이전글, 다음 글 정보 가져오기

			NoticeDTO prevNotice = service.getPrevNotice(nidx);
			NoticeDTO nextNotice = service.getNextNotice(nidx);

			// MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

			// System.out.println("session 확인용 "+ loginuser);
			NoticeDTO n = service.getView(paraMap, session);

			mav.addObject("noticedto", n);// 저장해줄 이름
			mav.addObject("prevNotice", prevNotice);
			mav.addObject("nextNotice", nextNotice);
			mav.setViewName("notice/noticeView.tiles");
		} catch (NumberFormatException e) {
		    
			 mav.setViewName("redirect:/notice/noticeList.bibo");
			 
			 }

			 return mav;
			}
		
	// @GetMapping("/view_2.action")
	@PostMapping("/notice/view_2.bibo")
	public ModelAndView view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {

		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("nidx");

		// === #141. 이전글제목, 다음글제목 보기 시작 === //
		String goBackURL = request.getParameter("goBackURL");
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		/*
		 * redirect:/ 를 할때 "한글데이터는 0에서 255까지의 허용 범위 바깥에 있으므로 인코딩될 수 없습니다" 라는
		 * java.lang.IllegalArgumentException 라는 오류가 발생한다.
		 * 이것을 방지하려면 아래와 같이 하면 된다.
		 */

		try {
			searchWord = URLEncoder.encode(searchWord, "UTF-8");
			goBackURL = URLEncoder.encode(goBackURL, "UTF-8");

			// System.out.println("~~~~ view_2.action 의 URLEncoder.encode(searchWord,
			// \"UTF-8\") : " + searchWord);
			// ~~~~ view_2.action 의 URLEncoder.encode(searchWord, "UTF-8") :
			// %EC%84%9C%EC%98%81%ED%95%99

			// System.out.println("~~~~ view_2.action 의 URLEncoder.encode(goBackURL,
			// \"UTF-8\") : " + goBackURL);
			// ~~~~ view_2.action 의 URLEncoder.encode(goBackURL, "UTF-8") :
			// %2Flist.action%3FsearchType%3Dname+searchWord%3D%25EC%2584%259C%25EC%2598%2581%25ED%2595%2599+currentShowPageNo%3D11

			// System.out.println(URLDecoder.decode(searchWord, "UTF-8")); // URL인코딩 되어진 한글을
			// 원래 한글모양으로 되돌려주는 것임.
			// 서영학

			// System.out.println(URLDecoder.decode(goBackURL, "UTF-8")); // URL인코딩 되어진 한글을
			// 원래 한글모양으로 되돌려주는 것임.
			// /list.action?searchType=name searchWord=%EC%84%9C%EC%98%81%ED%95%99
			// currentShowPageNo=11

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// === #141. 이전글제목, 다음글제목 보기 끝 === //

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");

		// ==== redirect(GET방식임) 시 데이터를 넘길때 GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면
		// RedirectAttributes 를 사용하면 된다. 시작 ==== //
		/////////////////////////////////////////////////////////////////////////////////
		Map<String, String> redirect_map = new HashMap<>();
		redirect_map.put("seq", seq);

		// === #142. 이전글제목, 다음글제목 보기 시작 === //
		redirect_map.put("goBackURL", goBackURL);
		redirect_map.put("searchType", searchType);
		redirect_map.put("searchWord", searchWord);
		// === #142. 이전글제목, 다음글제목 보기 끝 === //

		redirectAttr.addFlashAttribute("redirect_map", redirect_map);
		// redirectAttr.addFlashAttribute("키", 밸류값); 으로 사용하는데 오로지 1개의 데이터만 담을 수 있으므로
		// 여러개의 데이터를 담으려면 Map 을 사용해야 한다.

		mav.setViewName("redirect:/notice/noticeView.bibo");

		return mav;
	}

	@GetMapping("/notice/noticeEdit.bibo")
	public ModelAndView edit(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		if(loginuser == null) {
			String message = "관리자만 접근 가능합니다";
			//String loc = "javascript:history.back()";
			mav.addObject("message",message);
			mav.setViewName("redirect:/notice/noticeList.bibo");
			//mav.addObject("loc",loc);
		//	mav.setViewName("msg");
			return mav;
		}
		
		if(loginuser.getmIdx() != 0) {
			String message = "관리자만 접근 가능합니다";
		//	String loc = "javascript:history.back()";
			mav.addObject("message",message);
			mav.setViewName("redirect:/notice/noticeList.bibo");
			//	mav.addObject("loc",loc);
		//	mav.setViewName("msg");
			return mav;
		}
		String seq = request.getParameter("seq");
		String message = "";
		try {
		//	System.out.println("seq: " + seq); // 디버깅 로그 추가
			Integer.parseInt(seq);
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("nidx", seq);
			NoticeDTO noticedto = service.getView_no_increase_readCount(paraMap);
			//	System.out.println("noticedto: " + noticedto); // 디버깅 로그 추가
			if (noticedto == null) {
				message = "글 수정이 불가합니다";
				mav.addObject("message", message);
				mav.setViewName("redirect:/notice/noticeList.bibo");
				return mav;
			} else {
//				HttpSession session = request.getSession();
//				MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
//
//				System.out.println("loginuser: " + loginuser); // 디버깅 로그 추가
//
//				System.out.println("loginuser: " + loginuser);
//				if (loginuser != null) {
//					System.out.println("loginuser.getMIdx(): " + loginuser.getmIdx());
//					System.out.println("loginuser.getUserid(): " + loginuser.getUserid());
//				}
//				System.out.println("noticedto: " + noticedto);
//				if (noticedto != null) {
//					System.out.println("noticedto.getUserid(): " + noticedto.getUserid());
//				}

				// 관리자(mIdx가 0)이거나 글 작성자인 경우 수정 허용
				if (loginuser.getmIdx() == 0 || loginuser.getUserid().equals(noticedto.getUserid())) {
					mav.addObject("noticedto", noticedto);
					mav.setViewName("notice/noticeEdit.tiles");
					return mav;

				} else {
					message = "수정 권한이 없습니다";
					mav.addObject("message", message);
					mav.setViewName("redirect:/notice/noticeList.bibo");
					return mav;
				}
			}
		} catch (NumberFormatException e) {
			message = "유효하지 않은 글 번호입니다. 목록으로 돌아갑니다.";
			mav.addObject("message", message);
			mav.setViewName("redirect:/notice/noticeList.bibo");
			return mav;
		}
	}

	@PostMapping("/notice/editEnd.bibo")
	public ModelAndView isadmin_editEnd(ModelAndView mav, NoticeDTO noticedto, HttpServletRequest request, MultipartHttpServletRequest mrequest) {
	    
	    // 기존 공지사항 정보 조회
	    Map<String, String> paraMap = new HashMap<>();
	    paraMap.put("nidx", String.valueOf(noticedto.getNidx()));
	    NoticeDTO existingNotice = service.getView_no_increase_readCount(paraMap);

	    // 파일 저장 경로 설정
	    HttpSession session = mrequest.getSession();
	    String root = session.getServletContext().getRealPath("/");
	    String path = root + "resources" + File.separator + "files";

	    // 파일 삭제 여부 설정
	    String deleteFile = request.getParameter("deleteFile");
	    boolean isFileDeleted = false;
	    if ("true".equals(deleteFile)) {
	        isFileDeleted = true;
	    }

	    MultipartFile attach = noticedto.getAttach();

	    try {
	        // Case 1: 파일 변경 없이 나머지만 바꾸는 경우
	        if (!isFileDeleted && (attach == null || attach.isEmpty())) {
	            noticedto.setFilename(existingNotice.getFilename());
	            noticedto.setOrgname(existingNotice.getOrgname());
	            noticedto.setFilesize(existingNotice.getFilesize());
	        } 
	        // Case 2: 기존에 있던 첨부된 파일만 삭제하는 경우
	        else if (isFileDeleted) {
	            if (existingNotice.getFilename() != null) {
	                fileManager.doFileDelete(existingNotice.getFilename(), path);
	            }
	            noticedto.setFilename(null);
	            noticedto.setOrgname(null);
	            noticedto.setFilesize(null);
	        } 
	        // Case 3: 파일 변경이 있는 경우
	        else if (attach != null && !attach.isEmpty()) {
	            // 기존 파일 삭제
	            if (existingNotice.getFilename() != null) {
	                fileManager.doFileDelete(existingNotice.getFilename(), path);
	            }
	            // 새 파일 업로드
	            String newFileName = fileManager.doFileUpload(attach.getBytes(), attach.getOriginalFilename(), path);
	            noticedto.setFilename(newFileName);
	            noticedto.setOrgname(attach.getOriginalFilename());
	            noticedto.setFilesize(String.valueOf(attach.getSize()));
	        }

	        // 공지사항 수정
	        int n = service.edit(noticedto);

	        if (n == 1) {
	            mav.addObject("message", "글 수정 성공!!");
	            mav.addObject("loc", request.getContextPath() + "/notice/view.bibo?nidx=" + noticedto.getNidx());
	        } else {
	            mav.addObject("message", "글 수정 실패");
	            mav.addObject("loc", "javascript:history.back()");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.addObject("message", "파일 처리 중 오류가 발생했습니다: " + e.getMessage());
	        mav.addObject("loc", "javascript:history.back()");
	    }

	    mav.setViewName("msg");
	    return mav;
	}
	
	
	
	
	
	
	// 첨부파일 다운로드 하기
	@GetMapping("/notice/download.bibo")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String nidx = request.getParameter("nidx");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("nidx", nidx);

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = null;
		try {
			Integer.parseInt(nidx);
			NoticeDTO noticedto = service.getView_no_increase_readCount(paraMap);

			if (noticedto == null || noticedto.getFilename() == null) {
				out = response.getWriter();
				out.println(
						"<script type='text/javascript'>alert('존재하지 않는 글번호 이거나 첨부파일이 없으므로 파일다운로드가 불가합니다.'); history.back();</script>");
				return;
			} else {
				String fileName = noticedto.getFilename();
				String orgFilename = noticedto.getOrgname();

				HttpSession session = request.getSession();
				String root = session.getServletContext().getRealPath("/");
				String path = root + "resources" + File.separator + "files";

				boolean flag = fileManager.doFileDownload(fileName, orgFilename, path, response);

				if (!flag) {
					out = response.getWriter();
					out.println("<script type='text/javascript'>alert('파일다운로드가 실패되었습니다.'); history.back();</script>");
				}
			}
		} catch (NumberFormatException | IOException e) {
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('파일다운로드가 불가합니다.'); history.back();</script>");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	@PostMapping("/notice/delEnd.bibo")
	public ModelAndView delEnd(HttpServletRequest request, ModelAndView mav) {
		String nidx = request.getParameter("nidx");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("nidx", nidx);

		NoticeDTO noticedto = service.getView_no_increase_readCount(paraMap);
		String fileName = noticedto.getFilename();

		if (fileName != null && !"".equals(fileName)) {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";

			paraMap.put("path", path);
			paraMap.put("fileName", fileName);
		}

		int n = service.del(paraMap);

		if (n == 1) {
			mav.addObject("message", "글 삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/notice/noticeList.bibo");
		} else {
			mav.addObject("message", "글 삭제 실패!!");
			mav.addObject("loc", "javascript:history.back()");
		}
		mav.setViewName("msg");

		return mav;
	}
}
