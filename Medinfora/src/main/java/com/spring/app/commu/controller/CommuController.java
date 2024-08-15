package com.spring.app.commu.controller;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
import com.spring.app.common.Myutil;
import com.spring.app.commu.service.CommuService;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.domain.commu.BookmarkDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuCommentDTO;
import com.spring.app.domain.commu.CommuFilesDTO;
import com.spring.app.domain.commu.SuggestionDTO;

@Controller
public class CommuController {
	
	//의존객체주입
	@Autowired
	private CommuService service;
	
	@Autowired
	private FileManager fileManager;

	//카테고리 정해주는 메소드
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

	//커뮤 게시판 글 리스트 가져오기 
	@GetMapping(value="/commu/commuList.bibo")
	public ModelAndView commuList(ModelAndView mav, HttpServletRequest request,
	                              @RequestParam(value="category", defaultValue = "0") String category,
	                              @RequestParam(value="type", defaultValue = "z") String type,
	                              @RequestParam(value="word", defaultValue = "") String word,
	                              @RequestParam(value="currentPageNo", defaultValue = "1") String str_currentPageNo) {


		String sort = request.getParameter("sort"); //사용자가 선택한 sort 상태를 가져온다. 
		
		Map<String, String> paraMap = new HashMap<>();
		
	    // 유효성 검사(사용자가 url 쿼리문 장난칠때)
	    List<String> categoryList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	    List<String> typeList = Arrays.asList("z", "title", "content", "userid");

	    if (!categoryList.contains(category)) { //만약 카테고리가 카테고리 리스트에 없는 숫자라면
	        category = "0"; //카테고리를 0으로 설정 (선택)
	        paraMap.put("category", category); 
	    }

	    if (!typeList.contains(type)) { //타입이 타입리스트 안에 없다면
	        type = "z"; //제일 기본 타입
	        paraMap.put("type", type);
	    }

	    paraMap.put("category", category);
	    paraMap.put("type", type);
	    paraMap.put("word", word);
	    paraMap.put("sort", sort);

	    int totalCount = 0;        // 총 게시물 건수
	    int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수 
	    int currentPageNo = 0;     // 현재 페이지 번호
	    int totalPage = 0;         // 총 페이지 수

	    try {
	        // 총 게시물 건수 가져오기
	        totalCount = service.getCBListTotalCount(paraMap);
	        totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

	        // 현재 페이지 번호 설정 및 유효성 검사 - 사용자 입력시 막기
	        try {
	            currentPageNo = Integer.parseInt(str_currentPageNo);
	            if(currentPageNo < 1 || currentPageNo > totalPage) { //지금현재페이지가 1보다 작거나, 현재페이지가 전체페이지보다 크다면
	                currentPageNo = 1; //현재페이지를 1로 
	            } 
	        } catch (NumberFormatException e) { 
	            currentPageNo = 1;
	        }

	        // 시작 행번호와 끝 행번호 계산
	        int startRno = ((currentPageNo - 1) * sizePerPage) + 1;
	        int endRno = startRno + sizePerPage - 1;

	        paraMap.put("startRno", String.valueOf(startRno));
	        paraMap.put("endRno", String.valueOf(endRno));

	        // 게시물 리스트 가져오기(페이징처리됨)
	        List<CommuBoardDTO> commuBoardList = service.getCommuBoardList(paraMap);

	        // 첨부파일이 있을 경우 첨부파일 리스트 가져오기
	        List<String> fileSeqList = service.getfileSeqList();

	        
	        // 카테고리 텍스트 변환
	        for (CommuBoardDTO cbdto : commuBoardList) {
	            cbdto.setCategory(getCategoryText(cbdto.getCategory()));
	        }

	        // 페이지 바 만들기
	        int blockSize = 10;
	        int loop = 1;
	        int pageNo = ((currentPageNo - 1) / blockSize) * blockSize + 1;
	        
	        String pageBar = "<ul style='list-style:none; '>";
	        String url = "commuList.bibo"; //이동 url 

	        // [맨처음]
	        if(pageNo != 1) {
	            pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='" + url + 
	                    "?category="+category+"&type="+type+"&word="+word+"&currentPageNo=1'>[맨처음]</a></li>";
	        }

	        //loop, 페이지번호가 증가하는데 loop가 blockSize보다 크지 않거나, 페이지번호가 전체페이지보다 크지 않을 경우 증가한다
	        while (!(loop > blockSize || pageNo > totalPage)) {
	            if(pageNo == currentPageNo) {
	                pageBar += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px skyblue; color:white; background-color: skyblue; padding:1px 3px;'>"+pageNo+"</li>";
	            } else {
	                pageBar += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='" + url + 
	                        "?category="+category+"&type="+type+"&word="+word+"&currentPageNo="+pageNo+"'>"+pageNo+"</a></li>";
	            }
	            loop++;
	            pageNo++;
	        }

	        // [마지막]
	        if(pageNo <= totalPage) {
	            pageBar += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='" + url + 
	                    "?category="+category+"&type="+type+"&word="+word+"&currentPageNo="+totalPage+"'>[마지막]</a></li>";
	        }

	        pageBar += "</ul>";
	        String goBackURL = Myutil.getCurrentURL(request);
	        
	        mav.addObject("goBackURL", goBackURL);
	        mav.addObject("pageBar", pageBar);
	        mav.addObject("paraMap", paraMap); //검색용 맵  category, type, word 들어가 있음.
	        
	        // ModelAndView에 데이터 추가
	        mav.addObject("CommuBoardList", commuBoardList);
	        mav.addObject("fileSeqList", fileSeqList);
	        mav.addObject("totalPage", totalPage);
	        mav.addObject("currentShowPageNo", currentPageNo);
	        mav.addObject("sizePerPage", sizePerPage);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // 뷰 설정
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

		//파라미터로 카테고리/타입(글제목, 글내용 등)/검색어/ 현재 페이지를 받아온다.
		
		String sort = request.getParameter("sort");//정렬 받아오기
		
		
		//글 검색하고 난뒤 검색결과의 페이지 생성 
		int sizePerPage = 10;// 한 페이지당 10개

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1;
		int endRno = startRno + sizePerPage - 1;

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("category", category);
		paraMap.put("type", type);
		paraMap.put("word", word);
		paraMap.put("sort", sort);
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		List<CommuBoardDTO> CommuBoardList = null;
		
		CommuBoardList = service.getCommuBoardList(paraMap); //검색 리스트를 가져와서 담는다.
		
		int totalCount = service.getCBListTotalCount(paraMap); // 검색된 결과의 전체개수(count(*))
		int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);
		
		List<String> fileSeqList = null;
		fileSeqList = service.getfileSeqList(); //첨부파일 유무 알기 위해 DB select - 리스트 제목 옆에 표시하기 위함
		JSONArray jsonArr = new JSONArray();
		
		Boolean fileTrue = true;
		
		if (CommuBoardList != null) { //게시판에 쓴 글이 하나라도 있다면
			for (CommuBoardDTO cbdto : CommuBoardList) { //리스트 객체 for문 돌린다

				JSONObject jsonObj = new JSONObject(); 
				
				if(fileSeqList != null) { //파일이 있는 게시글이 있는 경우
					for(String file : fileSeqList) { //파일 리스트 for문
						if(file.equals(cbdto.getCidx())) { //파일있는 게시글 cidx == 불러온 게시글 cidx 라면
							jsonObj.put("fileTrue", fileTrue); //파일이 있다는 표시를 해준다
						}
					}
				}
				cbdto.setCategory(getCategoryText(cbdto.getCategory())); //가져온 카테고리 번호를 카테고리 이름으로 치환하는 메소드 사용
				
				jsonObj.put("category", cbdto.getCategory());
				jsonObj.put("title", cbdto.getTitle());
				jsonObj.put("commentcount", cbdto.getCommentCount());
				jsonObj.put("userid", cbdto.getUserid());
				jsonObj.put("writeday", cbdto.getWriteday());
				jsonObj.put("viewcnt", cbdto.getViewcnt());
				jsonObj.put("totalPage", totalPage);
				
				jsonArr.put(jsonObj); //jsonObj를 배열에 담음
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

	// 글 작성 성공한 뒤 
	@ResponseBody
	@PostMapping(value="/commu/commuWriteEnd.bibo", produces="text/plain;charset=UTF-8")
	public String isLogin_commuWriteEnd(MultipartHttpServletRequest mtp_request, HttpServletRequest request, HttpServletResponse response) {
		//파일업로드 -MultipartHttpServletRequest 로 멀티파트와 관련된 것 처리 가능 - request 선언한 것은 isLogin_ AOP 양식에 의해
		
		String category = mtp_request.getParameter("category");
		String title = mtp_request.getParameter("title");
		String content = mtp_request.getParameter("content");

		CommuBoardDTO cbdto = new CommuBoardDTO();
		
		cbdto.setCategory(category);
		cbdto.setTitle(title);
		cbdto.setContent(content);
		CommuFilesDTO cfdto = new CommuFilesDTO();

		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		cbdto.setUserid(loginuser.getUserid()); //로그인한 유저의 아이디를 set해준다.

		List<MultipartFile> fileList = mtp_request.getFiles("file_arr"); //파일들을 꺼내서 fileList에 담아준다.
		
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
		n = service.add(cbdto); // 글 등록
		jsonObj.put("result", n); //결과 
		
		if(fileList != null && fileList.size() > 0) { 
			for(MultipartFile mfile : fileList) {
				try {
					bytes = mfile.getBytes();
					originalFilename = mfile.getOriginalFilename();
					newFileName = fileManager.doFileUpload(bytes, originalFilename, path); //파일업로드
					
					fileSize = mfile.getSize();
					String cseq = service.getSeqCommu(); //하나의 글에 대한 seq를 가져온다. (cidx)

					cfdto.setCidx(cseq); 
					cfdto.setFileName(newFileName);
					cfdto.setOrgname(originalFilename);
					cfdto.setFileSize(String.valueOf(fileSize));
					
					service.add_File(cfdto); //파일첨부 테이블에 넣음
					
				} catch (Exception e) {
					e.printStackTrace();
					jsonObj.put("result", 0); //실패했다는 뜻 - n=0 실패 1= 성공
					break;
				} 
			}
		}
		return jsonObj.toString();
	}
	
	//글 상세보기 
	@RequestMapping("/commu/commuView.bibo")
	public ModelAndView commuView(ModelAndView mav, HttpServletRequest request) {

	    String cidx = request.getParameter("cidx");
	    String currentShowPageNo = request.getParameter("currentShowPageNo");
	    String category = request.getParameter("category");
	    String type = request.getParameter("type");
	    String word = request.getParameter("word");

	    if (cidx == null || cidx.isEmpty()) {
	        // cidx가 null이거나 비어있을 때의 처리
	    	mav.setViewName("redirect:/commu/commuList.bibo");
	        return mav;
	    }
	    
	    // 글 select
	    CommuBoardDTO cbdto = null;
	    List<CommuFilesDTO> fileList = null;
	    
	    BookmarkDTO bdto = new BookmarkDTO();
	    
	    //세션에 로그인한 유저아이디 가져오기
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
	    
	    //로그인한 유저가 북마크를 했는지 안했는지
		int alreadyBookmark = 0;
		
		if(loginuser != null) {
			//로그인유저아이디, cidx 를 bdto 에 넣어준다.
			bdto.setUserid(loginuser.getUserid());
			bdto.setCidx(cidx);
			alreadyBookmark = service.alreadyMarking(bdto); //북마크 되어있는지 여부, cidx
			// 안되어있다면 0, 되어있다면 1 
		}
	    if (cidx != null) {	
	    	// 조회수 증가랑 같이 글 하나 보기
	        cbdto = service.getCommuDetail(cidx);
	        // 첨부파일 가져오기
	        fileList = service.getAttachfiles(cidx);
	    }

	    mav.addObject("alreadyBookmark", alreadyBookmark);
	    mav.addObject("cbdto", cbdto);
	    mav.addObject("fileList", fileList);
	    mav.addObject("currentShowPageNo", currentShowPageNo);
	    mav.addObject("category", category);
	    mav.addObject("type", type);
	    mav.addObject("word", word);

	    mav.setViewName("commu/commuView.tiles");
	    return mav;
	}

	// 첨부파일 다운로드 받기
	@GetMapping("/commu/download.bibo")
	public ModelAndView download(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		String filename = request.getParameter("filename"); 
		String originFilename = request.getParameter("originFilename");
		String cidx = request.getParameter("cidx");
		
		try {
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "commu_attach_file"; //resources에 commu_attach_file 폴더 안에 저장되어있음.
	
			boolean flag = fileManager.doFileDownload(filename, originFilename, path, response);
	
			if (!flag) {
				mav.addObject("message", "다운로드 실패");
				mav.addObject("loc", request.getContextPath()+"/questionList.bibo");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		mav.setViewName("msg");
		return mav;
		
	}
	
	// 글 수정
	@GetMapping("/commu/commuEdit.bibo")
	public ModelAndView isLogin_commuEdit(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
	    String currentShowPageNo = request.getParameter("currentShowPageNo");
	    String category = request.getParameter("category");
	    String type = request.getParameter("type");
	    String word = request.getParameter("word");
		
		//글 수정해야할 글번호 가져오기
		String cidx = request.getParameter("cidx");
		String message = "";
		try {
			if (Integer.parseInt(cidx) <= 0) {
				message = "글 수정이 불가합니다.";
			}
			
		    CommuBoardDTO cbdto = null;
		    List<CommuFilesDTO> fileList = null;

		    if (cidx != null) {
		        // 조회수 증가랑 같이 글 하나 보기
		        cbdto = service.getCommuDetail(cidx);
		        // 첨부파일 가져오기
		        fileList = service.getAttachfiles(cidx);
		    }

			//로그인된 유저와 글쓴이가 같은지 
			if(cbdto == null) {
				message = "글 수정이 불가합니다.";
			} else {
				HttpSession session = request.getSession();
				MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
				if( !loginuser.getUserid().equals(cbdto.getUserid()) ) { //다른사람글수정
					message = "다른 사용자의 글은 수정이 불가합니다.";
				} else {
					//자신의 글을 수정할 경우
					//가져온 1개 글을 글수정할 폼이 있는 view단으로 보내준다
					mav.addObject("cbdto", cbdto);
					mav.addObject("fileList", fileList);
				    mav.addObject("currentShowPageNo", currentShowPageNo);
				    mav.addObject("category", category);
				    mav.addObject("type", type);
				    mav.addObject("word", word);
					mav.setViewName("commu/commuEdit.tiles");
					return mav;
				}
			}
			
		} catch (NumberFormatException e) {
			message = "글 수정이 불가합니다.";
		}
		String loc = "javascript:history.back()";
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		return mav;
	}
	
	// 글 수정 완료
	@ResponseBody
	@PostMapping(value="/commu/commuUpdateEnd.bibo", produces="text/plain;charset=UTF-8")
	public String editEnd(CommuBoardDTO cbdto, MultipartHttpServletRequest mtp_request, HttpServletRequest request, HttpServletResponse response) {
	    
		String cidx = cbdto.getCidx();
	    
	    HttpSession session = request.getSession();
	    session = mtp_request.getSession();
	    
	    //파일 리스트에 담기
	    List<MultipartFile> fileList = mtp_request.getFiles("file_arr");
	    
	    String root = session.getServletContext().getRealPath("/");
	    String path = root + "resources" + File.separator + "commu_attach_file";
	    
	    CommuFilesDTO cfdto = new CommuFilesDTO();
	    
	    byte[] bytes = null;
	    long fileSize = 0;
	    
	    JSONObject jsonObj = new JSONObject();
	    String originalFilename = "";
	    String newFileName = "";
	    
	    int n = 0;
	    
	    // 글 수정하기
	    n = service.edit(cbdto);
	    jsonObj.put("result", n);
	    
	    if(fileList != null && fileList.size() > 0) { 
	        try {
	        	 // 첨부파일 테이블 비우기 및 파일경로에서 해당첨부파일 삭제
	            // 새로운 파일 업로드
	            for (MultipartFile mfile : fileList) {
	                bytes = mfile.getBytes();
	                originalFilename = mfile.getOriginalFilename();
	                newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
	                fileSize = mfile.getSize();
	                
	                cfdto.setCidx(cidx);
	                cfdto.setFileName(newFileName);
	                cfdto.setOrgname(originalFilename);
	                cfdto.setFileSize(String.valueOf(fileSize));
	                
	                // 첨부파일 테이블 입력하기
	                service.add_File(cfdto); //파일첨부 테이블에 넣음
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            jsonObj.put("result", 0);
	        }
	    }
	    return jsonObj.toString();
	}
	
	//첨부파일 사용자가 선택한것만 삭제
	@ResponseBody
	@PostMapping(value = "/commu/commuDeleteFile.bibo", produces = "text/plain;charset=UTF-8")
	public String commuSearch(HttpServletRequest request){
		
		String cidx = request.getParameter("cidx");
		String fileName = request.getParameter("fileName");
	
		Map<String, String> paraMap = new HashMap<>();
        List<CommuFilesDTO> existFileList = service.getAttachfiles(cidx); //첨부파일 가져오기
        
	    HttpSession session = request.getSession();
	    
	    String root = session.getServletContext().getRealPath("/");
	    String path = root + "resources" + File.separator + "commu_attach_file";
     
        paraMap.put("cidx", cidx);
        paraMap.put("path", path);
        paraMap.put("fileName", fileName);
        
        int n= 0;
        
        // 테이블 파일 삭제
        n = service.fileDel(paraMap);
        if(n == 1) {
        	//폴더 내 파일삭제(로컬경로)
        	service.folderFileDel(paraMap);        	
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", n);
        
        return jsonObj.toString();
	}
	
	// 글 삭제 (첨부파일 삭제까지 포함)
	@ResponseBody
	@RequestMapping(value = "/commu/del.bibo", produces = "text/plain;charset=UTF-8")
	public String postDelete(HttpServletRequest request) {
		String cidx = request.getParameter("cidx");
		String commentCount = request.getParameter("commentCount");
			
		JSONObject jsonObj = new JSONObject();

		//해당 게시글의 첨부파일 리스트 가져오기
	    List<CommuFilesDTO> fileList = null;

	    if (cidx != null) {
	        fileList = service.getAttachfiles(cidx);
	    }
	    
	    HttpSession session = request.getSession();
	    String root = session.getServletContext().getRealPath("/");
	    String path = root + "resources" + File.separator + "commu_attach_file";
	    
		Map<String, String> paraMap = new HashMap<>();
	    
		paraMap.put("path", path);
		paraMap.put("cidx", cidx);
		String fileName ="";
		int n= 0;
		//게시글 삭제
		
		if(!(fileList.isEmpty())) {
			// 테이블 파일 삭제
			service.fileDelAll(cidx);
			//폴더 내 파일삭제
			for(CommuFilesDTO cfdto : fileList) {			
				paraMap.put("fileName", cfdto.getFileName());
				service.folderFileDel(paraMap);        	
			}

		}
		n = service.del(cidx);
		
        jsonObj.put("result", n);
        return jsonObj.toString();
	
	}
	
	// 댓글(대댓글) 쓰기
	@ResponseBody
	@PostMapping(value="/commu/addComment.bibo", produces="text/plain;charset=UTF-8") 
	// 스프링에서 json 또는 gson을 사용한 ajax 구현시 데이터를 화면에 출력해 줄때 한글로 된 데이터가 '?'로 출력되어 한글이  깨지는 현상 방지를 위한 produes
	public String addComment(HttpServletRequest request) {
		
		CommuCommentDTO cmtdto = new CommuCommentDTO();
		//파라미터로 cidx(글번호), 유저아이디, 콘텐츠를 가져온다
		String cidx = request.getParameter("cidx");
		String userid = request.getParameter("userid");
		String content = request.getParameter("content");
				
		//할당
		cmtdto.setCidx(cidx);
		cmtdto.setUserid(userid);
		cmtdto.setContent(content);
		
		//답댓글 쓰기인 경우
		String groupno = request.getParameter("groupno");
		String depthno = request.getParameter("depthno"); //depth에 따라 댓글, 답댓글, 답댓글의 답댓글이 달라짐
		String fk_cmidx = request.getParameter("fk_cmidx"); //원 댓글의 댓글 넘버
		String fk_userid = request.getParameter("fk_userid"); //원 댓글의 유저아이디, @user1 이렇게 답변할 경우
		
		if(fk_cmidx == null) {
			fk_cmidx = "";
		}
		if(fk_userid == null) {
			fk_userid = "";
		}
		cmtdto.setGroupno(groupno);
		cmtdto.setDepthno(depthno);
		cmtdto.setFk_cmidx(fk_cmidx);
		cmtdto.setFk_userid(fk_userid);
		
		if(cidx == null) {
			cidx = "";
		}
		
		int n = 0;
		try {
			n = service.addComment(cmtdto); //댓글 쓰기 - 서비스단에서 로직 처리.
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("n", n);
		jsonObj.put("userid", cmtdto.getUserid());
		return jsonObj.toString();
	}
	
	// 댓글(대댓글) 조회
	@ResponseBody
	@GetMapping(value="/commu/commentList.bibo", produces="text/plain;charset=UTF-8") 
	public String readComment(HttpServletRequest request, @RequestParam(value="pageNo", defaultValue = "1") String pageNo) {
		
		String cidx = request.getParameter("cidx");
		
		// 페이징 처리
		int sizePerPage = 15; //한 페이지당 15개의 댓글을 보여줄 것임
		
		int startRno = ((Integer.parseInt(pageNo) - 1) * sizePerPage) + 1; // 시작 행번호 
        int endRno = startRno + sizePerPage - 1; // 끝 행번호
		

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("cidx", cidx);
        paraMap.put("startRno", String.valueOf(startRno));
        paraMap.put("endRno", String.valueOf(endRno));
		
        // 현재 페이지 번호 설정 및 유효성 검사addComment
		List<CommuCommentDTO> commentList = service.getCommentList(paraMap);
		int totalCount = service.getCommentTotalCount(cidx); 

	    int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);      

	    
		JSONArray jsonArr = new JSONArray(); 

		if(commentList != null) {
			for(CommuCommentDTO cmtdto : commentList) {
				JSONObject jsonObj = new JSONObject(); //{}
				
				jsonObj.put("cidx", cmtdto.getCidx()); 
				jsonObj.put("cmidx", cmtdto.getCmidx()); 
				jsonObj.put("content", cmtdto.getContent()); 
				jsonObj.put("userid", cmtdto.getUserid()); 
				jsonObj.put("writeday", cmtdto.getWriteday()); 
				jsonObj.put("updateday", cmtdto.getUpdateday()); 
				jsonObj.put("groupno", cmtdto.getGroupno()); 
				jsonObj.put("depthno", cmtdto.getDepthno());
				jsonObj.put("fk_cmidx", cmtdto.getFk_cmidx());
	            // null 체크 후 빈 문자열로 대체
	            String fk_userid = cmtdto.getFk_userid() == null ? "" : cmtdto.getFk_userid();
	            jsonObj.put("fk_userid", fk_userid);
				
				jsonObj.put("totalPage", totalPage);
				jsonObj.put("sizePerPage", sizePerPage);
				
				jsonArr.put(jsonObj);
			}
		}
		return jsonArr.toString(); 
	}
	
	// 댓글(대댓글) 수정
	@ResponseBody
	@PostMapping(value="/commu/commentUpdate.bibo", produces="text/plain;charset=UTF-8") 
	public String commentUpdate(HttpServletRequest request) {
		
		CommuCommentDTO cmtdto = new CommuCommentDTO();
		String cmidx = request.getParameter("cmidx");
		String content = request.getParameter("content");
	
		cmtdto.setCmidx(cmidx);
		cmtdto.setContent(content);
	
		int n = service.updateComment(cmtdto);
		
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("n", n); 
		return jsonObj.toString();
	}
	
	// 댓글(대댓글) 삭제 -> delete 말고 update로 content만 '해당 댓글이 삭제되었습니다.' 로 변경
	@ResponseBody
	@PostMapping(value="/commu/commentDelete.bibo", produces="text/plain;charset=UTF-8") 
	public String commentDelete(HttpServletRequest request) {
		
		String cmidx = request.getParameter("cmidx");
		
		int n = service.deleteComment(cmidx);
		
		JSONObject jsonObj = new JSONObject(); // {} 이런 형태가 됨
		jsonObj.put("n", n);
		return jsonObj.toString();
	}
	
	// 추천
	@ResponseBody
	@GetMapping(value="/commu/suggestionPost.bibo", produces="text/plain;charset=UTF-8") 
	public String suggestionPost(HttpServletRequest request) {
		
		SuggestionDTO sdto = new SuggestionDTO();
		
		String userid = request.getParameter("userid"); //로그인한 유저아이디를 넘겨받음
		String cidx = request.getParameter("cidx");
		
		sdto.setUserid(userid);
		sdto.setCidx(cidx);
		
		JSONObject jsonObj = new JSONObject(); // {} 이런 형태가 됨
		
		int n = 0;
		int alreadySuggestion = 0;

        // 먼저 이미 추천한 기록이 있는지 확인
		alreadySuggestion = service.checkSuggestion(sdto);
		
		if(alreadySuggestion != 1) {//추천 안했다면
			n = service.suggestionPost(sdto); //추천, 
			n = service.postSuggestionUpdate(sdto); //추천수 업데이트
		}
		
		jsonObj.put("n", n);
		jsonObj.put("alreadySuggestion", alreadySuggestion); //이미 추천함
		return jsonObj.toString();
	}
	
	// 북마크	하기
	@ResponseBody
	@GetMapping(value="/commu/bookMark.bibo", produces="text/plain;charset=UTF-8") 
	public String bookMark(HttpServletRequest request) {
		BookmarkDTO bdto = new BookmarkDTO();
		
		//ajax에서 보내준 userid, cidx 를 추가(userid는 현재 로그인된 유저아이디)
		String userid = request.getParameter("userid");
		String cidx = request.getParameter("cidx");
		
		bdto.setUserid(userid); //객체에 할당
		bdto.setCidx(cidx);
		
		JSONObject jsonObj = new JSONObject(); // {} 이런 형태가 됨
		
		int n = 0;
		int alreadyBookmark = 0;

		alreadyBookmark = service.alreadyMarking(bdto); //이미 북마크 되어있다면 여부
		
		if(alreadyBookmark < 1) {
			n = service.bookmarkPost(bdto);
		}
		
		jsonObj.put("n", n);
		jsonObj.put("alreadyBookmark", alreadyBookmark);
		return jsonObj.toString();
	}
		
	// 북마크 해제
	@ResponseBody
	@GetMapping(value="/commu/delBookMark.bibo", produces="text/plain;charset=UTF-8") 
	public String delBookMark(HttpServletRequest request) {	
		
		BookmarkDTO bdto = new BookmarkDTO();
		
		String userid = request.getParameter("userid");
		String cidx = request.getParameter("cidx");
		
		bdto.setUserid(userid);
		bdto.setCidx(cidx);
		
		JSONObject jsonObj = new JSONObject(); 

		int n = 0;
		n = service.delBookMark(bdto);		
		jsonObj.put("n", n);
		return jsonObj.toString();
	}
	
	//댓글 페이지
	@ResponseBody
	@GetMapping(value="/commu/getCommentPage.bibo", produces="text/plain;charset=UTF-8") 
	public String getCommentPage(HttpServletRequest request) {	
		
		CommuCommentDTO cmtdto = new CommuCommentDTO();
		String cidx = request.getParameter("cidx");
		String cmidx = request.getParameter("cmidx");
				
		cmtdto.setCidx(cidx);
		cmtdto.setCmidx(cmidx);

		int sizePerPage = 15; //한 페이지당 15개의 댓글을 보여줄 것임
		int rno = service.getCommentPage(cmtdto);		
		int pageNo = (int) Math.ceil((double) rno / sizePerPage);
		
		JSONObject jsonObj = new JSONObject(); 
				
		jsonObj.put("pageNo", pageNo);
		return jsonObj.toString();
	}
	
}
