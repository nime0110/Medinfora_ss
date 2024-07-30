package com.spring.app.question.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.FileManager;
import com.spring.app.domain.AddQnADTO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.question.service.QuestionService;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionservice;
	
	@Autowired
	private FileManager fileManager;
	
	
	@RequestMapping(value="/questionList.bibo")
	public ModelAndView questionList(ModelAndView mav, HttpServletRequest request
									,@RequestParam(value="subject", defaultValue = "0")String searchSubject
									,@RequestParam(value="type", defaultValue = "z")String searchType
									,@RequestParam(value="word", defaultValue = "")String searchWord
									,@RequestParam(value="PageNo", defaultValue = "1")String str_currentPageNo) {
		
		List<MediQDTO> qList = null;
		// System.out.println(str_currentPageNo);
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountMark", "yes");
	
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchSubject", searchSubject);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("userid", "");
		paraMap.put("midx", "");
		
		int totalCount = 0;        // 총 게시물 건수
		int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수 
		int currentPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함. 
		int totalPage = 0;
		
		try {
			totalCount = questionservice.totalquestion(paraMap);
			
			// System.out.println("확인용 totalCount "+totalCount);
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
			
			if(str_currentPageNo == null) {
				currentPageNo = 1;
			}
			else {
				try {
					currentPageNo = Integer.parseInt(str_currentPageNo);
				}catch(NumberFormatException e) {
					currentPageNo = 1;
				}
				
				if(currentPageNo < 1 || currentPageNo > totalPage) {
					currentPageNo = 1;
				}
				
				int startRno = ((currentPageNo - 1) * sizePerPage) + 1; // 시작 행번호 
				int endRno = startRno + sizePerPage - 1; // 끝 행번호
				
				paraMap.put("startRno", String.valueOf(startRno));
				paraMap.put("endRno", String.valueOf(endRno));
				
				qList = questionservice.totalquestionList(paraMap);

				Map<String, Object> qdtoMap = new HashMap<>();
				qdtoMap.put("qList", qList);
				qdtoMap.put("totalCount", totalCount);
				
				// 페이지바 관련
				int blockSize = 10; // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수이다.
				int loop = 1;
				int pageNo = ((currentPageNo - 1)/blockSize) * blockSize + 1;
				
				qdtoMap.put("blockSize", blockSize);
				qdtoMap.put("loop", loop);
				qdtoMap.put("pageNo", pageNo);
				qdtoMap.put("currentPageNo", currentPageNo);
				qdtoMap.put("totalPage", totalPage);
				
				mav.addObject("qdtoMap", qdtoMap);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		mav.setViewName("question/questionList.tiles");
		
		return mav;
	}
	
	
	@ResponseBody
	@GetMapping("/questionSearch.bibo")
	public String questionSearch(HttpServletRequest request
								,@RequestParam(value="subject", defaultValue = "0")String searchSubject
								,@RequestParam(value="type", defaultValue = "z")String searchType
								,@RequestParam(value="word", defaultValue = "")String searchWord
								,@RequestParam(value="PageNo", defaultValue = "1")String str_currentPageNo) {
		
		// System.out.println(searchSubject);
		// System.out.println(searchWord);
		// System.out.println(searchType);
		// System.out.println(str_currentPageNo);
		
		List<MediQDTO> qList = null;
		// System.out.println(str_currentPageNo);
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountMark", "yes");
	
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchSubject", searchSubject);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("userid", "");
		paraMap.put("midx", "");
		
		int totalCount = 0;        // 총 게시물 건수
		int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수 
		int currentPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함. 
		int totalPage = 0;
		
		JSONObject jsonObj = new JSONObject();
		
		try {
			totalCount = questionservice.totalquestion(paraMap);
			
			// System.out.println("확인용 totalCount "+totalCount);
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
			
			if(str_currentPageNo == null) {
				currentPageNo = 1;
			}
			else {
				try {
					currentPageNo = Integer.parseInt(str_currentPageNo);
				}catch(NumberFormatException e) {
					currentPageNo = 1;
				}
				
				if(currentPageNo < 1 || currentPageNo > totalPage) {
					currentPageNo = 1;
				}
				
				int startRno = ((currentPageNo - 1) * sizePerPage) + 1; // 시작 행번호 
				int endRno = startRno + sizePerPage - 1; // 끝 행번호
				
				paraMap.put("startRno", String.valueOf(startRno));
				paraMap.put("endRno", String.valueOf(endRno));
				
				qList = questionservice.totalquestionList(paraMap);
				
				if(qList != null) {
					
					Map<String, Object> qdtoMap = new HashMap<>();
					qdtoMap.put("qList", qList);
					qdtoMap.put("totalCount", totalCount);
					
					// 페이지바 관련
					int blockSize = 10; // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수이다.
					int loop = 1;
					int pageNo = ((currentPageNo - 1)/blockSize) * blockSize + 1;
					
					qdtoMap.put("blockSize", blockSize);
					qdtoMap.put("loop", loop);
					qdtoMap.put("pageNo", pageNo);
					qdtoMap.put("currentPageNo", currentPageNo);
					qdtoMap.put("totalPage", totalPage);
					
					qdtoMap.put("subject", searchSubject);
					qdtoMap.put("type", searchType);
					qdtoMap.put("word", searchWord);
					
					
					jsonObj.put("qdtoMap", qdtoMap);
					
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
	
	
	
	@GetMapping("/questionWrite.bibo")
	public ModelAndView isLogin_questionWrite(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser.getmIdx() != 1) {
			mav.addObject("message", "일반유저만 질문등록이 가능합니다.");
			mav.addObject("loc", request.getContextPath()+"/questionList.bibo");
			
			mav.setViewName("msg");
		}
		else {
			mav.setViewName("question/questionWrite.tiles");
		}
		
		return mav;
	}
	
	
	@PostMapping("/questionWriteEnd.bibo")
	public ModelAndView isLogin_questionWriteEnd(MediQDTO qdto, HttpServletRequest request
												, HttpServletResponse response
												, MultipartHttpServletRequest mrequest
												, ModelAndView mav) {
		
		String qidx = qdto.getQidx();
		
		String imgsrc = qdto.getImgsrc();
		
		System.out.println("imgsrc : "+imgsrc);
		
		try {
			MultipartFile filesrc = qdto.getFilesrc();
			
			System.out.println(filesrc);
			
			if(filesrc != null && (imgsrc == null || imgsrc.isBlank())) {
				System.out.println(filesrc);
				// MultipartFile[field="filesrc", filename=귀여미인형.png, contentType=image/png, size=386470]
				
				HttpSession session = mrequest.getSession(); 
				String root = session.getServletContext().getRealPath("/");  
				
				System.out.println(root);  
				// C:\NCS\workspace_spring_framework\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Medinfora\
				
				String path = root+"resources"+File.separator+"files";
				
				
				String newFileName = "";
				// WAS(톰캣)의 디스크에 저장될 파일명
				
				byte[] bytes = null;
				// 첨부파일의 내용물을 담는 것
				
				long fileSize = 0;
				// 첨부파일의 크기
				
				
					
				bytes = filesrc.getBytes();
				// 첨부파일의 내용물을 읽어오는 것
				
				String originalFilename = filesrc.getOriginalFilename();
				// attach.getOriginalFilename() 이 첨부파일명의 파일명(예: 강아지.png) 이다.
				// System.out.println("~~~ 확인용 originalFilename => " + originalFilename); 
		        // ~~~ 확인용 originalFilename => LG_싸이킹청소기_사용설명서.pdf
				
				newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
				// 첨부되어진 파일을 업로드 하는 것이다. (파일내용, 원래파일명, 경로)
				
				System.out.println("newFileName : "+newFileName);
				// 202406271207351276805640675800.png
				System.out.println("originalFilename : "+originalFilename);
				// 귀여미인형.png
				
			/*
		     	3. BoardVO boardvo 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기 
			*/
				//boardvo.setFileName(newFileName);
				// WAS(톰캣)에 저장된 파일명(202406271207351276805640675800.png)
				
				//boardvo.setOrgFilename(originalFilename);
				// 게시판 페이지에서 첨부된 파일(귀여운인형.png)을 보여줄 때 사용.
		        // 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
				
				fileSize = filesrc.getSize();  // 첨부파일의 크기(단위는 byte 임)
				//boardvo.setFileSize(String.valueOf(fileSize));
				System.out.println("fileSize : "+fileSize);
				
				// imgsrc 에는 모두 합친문자열로 저장 구분자는 / 로 간다.
				if(newFileName != null) {
					imgsrc = newFileName+"/"+originalFilename+"/"+String.valueOf(fileSize);
				}
				else {
					imgsrc = "";
				}
				
				qdto.setImgsrc(imgsrc);
				
			}// end of if---
			else { // 파일이 없는 경우
				
				if(imgsrc == null || imgsrc=="") {
					imgsrc = "";
					qdto.setImgsrc(imgsrc);
				}
			}
			
			
			
			int n = 0;
			
			if(qidx == null) {
				n = questionservice.questionWriteEnd(qdto);
				
				if(n==1) {
					
					mav.addObject("message", "질문등록을 성공하였습니다.");
					mav.addObject("loc", request.getContextPath()+"/questionList.bibo");
					
					mav.setViewName("msg");
				    //  /list.action 페이지로 redirect(페이지이동)해라는 말이다.
				}
				else {
					mav.addObject("message", "질문등록을 실패하였습니다.");
					mav.addObject("loc", request.getContextPath()+"/questionWrite.bibo");
					
					mav.setViewName("msg");
				}
			}
			else {
				n = questionservice.questionUpdate(qdto);
				
				if(n==1) {
					
					mav.addObject("message", "질문수정을 성공하였습니다.");
					mav.addObject("loc", request.getContextPath()+"/questionView.bibo?qidx="+qidx);
					
					mav.setViewName("msg");
				    //  /list.action 페이지로 redirect(페이지이동)해라는 말이다.
				}
				else {
					mav.addObject("message", "질문등록을 실패하였습니다.");
					mav.addObject("loc", request.getContextPath()+"/questionView.bibo?qidx="+qidx);
					
					mav.setViewName("msg");
				}
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		return mav;
	}
	
	
	
	// 질문 조회
	@GetMapping("/questionView.bibo")
	public String questionView(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String readCountMark = String.valueOf(session.getAttribute("readCountMark"));
		
		if(readCountMark == null || "null".equalsIgnoreCase(readCountMark)) {
			readCountMark = "no";
		}
		
		String redirect = "question/questionView.tiles";
		
		Map<String, Object> totalcontent = new HashMap<>();
		
		try {
		
		// 가져올 값
		/*
		 	1. 글관련
		 	작성자
		 	구분
		 	작성일 > 형식 yyyy-mm-dd 로 해야함
		 	진행상태 > 답변수가 1개 이상일 경우 답변완료로 진행 아니면 답변중
		 	조회수 > 리스트에서 전달해준 mark가 없으면 조회수 증가 x
		 	파일정보 > 다운로드 가능해야함(단, 의료회원 및 작성자, 관리자만 가능하도록)
		 	
		 	2.답변내용(있는경우 없는경우로 나눠서 해야함)
		 	- 질문번호를 넣어서 가져옴(리스트로 가져와야함)
		 	
		 	3.추가질문 또는 답변 내용(2번과 동일)
		 	- 답변번호를 넣어서 가져옴(리스트로 가져와야함)
		 	

		 	
		*/
		
			// 먼저 글번호를 가져오자
			String str_qidx = request.getParameter("qidx");
			System.out.println(str_qidx);
			
			if(str_qidx == null) {
				redirect = "redirect:/questionList.bibo";
				return redirect;
			}

			int qidx = Integer.parseInt(str_qidx);
			
			if("yes".equalsIgnoreCase(readCountMark)) {
				questionservice.viewCountIncrease(qidx);
			}
			
			// 글정보 조회
			MediQDTO qdto = questionservice.questionView(qidx);
			
			if(qdto == null) {
				redirect = "redirect:/questionList.bibo";
			}
			else {
				
				
				totalcontent.put("qdto", qdto);
			}
			
			// 답변 조회
			List<MediADTO> adtoList = questionservice.answerView(qidx);
	/*		
			for(MediADTO adto : adtoList) {
				System.out.println(adto.getAddqnadtoList());
				List<AddQnADTO> addqnadtoList = adto.getAddqnadtoList();
				
				for(AddQnADTO asb : addqnadtoList) {
					System.out.println(asb.getAidx());
				}
			}
	*/		
			
			totalcontent.put("adtoList", adtoList);
			
			request.setAttribute("totalcontent", totalcontent);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			redirect = "redirect:/questionList.bibo";
		}
		
		session.removeAttribute("readCountMark");
		
		return redirect;
	}
	
	
	
	@ResponseBody
	@PostMapping("/answerWrite.bibo")
	public String answerWrite(MediADTO mdto) {
		
		JSONObject jsonObj = new JSONObject();
		
		// qnacnt 는 기본값 0 이므로 추가답변 추가될 때마다 +1 해야함
		
		int result = questionservice.answerWrite(mdto);
		
		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	
	@ResponseBody
	@PostMapping("/addQAUpload.bibo")
	public String addQAUpload(AddQnADTO addqadto) {
		
		JSONObject jsonObj = new JSONObject();
		
		// qnacnt 는 기본값 0 이므로 추가질문 추가될 때마다 +1 해야함
		// 추가질문시 넘겨받은 cntnum 값은 qnanum+1 이다.
		// 추가답변시 넘겨받은 cntnum 은 추가질문의 cntnum 이다.
		
		int result = questionservice.addqaUpload(addqadto);
		
		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	
	
	@ResponseBody
	@PostMapping("/addQAUpdate.bibo")
	public String addQAUpdate(AddQnADTO addqadto) {
		
		JSONObject jsonObj = new JSONObject();
		
		int result = questionservice.addqaUpdate(addqadto);
		
		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	@ResponseBody
	@GetMapping("/addQADelete.bibo")
	public String addQADelete(AddQnADTO addqadto) {
		
		JSONObject jsonObj = new JSONObject();
		
		int result = questionservice.addqaDelete(addqadto);
		
		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	
	@ResponseBody
	@PostMapping("/answerUdate.bibo")
	public String answerUdate(MediADTO adto, HttpServletRequest request) {
		
		JSONObject jsonObj = new JSONObject();
		int result = 0;
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser.getUserid().equalsIgnoreCase(adto.getUserid())) {
			result = questionservice.answerUpdate(adto);
		}

		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	@ResponseBody
	@PostMapping("/answerDelete.bibo")
	public String answerDelete(MediADTO adto, HttpServletRequest request) {
		
		JSONObject jsonObj = new JSONObject();
		int result = 0;
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser.getUserid().equalsIgnoreCase(adto.getUserid())) {
			result = questionservice.answerDelete(adto);
			
			if(result >= 1) {
				result = 1;
			}
		}

		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	
	@ResponseBody
	@PostMapping("/questionDelete.bibo")
	public String questionDelete(MediQDTO qdto, HttpServletRequest request) {
		
		JSONObject jsonObj = new JSONObject();
		int result = 0;
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser.getUserid().equalsIgnoreCase(qdto.getUserid())) {
			result = questionservice.questionDelete(qdto.getQidx());

		}

		jsonObj.put("result", result);
		
		return jsonObj.toString();
	}
	
	
	@RequestMapping(value="/questionUpdate.bibo")
	public String questionUpdate(MediQDTO qdto, HttpServletRequest request) {
		
		String method = request.getMethod();
		String redirect = "question/questionUpdate.tiles";
		
		if("post".equalsIgnoreCase(method)) {
			System.out.println("수정하러 와쪄염");
			
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			String qidx = qdto.getQidx();
			if(loginuser.getUserid().equalsIgnoreCase(qdto.getUserid())) {
				// 글정보 가져오기
				MediQDTO originqdto = questionservice.questionView(Integer.parseInt(qidx));
				request.setAttribute("originqdto", originqdto);
			}
			else {
				redirect = "redirect:/questionView.bibo?qidx="+qdto.getQidx();
			}
		}
		else {
			redirect = "redirect:/questionList.bibo";
		}
		
		return redirect;
	}
	
	
	// 첨부파일 다운
	@GetMapping("/question/filedownload.bibo")
	public void filedownload(HttpServletRequest request, HttpServletResponse response){
		
		String filename = request.getParameter("filename");
		String originFilename = request.getParameter("originFilename");
		// String qidx = request.getParameter("qidx");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = null;
		
		try {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
	
			boolean flag = fileManager.doFileDownload(filename, originFilename, path, response);
	
			if (!flag) {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('파일이 존재하지 않습니다.'); location.reload();</script>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
