package com.spring.app.mypage.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.mypage.service.MypageService;
import com.spring.app.question.service.QuestionService;

@Controller
@RequestMapping(value="/mypage/")
public class MypageController {
	
	@Autowired
	private MypageService service;
	
	@Autowired
	private QuestionService questionservice;

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
		
		String message = "내 정보 변경에 실패하였습니다.";
 		String loc = "myinfo.bibo";
		
		String userid = request.getParameter("userid");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String detailaddress = request.getParameter("detailaddress");
		
		if(address == null) {
			address = "";
			detailaddress = "";
		}
		
		paraMap.put("userid",userid);
		paraMap.put("mobile",mobile);
		paraMap.put("address",address);
		paraMap.put("detailaddress",detailaddress);
		
		
		if(service.updateinfo(paraMap)) {
			message = "내 정보를 변경하였습니다.";
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			loginuser.setMobile(mobile);
			if(address!="") {
				loginuser.setAddress(address);
				loginuser.setDetailAddress(detailaddress);
			}
		}
		
		mav.addObject("message",message);
		mav.addObject("loc",loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	@ResponseBody
	@PostMapping("nowpwdCheck.bibo")
	public String nowpwdCheck(HttpServletRequest request) {
		
		JSONObject jsonObj = new JSONObject();
		
		Map<String,String> paraMap = new HashMap<String, String>();
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		paraMap.put("userid",userid);
		paraMap.put("pwd",pwd);
		
		if(service.nowpwdCheck(paraMap)) {
			jsonObj.put("isPwd", true);
		}else {
			jsonObj.put("isPwd", false);
		}
		
		return jsonObj.toString();
	}
	
	@PostMapping("updatepwd.bibo")
	public ModelAndView isLogin_updatepwd(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> paraMap = new HashMap<String, String>();
		
		String message = "비밀번호 변경을 실패하였습니다.";
 		String loc = "myinfo.bibo";
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		paraMap.put("userid",userid);
		paraMap.put("pwd",pwd);
		
		if(service.updatepwd(paraMap)==1) {
			message = "비밀번호를 변경하였습니다.";
		}
		
		mav.addObject("message",message);
		mav.addObject("loc",loc);
		mav.setViewName("msg");
		
		return mav;
	}
	
	// === (일반회원) 진료예약 열람 === //
	@GetMapping("myreserve.bibo")
	public ModelAndView isMember_myreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/myreserve.info");
		return mav;
	}
	
	// === (의료인) 진료예약 열람 === //
	@GetMapping("mdreserve.bibo")
	public ModelAndView isDr_mdreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/mdreserve.info");
		return mav;
	}	// end of public ModelAndView isLogin_mdreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {-----
	
	// === (의료인) 진료예약열람(페이징, 검색 처리) === //
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
		else {
			inputsc = "";
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

		// 진료예약일이 오늘 이전이라면 진료완료로 변경
		boolean update = checkDate_checkin(reserveList);
		
		if(update) {	// 변경한 진료현황이 존재한 경우
			reserveList = service.reserveList(paraMap);
		}
		
		int totalCnt = service.reserveListCnt(paraMap);	// 리스트 총 결과 개수
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
		        jsonObj.put("ridx", rsdto.getRidx());
		        
		        jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);
				
		        jsonArr.put(jsonObj);
		        
			}	// end of for------------------
		}

		return jsonArr.toString();
	}	// end of public String mdreserveList(HttpServletRequest request) {-----

	// === (의료인) 진료현황 변경 모달창 정보 === //
	@ResponseBody
	@GetMapping(value="getRdto.bibo", produces="text/plain;charset=UTF-8")
	public String getRdto(HttpServletRequest request) {
		String ridx = request.getParameter("ridx");
		
		ReserveDTO rsdto = null;
		if(ridx != null) {
			// ridx 를 통해 예약 정보 가져오기
			rsdto = service.getRdto(ridx);
		}
		
		JSONObject jsonObj = new JSONObject();	// {}
		if(rsdto != null) {
			try {
				rsdto.setMobile(aES256.decrypt(rsdto.getMobile()));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}
			jsonObj.put("ridx", rsdto.getRidx());
			jsonObj.put("name", rsdto.getName());
			jsonObj.put("mobile", rsdto.getMobile());
			jsonObj.put("rStatus", rsdto.getrStatus());
			jsonObj.put("reportday", rsdto.getReportday());
			jsonObj.put("checkin", rsdto.getCheckin());
		}
		return jsonObj.toString();
	} // end of public String getRdto(HttpServletRequest request) {---------------
	
	// === (일반) 진료예약열람(페이징, 검색 처리) === //
	@ResponseBody
	@GetMapping(value="myreserveList.bibo", produces="text/plain;charset=UTF-8")
	public String myreserveList(HttpServletRequest request) {
		// 예약리스트(페이징, 검색처리) 
		return reserveList(request);
	}	// end of public String mdreserveList(HttpServletRequest request) {-----
	
	// === (일반회원) 진료접수 취소 정보 === //
	@ResponseBody
	@GetMapping(value="cancleRdto.bibo", produces="text/plain;charset=UTF-8")
	public String cancleRdto(HttpServletRequest request) {
		String ridx = request.getParameter("ridx");
		
		JSONObject jsonObj = new JSONObject();
		if(ridx != null) {
			// ridx 를 통해 진료접수 취소하기
			int n = service.cancleRdto(ridx);
			if(n==1) {
				// 예얄리스트(페이징, 검색처리) 
				return reserveList(request);
			}
		}
		
		return jsonObj.toString();
	}	// end of public String getRdto(HttpServletRequest request) {---------------
	
	// 예약리스트(페이징, 검색처리) 
	private String reserveList(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		String userid = loginuser.getUserid();

		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		String sclist = request.getParameter("sclist");		// 검색 구분
		String inputsc = request.getParameter("inputsc");	// 검색한 값

		if(inputsc != null) {
			inputsc = inputsc.trim();
		}
		else {
			inputsc = "";
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
		
		paraMap.put("userid",userid);
		
		List<ReserveDTO> reserveList = null;
		
		// userid 의 현재 예약리스트 가져오기(검색포함)
		reserveList = service.UserReserveList(paraMap);
		
		// 진료예약일이 오늘 이전이라면 진료완료로 변경
		boolean update = checkDate_checkin(reserveList);
		
		if(update) {	// 변경한 진료현황이 존재한 경우
			reserveList = service.reserveList(paraMap);
		}
				
		int totalCnt = service.UserReserveListCnt(paraMap);	// 리스트 총 결과 개수
		int totalPage = (int)Math.ceil((double)totalCnt/sizePerPage);
		
		JSONArray jsonArr = new JSONArray();
		List<MemberDTO> memberList = null;
		
		if(reserveList != null) {	// 예약리스트가 존재하는 경우
			for(ReserveDTO rsdto: reserveList) {
				String hidx = rsdto.getHidx();
				
				// 예약된 병원의 아이디 값을 가지고 이름과 전화번호 알아오기
				memberList = service.GetHidxInfo(hidx);
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
		        jsonObj.put("ridx", rsdto.getRidx());
		        
		        jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);
				
		        jsonArr.put(jsonObj);
		        
			}	// end of for------------------
		}
		return jsonArr.toString();
	}	// end of private String reserveList(HttpServletRequest request) {---------------------
	
	// 진료예약일이 오늘 이전이라면 진료완료로 변경
	private boolean checkDate_checkin(List<ReserveDTO> reserveList) {
		LocalDate today = LocalDate.now();
        
		boolean update = false;
		for(ReserveDTO rsdto: reserveList) {
			String ridx = rsdto.getRidx();
			String rcode = rsdto.getRcode();
			String checkin = rsdto.getCheckin().substring(0,10);
			
			// 진료예약일을 날짜타입으로 변환
			LocalDate checkinDate = LocalDate.parse(checkin);
			
			if(checkinDate.isBefore(today)) {	// 만약 예약일이 오늘 날짜와 이전이라면
				if("1".equals(rcode) || "2".equals(rcode)) {	// 접수신청이나 접수완료이라면
					// 진료완료로 변경하기
					service.updatercode(ridx);
					update = true;
				}
			}
		}	// end of for-----------
		return update;
	}	// end of private boolean checkDate_checkin(List<ReserveDTO> reserveList) {----------------------

	// === 진료일정관리 === //
	@GetMapping("reserveSchedule.bibo")
	public ModelAndView isDr_reserveSchedule(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		String userid = loginuser.getUserid();
		
		String hidx = "";
		
		// 아이디를 통해 병원인덱스 값 찾기
		hidx = service.Searchhospital(userid);
		
		// hidx 의 예약리스트 가져오기
		List<ReserveDTO> reserveList = service.TotalreserveList(hidx);
		
		JSONArray name_jsonArr = new JSONArray();
		JSONArray time_jsonArr = new JSONArray();
		JSONArray checkinEnd_jsonArr = new JSONArray();
		if(reserveList != null) {
			for(ReserveDTO rsdto: reserveList) {
				String patient_id = rsdto.getUserid();
				
				// 예약된 환자의 아이디 값을 가지고 이름 알아오기
				List<MemberDTO> memberList = service.GetPatientInfo(patient_id);
				if(memberList != null) {
					for(MemberDTO mdto: memberList) {
						// select 용으로 사용되는 값에 담기
						rsdto.setName(mdto.getName());
					}	// end of for---------------
					name_jsonArr.put(rsdto.getName());
					time_jsonArr.put(rsdto.getCheckin());
				}
				String checkin = rsdto.getCheckin();
				try {
					// checkin 을 CALENDAR 타입으로 변환
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
				    Date parsedDate = dateFormat.parse(checkin);
				    
				    Calendar calendar = Calendar.getInstance();
				    calendar.setTime(parsedDate);
				    
				    // 30분 추가
				    calendar.add(Calendar.MINUTE, 30);
				    
				    // 30분 추가한 값을 String 타입에 담기
				    Date plus30 = calendar.getTime();
				    String checkinEnd = dateFormat.format(plus30);
				    checkinEnd_jsonArr.put(checkinEnd);
				} catch (ParseException e) {
				    e.printStackTrace();
				}
				
			}	//end of for---------------
		}
		// ReserveDTO 의 name 값과 checkin 을 짝지어 넘겨주기
		mav.addObject("nameList", name_jsonArr.toString());
		mav.addObject("timeList", time_jsonArr.toString());
		mav.addObject("checkinEndList", checkinEnd_jsonArr.toString());
		mav.setViewName("mypage/reserveSchedule.info");
		return mav;
	}	// end of public ModelAndView isLogin_mdreserve(ModelAndView mav,HttpServletRequest request, HttpServletResponse response) {-----
	
	// === (의료인) 예약일정관리 환자정보 모달창 정보 === //
	@ResponseBody
	@GetMapping(value="getPatientInfo.bibo", produces="text/plain;charset=UTF-8")
	public String getPatientInfo(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		String hospital_userid = loginuser.getUserid();
		
		// 아이디를 통해 병원인덱스 값 찾기
		String hidx = service.Searchhospital(hospital_userid);
		
		String checkin = request.getParameter("checkin");
		
		Map<String, String> paraMap = new HashMap();
		paraMap.put("hidx", hidx);
		paraMap.put("checkin", checkin);		
		
		ReserveDTO rdto = null;
		if(checkin != null) {
			// 병원과 예약일시를 통해 환자아이디 가져오기
			rdto = service.getPatientd(paraMap);
		}

		MemberDTO mdto = null;
		JSONObject jsonObj = new JSONObject();	// {}
		if(rdto != null) {
			String userid = rdto.getUserid();
			// userid 를 통해 환자 정보 가져오기
			mdto = service.getPatientInfo(userid);
			try {
				mdto.setMobile(aES256.decrypt(mdto.getMobile()));
				mdto.setEmail(aES256.decrypt(mdto.getEmail()));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}
			jsonObj.put("name", mdto.getName());
			jsonObj.put("mobile", mdto.getMobile());
			jsonObj.put("email", mdto.getEmail());
			jsonObj.put("address", mdto.getAddress());
			jsonObj.put("birthday", mdto.getBirthday());
			jsonObj.put("gender", mdto.getGender());
		}
		return jsonObj.toString();
	}	// end of public String getRdto(HttpServletRequest request) {---------------
	
	@GetMapping("searchloglist.bibo")
	public ModelAndView isAdmin_searchloglist(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		
		mav.setViewName("mypage/searchloglist.info");
		
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="getlogdata.bibo", produces="text/plain;charset=UTF-8")
	public String getlogdata(HttpServletRequest request) {
		
		String result = "";
		
		String opt = request.getParameter("t");
		String opu = request.getParameter("u");
		String opr = request.getParameter("r");
		
		Map<String,String> paraMap = new HashMap<String, String>();
		paraMap.put("opu",opu);
		paraMap.put("opr",opr);
		
		if(opt.equals("t0")) {
			Map<String,List<String>> resultT0 = service.getT0data(paraMap);
			
			JSONArray xAxis = new JSONArray();
			JSONArray series = new JSONArray();
			
			for(String data : resultT0.get("xAxis")) {
				xAxis.put(data);
			}
			
			for(String data : resultT0.get("data")) {
				series.put(data);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("xAxis", xAxis);
			jsonObj.put("series", series);
			
			result = jsonObj.toString();
			
		}else {
			List<Map<String,String>> resultT1 = service.getT1data(paraMap);
			
			JSONArray data = new JSONArray();
			
			for(Map<String,String> resultT : resultT1) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("value", resultT.get("count"));
				jsonObj.put("name", resultT.get("searchword"));
				data.put(jsonObj);
			}
			
			result = data.toString();
		}
		
		return result;
	}
	
	
	@GetMapping("myquestion.bibo")
	public ModelAndView isLogin_myquestion(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/myquestion.info");
		
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="myQnA.bibo", produces="text/plain;charset=UTF-8")
	public String myQnA(HttpServletRequest request) {
		
		String searchSubject = request.getParameter("subject");
		String searchType = request.getParameter("type");
		String searchWord = request.getParameter("word");
		String str_currentPageNo = request.getParameter("currentPageNo");
		

		HttpSession session = request.getSession();
		session.setAttribute("readCountMark", "yes");

		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		
		JSONObject jsonObj = new JSONObject();
		
		List<MediQDTO> qList = null;
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchSubject", searchSubject);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("userid", loginuser.getUserid());
		paraMap.put("midx", String.valueOf(loginuser.getmIdx()));
		
		int totalCount = 0;
		int sizePerPage = 10;
		int currentPageNo = 1;
		int totalPage = 0;
		
		totalCount = questionservice.totalquestion(paraMap);
		
		totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
		
		if(str_currentPageNo == null) {
			currentPageNo = 1;
		}
		
		try {
			currentPageNo = Integer.parseInt(str_currentPageNo);
		} catch (Exception e) {
			currentPageNo = 1;
		}
		
		int startRno = ((currentPageNo - 1) * sizePerPage) + 1; 
		int endRno = startRno + sizePerPage - 1;
		
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		qList = questionservice.totalquestionList(paraMap);
		
		Map<String, Object> qdtoMap = new HashMap<>();
		qdtoMap.put("qList", qList);
		qdtoMap.put("totalPage", totalPage);
		
		jsonObj.put("qdtoMap", qdtoMap);
		
		return jsonObj.toString();
	}
	
	
	
	
}
