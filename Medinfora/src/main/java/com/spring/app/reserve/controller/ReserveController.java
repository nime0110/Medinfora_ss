package com.spring.app.reserve.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.reserve.service.ReserveService;

@Controller
@RequestMapping(value="/reserve/")
public class ReserveController {

	@Autowired
	private ReserveService service;
	
	@GetMapping("choiceDr.bibo")
	public ModelAndView isLogin_choiceDr(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
		
		if(loginuser != null && loginuser.getmIdx() == 2) {
			String message = "(단체)병원 회원은 접근 불가능합니다.";
	 		String loc = request.getContextPath()+"/index.bibo";
	 		
	 		request.setAttribute("message", message);
	 		request.setAttribute("loc", loc);
	 		
	 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	 		
	 		try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		
		mav.setViewName("reserve/choiceDr.tiles");
		
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="choiceDrList.bibo", produces="text/plain;charset=UTF-8")
	public String choiceDrList(HttpServletRequest request) {
		
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		String city = request.getParameter("city");
		String local = request.getParameter("local");
		String classcode = request.getParameter("classcode");
		String hpname = request.getParameter("hpname");
		
		if(city == null || "시/도 선택".equals(city)) {
			city = "";
		}
		if(local == null || "시/군구 선택".equals(local)) {
			local = "";
		}
		if(classcode == null || "진료과목 선택".equals(classcode)) {
			classcode = "";
		}
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		int sizePerPage = 6;	// 한 페이지당 보여줄 개수
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호 
		int endRno = startRno + sizePerPage - 1; // 끝 행번호
		
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		paraMap.put("city", city);
		paraMap.put("local", local);
		paraMap.put("classcode", classcode);
		paraMap.put("hpname", hpname);

        List<HospitalDTO> mbHospitalList = service.mbHospitalList(paraMap);
        
		int totalCnt = service.getmbHospitalCnt(paraMap);	// 회원가입된 병원 개수
		
		int totalPage = (int)Math.ceil((double)totalCnt/sizePerPage);
		
		JSONArray jsonArr = new JSONArray();
		
		if(mbHospitalList != null) {
			for(HospitalDTO hpdto : mbHospitalList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("classcode",hpdto.getClasscode());
				jsonObj.put("hidx",hpdto.getHidx());
				jsonObj.put("hpname",hpdto.getHpname());
				jsonObj.put("hpaddr",hpdto.getHpaddr());
				jsonObj.put("hptel",hpdto.getHptel());

				jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);
				jsonObj.put("paraMap", paraMap);
				
				jsonArr.put(jsonObj);
			}	// end of for---------
		}
		return jsonArr.toString();
	}
	
	@GetMapping("choiceDay.bibo")
	public ModelAndView choiceDay(ModelAndView mav) {
		mav.setViewName("redirect:/index.bibo");
		return mav;
	}
	
	@PostMapping("choiceDay.bibo")
	public ModelAndView choiceDay(ModelAndView mav, HttpServletRequest request) {
		
		String hidx = request.getParameter("hidx");
		
		Calendar currentDate = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// String[] dayOfweekArr = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
		
		String day = dateFormat.format(currentDate.getTime());
		/*
		for(int i=0; i< 30; i++) {
			String date = dateFormat.format(currentDate.getTime());
			int n = currentDate.get(Calendar.DAY_OF_WEEK) - 1;
			String dayOfweek = dayOfweekArr[n];
			System.out.println(dayOfweek);
			
			currentDate.add(Calendar.DATE, 1);
		}
		*/
		String dayOfweek = "";
		int n = currentDate.get(Calendar.DAY_OF_WEEK);
		
		// 날짜가 공휴일인지 확인
		int check = service.holidayCheck(day);
		if(check == 1) {
			n = 0;
		}
		
		switch (n) {
		case 1:
			dayOfweek = "일요일";
			break;
		case 2:
			dayOfweek = "월요일";
			break;
		case 3:
			dayOfweek = "화요일";
			break;
		case 4:
			dayOfweek = "수요일";
			break;
		case 5:
			dayOfweek = "목요일";
			break;
		case 6:
			dayOfweek = "금요일";
			break;
		case 7:
			dayOfweek = "토요일";
			break;
		case 0:
			dayOfweek = "공휴일";
			break;
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("hidx", hidx);
		paraMap.put("day", day);
		paraMap.put("dayOfweek", dayOfweek);
		
		// 병원과 요일 파악하여, 오늘(현재시간 이후) 진료예약 가능한 업무시간 파악하기
		List<HospitalDTO> availableTimeList = service.todayReserveAvailable(paraMap);
		
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}
	
}
