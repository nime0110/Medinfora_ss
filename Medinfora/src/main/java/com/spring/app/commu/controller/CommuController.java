package com.spring.app.commu.controller;



import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.FileManager;
import com.spring.app.commu.service.CommuService;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuFilesDTO;


@Controller
public class CommuController {
	
	//의존객체주입
	@Autowired
	private CommuService service;
	
	@Autowired
	private FileManager fileManager;

	//카테고리 
	public String getCategoryText(String category) {
	    switch (category) {
	        case "1": return "임신·성고민";
	        case "2": return "다이어트·헬스";
	        case "3": return "마음 건강";
	        case "4": return "탈모 톡톡";
	        case "5": return "피부 고민";
	        case "6": return "뼈와 관절";
	        case "7": return "영양제";
	        case "8": return "질환 고민";
	        case "9": return "자유게시판";
	        default: return "";
	    }
	}

	
	// 글 리스트
	@GetMapping(value="/commu/commuList.bibo")
	public ModelAndView commuList(ModelAndView mav, HttpServletRequest request
			,@RequestParam(value="category", defaultValue = "0")String category
			,@RequestParam(value="type", defaultValue = "z")String type
			,@RequestParam(value="word", defaultValue = "")String word
			,@RequestParam(value="currentShowPageNo", defaultValue = "1")String currentShowPageNo) {
		
		int sizePerPage = 10;// 한 페이지당 10개 

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1;
		int endRno = startRno + sizePerPage - 1;

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("category", category);
		paraMap.put("type", type);
		paraMap.put("word", word);
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		List<CommuBoardDTO> CommuBoardList = null;
		
		CommuBoardList = service.getCommuBoardList(paraMap);
		
		int totalCount = service.getCBListTotalCount(paraMap); // 전체개수
		int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);
		//첨부파일이 있을 경우
		List<String> fileSeqList = null;
		fileSeqList = service.getfileSeqList();
		
        // 카테고리 텍스트 변환
        for (CommuBoardDTO cbdto : CommuBoardList) {
            cbdto.setCategory(getCategoryText(cbdto.getCategory()));
        }
		
		mav.addObject("CommuBoardList", CommuBoardList);
		mav.addObject("fileSeqList", fileSeqList);
		mav.addObject("totalPage", totalPage);
		mav.addObject("sizePerPage", sizePerPage);
		mav.addObject("currentShowPageNo", currentShowPageNo);
		
		mav.setViewName("commu/commuList.tiles");
		
		return mav;
	}
	
	//글 검색하기
	@ResponseBody
	@GetMapping(value = "/commu/commuSearch.bibo", produces = "text/plain;charset=UTF-8")
	public String commuSearch(HttpServletRequest request
			,@RequestParam(value="category", defaultValue = "0")String category
			,@RequestParam(value="type", defaultValue = "z")String type
			,@RequestParam(value="word", defaultValue = "")String word
			,@RequestParam(value="currentShowPageNo", defaultValue = "1")String currentShowPageNo){

		int sizePerPage = 10;// 한 페이지당 10개

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1;
		int endRno = startRno + sizePerPage - 1;

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("category", category);
		paraMap.put("type", type);
		paraMap.put("word", word);
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		List<CommuBoardDTO> CommuBoardList = null;
		
		CommuBoardList = service.getCommuBoardList(paraMap);
		
		int totalCount = service.getCBListTotalCount(paraMap); // 전체개수
		int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);
		
		
		List<String> fileSeqList = null;
		fileSeqList = service.getfileSeqList();
		JSONArray jsonArr = new JSONArray();
		
		Boolean fileTrue = true;
		
		if (CommuBoardList != null) {
			for (CommuBoardDTO cbdto : CommuBoardList) {

				JSONObject jsonObj = new JSONObject(); // {}
				
				if(fileSeqList != null) {
					for(String file : fileSeqList) {
						if(file.equals(cbdto.getCidx())) {
							jsonObj.put("fileTrue", fileTrue);
						}
					}
				}
				
				cbdto.setCategory(getCategoryText(cbdto.getCategory()));
				
				jsonObj.put("category", cbdto.getCategory());
				jsonObj.put("title", cbdto.getTitle());
				jsonObj.put("commentcount", cbdto.getCommentCount());
				jsonObj.put("userid", cbdto.getUserid());
				jsonObj.put("writeday", cbdto.getWriteday());
				jsonObj.put("viewcnt", cbdto.getViewcnt());
				jsonObj.put("totalPage", totalPage);
				
				
				jsonArr.put(jsonObj);
			}	
		}
		return jsonArr.toString();
	}

	
	
	// 글 작성하기 페이지로 이동 
	@GetMapping("/commu/commuWrite.bibo")
	public ModelAndView isLogin_commuWrite(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		mav.setViewName("commu/commuWrite.tiles");
		
		return mav;
	}

		
	// 글 작성 성공
	@ResponseBody
	@PostMapping(value="/commu/commuWriteEnd.bibo", produces="text/plain;charset=UTF-8")
	public String isLogin_commuWriteEnd(MultipartHttpServletRequest mtp_request, HttpServletRequest request, HttpServletResponse response) {
		
		
		String category = mtp_request.getParameter("category");
		String title = mtp_request.getParameter("title");
		String content = mtp_request.getParameter("content");
		
		
		CommuBoardDTO cbdto = new CommuBoardDTO();
		CommuFilesDTO cfdto = new CommuFilesDTO();
		
		cbdto.setCategory(category);
		cbdto.setTitle(title);
		cbdto.setContent(content);

		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		cbdto.setUserid(loginuser.getUserid());


		List<MultipartFile> fileList = mtp_request.getFiles("file_arr");
		
		session = mtp_request.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "resources" + File.separator + "commu_attach_file";
		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다.

		
		byte[] bytes = null;
		// 첨부파일의 내용물을 담는 것
		
		long fileSize = 0;
		// 첨부파일의 크기
		
		JSONObject jsonObj = new JSONObject();
		String originalFilename = "";
		String newFileName = "";
		// WAS(톰캣)의 디스크에 저장될 파일명
		
		int n = 0;
		n = service.add(cbdto);
		jsonObj.put("result", n);
		
		if(fileList != null && fileList.size() > 0) { 
			for(MultipartFile mfile : fileList) {
				try {
					bytes = mfile.getBytes();
					originalFilename = mfile.getOriginalFilename();
					newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
					
					//System.out.println(newFileName);
					//System.out.println(originalFilename);
					
					fileSize = mfile.getSize();
					
					//System.out.println(fileSize);
					
					String cseq = service.getSeqCommu();

					cfdto.setCidx(cseq);
					cfdto.setFileName(newFileName);
					cfdto.setOrgFilename(originalFilename);
					cfdto.setFileSize(String.valueOf(fileSize));
					
					service.add_File(cfdto); //파일첨부 테이블에 넣음
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
					jsonObj.put("result", 0);
					break;
				} 
			}
		}
		
	    
		return jsonObj.toString();
		

	}}
