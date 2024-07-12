package com.spring.app.mypage.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.mypage.service.MypageService;

@Controller
@RequestMapping(value="/mypage/")
public class MypageController {
	
	@Autowired
	private MypageService service;

	@Autowired
    private AES256 aES256;
	
	@GetMapping("myinfo.bibo")
	public ModelAndView isLogin_myinfo(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		mav.setViewName("mypage/myinfo.info");
		
		return mav;
	}
	
	@PostMapping("updatemember.bibo")
	public ModelAndView isLogin_updatemember(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> paraMap = new HashMap<>();
		
		paraMap.put("userid",request.getParameter("userid"));
		paraMap.put("mobile",request.getParameter("mobile"));
		paraMap.put("address",request.getParameter("address"));
		paraMap.put("detailaddress",request.getParameter("detailaddress"));
		
		if(service.updateinfo(paraMap)) {
			
		}
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	@GetMapping("myreserve.bibo")
	public ModelAndView isLogin_myreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/myreserve.info");
		return mav;
	}
	
	// === 진료예약 열람 === //
	@GetMapping("mdreserve.bibo")
	public ModelAndView isLogin_mdreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser != null && loginuser.getmIdx() != 2) {
			String message = "(단체)병원 회원으로 로그인 후 접근 가능합니다.";
	 		String loc = "javascript:history.back()";
	 		
	 		request.setAttribute("message", message);
	 		request.setAttribute("loc", loc);
	 		
	 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	 		
	 		try {
				dispatcher.forward(request, response);	// /WEB-INF/views/msg.jsp 로 이동
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		
		mav.setViewName("mypage/mdreserve.info");
		return mav;
	}
	
	// === 진료예약열람(페이징, 검색 처리) === //
	@ResponseBody
	@GetMapping(value="mdreserveList.bibo", produces="text/plain;charset=UTF-8")
	public String mdreserveList(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		String userid = loginuser.getUserid();
		
		String hidx = "";
		
		// 아이디를 통해 병원인덱스 값 찾기
		hidx = service.Searchhospital(userid);

		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		String sclist = request.getParameter("sclist");		// 검색 구분
		String inputsc = request.getParameter("inputsc");	// 검색한 값
		
		if(inputsc != null) {
			inputsc = inputsc.trim();
		}
		
		int sizePerPage = 10;	// 한 페이지당 보여줄 개수
		
		if(currentShowPageNo == null) {		// 처음 접속한 경우
			currentShowPageNo = "1";
		}
		
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호 
		int endRno = startRno + sizePerPage - 1; // 끝 행번호
		
		Map<String, String> paraMap = new HashMap<>();
        paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		paraMap.put("sclist",sclist);
		paraMap.put("inputsc",inputsc);
		
		paraMap.put("hidx",hidx);
		
		List<ReserveDTO> reserveList = null;
		
		// hidx 의 현재 예약리스트 가져오기(검색포함)
		reserveList = service.reserveList(paraMap);
		
		int totalCnt = reserveList.size();	// 리스트 총 결과 개수
		int totalPage = (int)Math.ceil((double)totalCnt/sizePerPage);
		
		JSONArray jsonArr = new JSONArray();
		List<MemberDTO> memberList = null;
		
		if(reserveList != null) {	// 예약리스트가 존재하는 경우
			for(ReserveDTO rsdto: reserveList) {
				String patient_id = rsdto.getUserid();
				
				// 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
				memberList = service.GetPatientInfo(patient_id);
				if(memberList != null) {
					for(MemberDTO mdto: memberList) {
						try {
							// select 용으로 사용되는 값에 담기
							rsdto.setName(mdto.getName());
							rsdto.setMobile(aES256.decrypt(mdto.getMobile()));
						} catch (UnsupportedEncodingException | GeneralSecurityException e) {
							e.printStackTrace();
						}
					}	// end of for---------------
				}
				
				JSONObject jsonObj = new JSONObject();
		        jsonObj.put("checkin", rsdto.getCheckin());
		        jsonObj.put("name", rsdto.getName());
		        jsonObj.put("mobile", rsdto.getMobile());
		        jsonObj.put("reportday", rsdto.getReportday());
		        jsonObj.put("rcode", rsdto.getRcode());
		        
		        jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);
				
		        jsonArr.put(jsonObj);
		        
			}	// end of for------------------
		}

		return jsonArr.toString();
	}
	
}
