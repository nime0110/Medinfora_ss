package com.spring.app.question.controller;

import java.io.File;
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

import com.spring.app.common.FileManager;
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
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountMark", "yes");
	
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchSubject", searchSubject);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		int totalCount = 0;        // 총 게시물 건수
		int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수 
		int currentPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함. 
		int totalPage = 0;
		
		try {
			totalPage = questionservice.totalquestion(paraMap);
			
			System.out.println("확인용 totalCount "+totalCount);
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
			
			if(str_currentPageNo == null) {
				currentPageNo = 1;
			}
			else {
				currentPageNo = Integer.parseInt(str_currentPageNo);
				
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
					qdtoMap.put("totalPage", totalPage);
					
					mav.addObject("qdtoMap", qdtoMap);
					
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		mav.setViewName("question/questionList.tiles");
		
		return mav;
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
		
		String imgsrc = "";
		
		
		try {
			MultipartFile filesrc = qdto.getFilesrc();
			
			System.out.println(filesrc);
			
			if(filesrc != null) {
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
				
				System.out.println(newFileName);
				// 202406271207351276805640675800.png
				System.out.println(originalFilename);
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
				System.out.println(fileSize);
				
				// imgsrc 에는 모두 합친문자열로 저장 구분자는 / 로 간다.
				if(newFileName != null) {
					imgsrc = newFileName+"/"+originalFilename+"/"+String.valueOf(fileSize);
				}
				else {
					imgsrc = "";
				}
				
				qdto.setImgsrc(imgsrc);
				
			}// end of if---
			else {
				imgsrc = "";
				qdto.setImgsrc(imgsrc);
			}
			
			
			
			int n = 0;
			
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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		return mav;
	}
	
}
