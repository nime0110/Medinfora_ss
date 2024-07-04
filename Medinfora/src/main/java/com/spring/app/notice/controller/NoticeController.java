package com.spring.app.notice.controller;

import java.io.File;
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
	
	@GetMapping("/notice.bibo")
	public String notice() {
		
		return "notice";
	}

    // 공지사항 글 쓰기 폼 페이지 요청
    @GetMapping("/notice/noticeWrite.bibo")
    public ModelAndView isLogin_noticeWrite(HttpServletRequest request, HttpServletResponse response,
            ModelAndView mav) {

        mav.setViewName("notice/noticeWrite.tiles");
        return mav;
    }

    @PostMapping("/noticeWriteEnd.bibo")
    public ModelAndView isLogin_noticeWriteEnd(Map<String, String> paraMap, ModelAndView mav, NoticeDTO noticedto,
            MultipartHttpServletRequest mrequest) {

        MultipartFile attach = noticedto.getAttach();

        if (attach != null) {
            HttpSession session = mrequest.getSession();
            String root = session.getServletContext().getRealPath("/");

            String path = root + "resources" + File.separator + "files";

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
     
        int n = 0;

        if (attach.isEmpty()) {

            n = service.noticeWrite(noticedto);

        } else {
            n = service.add_noticeWrite(noticedto);
        }

        if (n == 1) {
            mav.setViewName("redirect:/notice/noticeList.bibo");
        } else {
            mav.setViewName("/notice/noticeWrite.tiles");
        }

   
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
        //System.out.println("noticeListdto size: " + noticeListdto.size());
        for (NoticeDTO notice : noticeListdto) {
         //   System.out.println("Notice: " + notice.getTitle());
        }

        mav.addObject("noticeListdto", noticeListdto);
        mav.addObject("paraMap", paraMap);

        String pageBar = Myutil.makePageBar(currentShowPageNo, sizePerPage, totalPage, "noticeList.bibo", searchType,
                searchWord);
        mav.addObject("pageBar", pageBar);

        String goBackURL = Myutil.getCurrentURL(request);
        mav.addObject("goBackURL", goBackURL);

        mav.setViewName("notice/noticeList.tiles");
        return mav;
    }

    @PostMapping("/editEnd.bibo")
    public ModelAndView editEnd(ModelAndView mav, NoticeDTO noticedto, HttpServletRequest request) {

//        int n = service.edit(noticedto);
//
//        if (n == 1) {
//            mav.addObject("message", "글 수정 성공!!");
//            mav.addObject("loc", request.getContextPath() + "/view.action?seq=" + noticedto.getSeq());
//            mav.setViewName("msg");
//        }
        return mav;
    }

    @GetMapping("/del.bibo")
    public ModelAndView isLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

        String seq = request.getParameter("seq");

        String message = "";

//        try {
            Integer.parseInt(seq);

            // 글 삭제해야 할 글 1개 내용가져오기
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("seq", seq);

            // NoticeDTO noticedto = service.getView_no_increase_readCount(paraMap);
            // 글 조회수 증가는 없고 단순히 글 1개만 조회를 해오는 것

//            if (noticedto == null) {
//                message = "글 삭제가 불가합니다.";
//            } else {
//                HttpSession session = request.getSession();
//                MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
//
//                if (!loginuser.getUserid().equals(noticedto.getFk_userid())) {
//                    message = "다른 사용자의 글은 삭제가 불가합니다.";
//                } else {
//                    // 자신의 글을 삭제할 경우
//                    // 가져온 1개글을 글삭제할 폼이 있는 view 단으로 보내준다.
//                    mav.addObject("noticedto", noticedto);
//                    mav.setViewName("notice/del.tiles1");
//
//                    return mav;
//                }
//            }
//
//        } catch (NumberFormatException e) {
//            message = "글 삭제가 불가합니다.";
//        }
//
//        String loc = "javascript:history.back()";
//        mav.addObject("message", message);
//        mav.addObject("loc", loc);
//
//        mav.setViewName("msg");

        return mav;

    }

    @PostMapping("/delEnd.bibo")
    public ModelAndView delEnd(ModelAndView mav, HttpServletRequest request) {
        String seq = request.getParameter("seq");

//        int n = service.del(seq);
//
//        if (n == 1) {
//            mav.addObject("message", "글 삭제 성공!!");
//            mav.addObject("loc", request.getContextPath() + "/noticeList.bibo");
//            mav.setViewName("msg");
//        }

        return mav;
    }
    
	// === #62. 글1개를 보여주는 페이지 요청 === //
 //	@GetMapping("/view.action")
	@RequestMapping("/view.bibo") // === #133. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함.  
	public ModelAndView view(ModelAndView mav, HttpServletRequest request) {
		String nidx = request.getParameter("nidx");
		System.out.println("확인용 nidx" + nidx);
		
		mav.setViewName("notice/noticeView.tiles");
		
		return mav;
		/*
		 * String seq = ""; String goBackURL = ""; String searchType = ""; String
		 * searchWord = ""; /// Map<String, ?> inputFlashMap =
		 * RequestContextUtils.getInputFlashMap(request); // redirect 되어서 넘어온 데이터가 있는지
		 * 꺼내어 와본다.
		 * 
		 * if(inputFlashMap != null) { // redirect 되어서 넘어온 데이터가 있다 라면
		 * 
		 * @SuppressWarnings("unchecked") // 경고 표시를 하지 말라는 뜻이다. Map<String, String>
		 * redirect_map = (Map<String, String>) inputFlashMap.get("redirect_map"); //
		 * "redirect_map" 값은 /view_2.action 에서 redirectAttr.addFlashAttribute("키", 밸류값);
		 * 을 할때 준 "키" 이다. // "키" 값을 주어서 redirect 되어서 넘어온 데이터를 꺼내어 온다. // "키" 값을 주어서
		 * redirect 되어서 넘어온 데이터의 값은 Map<String, String> 이므로 Map<String, String> 으로
		 * casting 해준다.
		 * 
		 * 
		 * // System.out.println("~~~ 확인용 seq : " + redirect_map.get("seq")); seq =
		 * redirect_map.get("seq");
		
			// === #143. 이전글제목, 다음글제목 보기 시작 === //
			searchType = redirect_map.get("searchType");
			
			try {
			     searchWord = URLDecoder.decode(redirect_map.get("searchWord"), "UTF-8"); // 한글데이터가 포함되어 있으면 반드시 한글로 복구해 주어야 한다. 
			     goBackURL = URLDecoder.decode(redirect_map.get("goBackURL"), "UTF-8");   // 한글데이터가 포함되어 있으면 반드시 한글로 복구해 주어야 한다.
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			} 
			
		//  System.out.println("~~~ 확인용 searchType : " + searchType);
		//  System.out.println("~~~ 확인용 searchWord : " + searchWord);
		//  System.out.println("~~~ 확인용 goBackURL : " + goBackURL);
		//  === #143. 이전글제목, 다음글제목 보기 끝 === //
		    
		}
		 */
		////////////////////////////////////////////////////////////
		
//		else { // redirect 되어서 넘어온 데이터가 아닌 경우  
//			
//			// == 조회하고자 하는 글번호 받아오기 ==
//        	
//        	// 글목록보기인 /list.action 페이지에서 특정 글제목을 클릭하여 특정글을 조회해온 경우  
//        	// 또는 
//        	// 글목록보기인 /list.action 페이지에서 특정 글제목을 클릭하여 특정글을 조회한 후 새로고침(F5)을 한 경우는 원본이 form 을 사용해서 POST 방식으로 넘어온 경우이므로 "양식 다시 제출 확인" 이라는 대화상자가 뜨게 되며 "계속" 이라는 버튼을 클릭하면 이전에 입력했던 데이터를 자동적으로 입력해서 POST 방식으로 진행된다. 그래서  request.getParameter("seq"); 은 null 이 아닌 번호를 입력받아온다.     
//			// 그런데 "이전글제목" 또는 "다음글제목" 을 클릭하여 특정글을 조회한 후 새로고침(F5)을 한 경우는 원본이 /view_2.action 을 통해서 redirect 되어진 경우이므로 form 을 사용한 것이 아니라서 "양식 다시 제출 확인" 이라는 alert 대화상자가 뜨지 않는다. 그래서  request.getParameter("seq"); 은 null 이 된다. 
//			seq = request.getParameter("seq");
//		 // System.out.println("~~~~~~ 확인용 seq : " + seq);
//        	// ~~~~~~ 확인용 seq : 213
//        	// ~~~~~~ 확인용 seq : null
//			
//			
//			// === #134. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함.  
//			goBackURL = request.getParameter("goBackURL");
//		//	System.out.println("~~~ 확인용(view.action) goBackURL : " + goBackURL);
//		/*
//		         잘못된 것(get방식일 경우)
//		  	~~~ 확인용(view.action) goBackURL : /list.action?searchType=subject
//			
//			올바른 것(post방식일 경우)
//			~~~ 확인용(view.action) goBackURL : /list.action?searchType=subject&searchWord=정화&currentShowPageNo=3
//		*/	
//			
//		 // >>> 글목록에서 검색되어진 글내용일 경우 이전글제목, 다음글제목은 검색되어진 결과물내의 이전글과 다음글이 나오도록 하기 위한 것이다.  시작  <<< // 
//			searchType = request.getParameter("searchType");
//			searchWord = request.getParameter("searchWord");
//			
//			if(searchType == null) {
//				searchType = "";
//			}
//			
//			if(searchWord == null) {
//				searchWord = "";
//			}
//			
//		//	System.out.println("~~~ 확인용(view.action) searchType : " + searchType);
//			// ~~~ 확인용(view.action) searchType : 
//			// ~~~ 확인용(view.action) searchType : subject
//			
//		//	System.out.println("~~~ 확인용(view.action) searchWord : " + searchWord);
//			// ~~~ 확인용(view.action) searchWord :
//			// ~~~ 확인용(view.action) searchWord : java
//			
//		  // >>> 글목록에서 검색되어진 글내용일 경우 이전글제목, 다음글제목은 검색되어진 결과물내의 이전글과 다음글이 나오도록 하기 위한 것이다.  끝  <<< //
//		}
//		
//		mav.addObject("goBackURL", goBackURL);
//		
//		try {
//			Integer.parseInt(seq);
//		 /* 
//		     "이전글제목" 또는 "다음글제목" 을 클릭하여 특정글을 조회한 후 새로고침(F5)을 한 경우는   
//		         원본이 /view_2.action 을 통해서 redirect 되어진 경우이므로 form 을 사용한 것이 아니라서   
//		     "양식 다시 제출 확인" 이라는 alert 대화상자가 뜨지 않는다. 
//		         그래서  request.getParameter("seq"); 은 null 이 된다. 
//		         즉, 글번호인 seq 가 null 이 되므로 DB 에서 데이터를 조회할 수 없게 된다.     
//		         또한 seq 는 null 이므로 Integer.parseInt(seq); 을 하면  NumberFormatException 이 발생하게 된다. 
//		  */
//			
//			HttpSession session = request.getSession();
//			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
//			
//			String login_userid = null;
//			if(loginuser != null) {
//				login_userid = loginuser.getUserid();
//				// login_userid 는 로그인 되어진 사용자의 userid 이다.
//			}
//			
//			Map<String, String> paraMap = new HashMap<>();
//			paraMap.put("seq", seq);
//			paraMap.put("login_userid", login_userid);
//			
//		// >>> 글목록에서 검색되어진 글내용일 경우 이전글제목, 다음글제목은 검색되어진 결과물내의 이전글과 다음글이 나오도록 하기 위한 것이다.  시작  <<< //
//			paraMap.put("searchType", searchType);
//			paraMap.put("searchWord", searchWord);
//		// >>> 글목록에서 검색되어진 글내용일 경우 이전글제목, 다음글제목은 검색되어진 결과물내의 이전글과 다음글이 나오도록 하기 위한 것이다. 끝  <<< //	
//			
//			// === #68. !!! 중요 !!! 
//	        //     글1개를 보여주는 페이지 요청은 select 와 함께 
//			//     DML문(지금은 글조회수 증가인 update문)이 포함되어져 있다.
//			//     이럴경우 웹브라우저에서 페이지 새로고침(F5)을 했을때 DML문이 실행되어
//			//     매번 글조회수 증가가 발생한다.
//			//     그래서 우리는 웹브라우저에서 페이지 새로고침(F5)을 했을때는
//			//     단순히 select만 해주고 DML문(지금은 글조회수 증가인 update문)은 
//			//     실행하지 않도록 해주어야 한다. !!! === //
//			
//			// 위의 글목록보기 #69. 에서 session.setAttribute("readCountPermission", "yes"); 해두었다.
//			NoticeDTO noticedto = null;
//			
//			if("yes".equals( (String)session.getAttribute("readCountPermission") )) {
//			// 글목록보기인 /list.action 페이지를 클릭한 다음에 특정글을 조회해온 경우이다.
//				
//				noticedto = service.getView(paraMap);
//				// 글 조회수 증가와 함께 글 1개를 조회를 해오는 것
//			//	System.out.println("~~ 확인용 글내용 : " + boardvo.getContent());
//				
//				session.removeAttribute("readCountPermission");
//				// 중요함!! session 에 저장된 readCountPermission 을 삭제한다. 
//			}
//			
//			else {
//				// 글목록에서 특정 글제목을 클릭하여 본 상태에서
//			    // 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
//			//	System.out.println("글목록에서 특정 글제목을 클릭하여 본 상태에서 웹브라우저에서 새로고침(F5)을 클릭한 경우");
//				
//				noticedto = service.getView_no_increase_readCount(paraMap);
//				// 글 조회수 증가는 없고 단순히 글 1개만 조회를 해오는 것
//				
//			 // 또는 redirect 해주기 (예 : 버거킹 www.burgerking.co.kr 메뉴소개)
//			 /*	
//				mav.setViewName("redirect:/list.action");
//				return mav;
//			 */	
//				
//				if(noticedto == null) {
//					mav.setViewName("redirect:/notice/noticeList.tiles");
//					return mav;
//				}
//			}
//			
//			mav.addObject("noticedto", noticedto);
//			
//			// === #140. 이전글제목, 다음글제목 보기 === //
//			mav.addObject("paraMap", paraMap); 
//			
//			mav.setViewName("notice/noticeView.tiles");
//			//  /WEB-INF/views/tiles1/board/view.jsp 파일을 생성한다.
//			
//		} catch (NumberFormatException e) {
//			mav.setViewName("redirect:/noticeList.action");
//		}
		
//		return mav;
	}
	
	
 //	@GetMapping("/view_2.action")
	@PostMapping("/view_2.bibo")
	public ModelAndView view_2(ModelAndView mav, HttpServletRequest request, RedirectAttributes redirectAttr) {
		
		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("seq");
		
		// === #141. 이전글제목, 다음글제목 보기 시작 === //
		String goBackURL = request.getParameter("goBackURL");
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		/* 
		  redirect:/ 를 할때 "한글데이터는 0에서 255까지의 허용 범위 바깥에 있으므로 인코딩될 수 없습니다" 라는 
		  java.lang.IllegalArgumentException 라는 오류가 발생한다.
		    이것을 방지하려면 아래와 같이 하면 된다.
		*/
		
		try {
			searchWord = URLEncoder.encode(searchWord, "UTF-8");
			goBackURL = URLEncoder.encode(goBackURL, "UTF-8");
			
		//	System.out.println("~~~~ view_2.action 의  URLEncoder.encode(searchWord, \"UTF-8\") : " + searchWord);
            //  ~~~~ view_2.action 의  URLEncoder.encode(searchWord, "UTF-8") : %EC%84%9C%EC%98%81%ED%95%99
			
		//	System.out.println("~~~~ view_2.action 의  URLEncoder.encode(goBackURL, \"UTF-8\") : " + goBackURL);
		    //	~~~~ view_2.action 의  URLEncoder.encode(goBackURL, "UTF-8") : %2Flist.action%3FsearchType%3Dname+searchWord%3D%25EC%2584%259C%25EC%2598%2581%25ED%2595%2599+currentShowPageNo%3D11 
			
		//	System.out.println(URLDecoder.decode(searchWord, "UTF-8")); // URL인코딩 되어진 한글을 원래 한글모양으로 되돌려주는 것임.
		    //	서영학
			
		//	System.out.println(URLDecoder.decode(goBackURL, "UTF-8"));  // URL인코딩 되어진 한글을 원래 한글모양으로 되돌려주는 것임.
		    //  /list.action?searchType=name searchWord=%EC%84%9C%EC%98%81%ED%95%99 currentShowPageNo=11
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// === #141. 이전글제목, 다음글제목 보기 끝 === //
		
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		// ==== redirect(GET방식임) 시 데이터를 넘길때 GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 시작 ==== //
		/////////////////////////////////////////////////////////////////////////////////
		Map<String, String> redirect_map = new HashMap<>();
		redirect_map.put("seq", seq);
		
		// === #142. 이전글제목, 다음글제목 보기 시작 === //
		redirect_map.put("goBackURL", goBackURL);
		redirect_map.put("searchType", searchType);
		redirect_map.put("searchWord", searchWord);
		// === #142. 이전글제목, 다음글제목 보기 끝 === //
		
		redirectAttr.addFlashAttribute("redirect_map", redirect_map);
		// redirectAttr.addFlashAttribute("키", 밸류값); 으로 사용하는데 오로지 1개의 데이터만 담을 수 있으므로 여러개의 데이터를 담으려면 Map 을 사용해야 한다. 
		
		mav.setViewName("redirect:/view.bibo"); // 실제로 redirect:/view.action 은 POST 방식이 아닌 GET 방식이다.
        /////////////////////////////////////////////////////////////////////////////////
		// ==== redirect(GET방식임) 시 데이터를 넘길때 GET 방식이 아닌 POST 방식처럼 데이터를 넘기려면 RedirectAttributes 를 사용하면 된다. 끝 ==== //
		
		return mav;
	}
	
    
    
    
}
