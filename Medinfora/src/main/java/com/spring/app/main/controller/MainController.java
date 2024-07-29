package com.spring.app.main.controller;


import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.FileManager;
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value="/")
	public ModelAndView commom(ModelAndView mav) {
		
		mav.setViewName("redirect:index.bibo");
		
		return mav;
	}
	
	@RequestMapping(value="/index.bibo")
	public ModelAndView index(ModelAndView mav) {
		
		// index 공지리스트 가져오기
		List<NoticeDTO> ndtoList = service.getIdxNdtoList();
		
		
		mav.addObject("ndtoList",ndtoList);
		mav.setViewName("index.tiles");
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getcityinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getareainfo() {
		
		// city Jsonarr으로 파싱
		List<String> citylist = service.getcityinfo();
		
		JSONArray jsonarr = new JSONArray();
		
		for(String city : citylist) {
			jsonarr.add(city);
		}
		
		return jsonarr.toString();
		
	}// end of public String getareainfo()
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getlocalinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getlocalinfo(HttpServletRequest request) {
		
		// local Jsonarr으로 파싱
		String city = request.getParameter("city");
		
		List<String> locallist = service.getlocalinfo(city);
		
		JSONArray jsonarr = new JSONArray();
		
		for(String local : locallist) {
			jsonarr.add(local);
		}
		
		return jsonarr.toString();
		
	}// end of public String getlocalinfo(HttpServletRequest request)
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getcountryinfo.bibo", produces = "text/plain;charset=UTF-8")
	public String getcountryinfo(HttpServletRequest request) {
		
		// country Jsonarr으로 파싱
		String city = request.getParameter("city");
		String local = request.getParameter("local");
		
		KoreaAreaVO inputareavo = new KoreaAreaVO(city, local);
		
		List<String> countryList = service.getcountryinfo(inputareavo);
		
		JSONArray jsonarr = new JSONArray();
		
		for(String country : countryList) {
			jsonarr.add(country);
		}
		
		return jsonarr.toString();
		
	}// end of public String getcountryinfo(HttpServletRequest request)
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getclasscode.bibo", produces="text/plain;charset=UTF-8")
	public String getclasscode() {
		
		JSONArray jsonarr = new JSONArray();
		
		List<ClasscodeDTO> clsscodeDTOList = service.getclasscode();
		
		for(ClasscodeDTO clsscodeDTO : clsscodeDTOList) {
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("classname",clsscodeDTO.getClassname());
			jsonObj.put("classcode",clsscodeDTO.getClasscode());
			
			jsonarr.add(jsonObj);
		}
		
		return jsonarr.toString();
	}// end of public String getclasscode()
	
	
	
	// 스마트에디터 드래그앤드롭을 이용한 다중 사진 파일 업로드(공지사항, Q&A 공통 적용)
	@PostMapping("/images/multiplePhotoUpload.bibo")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) {
		
		// WAS 의 webapp 의 절대경로
		HttpSession session = request.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "resources"+File.separator+"photo_upload";
		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다
		
		if("medinfora".equalsIgnoreCase(root)) {
			System.out.println("잘못된 루트로 들어감 확인필요");
		}

		File dir = new File(path);

		if(!dir.exists()) {	// 폴더가 존재하지 않으면
			dir.mkdirs();	// 서브폴더 까지 만든다. 이게더 좋데
		}


		try {
			String filename = request.getHeader("file-name"); // 파일명(문자열)을 받는다 - 일반 원본파일명
			// 네이버 스마트에디터를 사용한 파일업로드시 싱글파일업로드와는 다르게 멀티파일업로드는 파일명이 header 속에 담겨져 넘어오게 되어있다. 
			/*
	             	[참고]
	             	HttpServletRequest의 getHeader() 메소드를 통해 클라이언트 사용자의 정보를 알아올 수 있다. 

		            request.getHeader("referer");           // 접속 경로(이전 URL)
		            request.getHeader("user-agent");        // 클라이언트 사용자의 시스템 정보
		            request.getHeader("User-Agent");        // 클라이언트 브라우저 정보 
		            request.getHeader("X-Forwarded-For");   // 클라이언트 ip 주소 
		            request.getHeader("host");              // Host 네임  예: 로컬 환경일 경우 ==> localhost:9090    
			*/
			// System.out.println(">>> 확인용 filename ==> " + filename);
			// >>> 확인용 filename ==> berkelekle%EB%8B%A8%EA%B0%80%EB%9D%BC%ED%8F%AC%EC%9D%B8%ED%8A%B803.jpg 

			InputStream is = request.getInputStream(); // is는 네이버 스마트 에디터를 사용하여 사진첨부하기 된 이미지 파일임.

			String newFilename = fileManager.doFileUpload(is, filename, path);
			   
			String ctxPath = request.getContextPath(); //  

			String strURL = "";
			strURL += "&bNewLine=true&sFileName="+newFilename; 
			// strURL += "&sWidth="+width;
			strURL += "&sFileURL="+ctxPath+"/resources/photo_upload/"+newFilename;

			// === 웹브라우저 상에 사진 이미지를 쓰기 === //
			PrintWriter out = response.getWriter();
			out.print(strURL);

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	@GetMapping("/search.bibo")
	public ModelAndView searach(ModelAndView mav, HttpServletRequest request) {
		
		String search = request.getParameter("search");
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

		String userid = "Anonymous";
		
		if(loginuser != null) {
			userid = loginuser.getUserid();
		}
		
		if(search == "") {
			mav.addObject("nosearch",1);
			mav.setViewName("search.tiles");
			
			return mav;
		}
		
		Map<String,List<Object>> searchList = service.searach(search);
		
		int hcnt = service.hcnt(search);
		
		@SuppressWarnings("unchecked")
		Map<String,Integer> countlist = (Map<String, Integer>) searchList.get("countmap").get(0);
		
		if(countlist.get("totalcount") == 0) {
			mav.addObject("nosearch",2);
			mav.addObject("search",search);
			mav.setViewName("search.tiles");
			return mav;
		}
		
		// 로그 작성부분
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("search", search);
		paraMap.put("userid", userid);
		
		service.writeSearchlog(paraMap);
		
		mav.addObject("hcnt",hcnt);
		mav.addObject("searchlist",searchList);
		mav.addObject("nosearch",0);
		mav.addObject("search",search);
		mav.setViewName("search.tiles");
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/getpopword.bibo")
	public String getpopword() {
		
		JSONArray jsonArr = new JSONArray();
		
		List<String> popwordList = service.getPopwordList();
		
		for(String popword : popwordList) {
			jsonArr.add(popword);
		}
		
		return jsonArr.toString();
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getmorehinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getmorehinfo(HttpServletRequest request) {

		String search = request.getParameter("search");
		String sn = request.getParameter("sn");
		String en = request.getParameter("en");
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("search",search);
		paraMap.put("sn",sn);
		paraMap.put("en",en);
		
		List<HospitalDTO> hdtoList = service.getmorehinfo(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		for(HospitalDTO hdto : hdtoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("hidx",hdto.getHidx());
			jsonObj.put("hpname",hdto.getHpname());
			jsonObj.put("agency",hdto.getAgency());
			jsonObj.put("hpaddr",hdto.getHpaddr());
			jsonObj.put("hptel",hdto.getHptel());
			jsonArr.add(jsonObj);
		}
		
		return jsonArr.toString();
	}
	
}