package com.spring.app.reserve.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.MemberDTO;
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
	
	@SuppressWarnings("unchecked")
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
				
				jsonArr.add(jsonObj);
			}	// end of for---------
		}
		return jsonArr.toString();
	}
	
	@GetMapping("choiceDay.bibo")
	public ModelAndView choiceDay(ModelAndView mav) {
		mav.setViewName("redirect:/index.bibo");
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("choiceDay.bibo")
	public ModelAndView choiceDay(ModelAndView mav, HttpServletRequest request) {
		
		String hidx = request.getParameter("hidx");
		
		Calendar currentDate = Calendar.getInstance();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 만약 30분 이전이라면 현재 분 00분으로 하고 30분 이후라면 현재시간 30으로 설정
		int minutes = currentDate.get(Calendar.MINUTE);
		if (minutes < 30) {
		    currentDate.set(Calendar.MINUTE, 0);
		} else {
		    currentDate.set(Calendar.MINUTE, 30);
		}
		currentDate.set(Calendar.SECOND, 0);
		
		// 30일동안의 진료예약가능여부 파악
		List<String> availableDayList = new ArrayList<>();
		
		String today = dateFormat.format(currentDate.getTime());
		
		for(int i=0; i< 30; i++) {
			
			String day = dateFormat.format(currentDate.getTime());
			
			String dayOfweek = parseDayofWeek(currentDate,day);
			
			System.out.println(day + dayOfweek);
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("hidx", hidx);
			paraMap.put("day", day);
			
			// 병원의 오픈시간과 마감시간 파악
			HospitalDTO hospitalTime = service.hospitalTime(hidx);
			
			int start_h=0,start_m=0,end_h=0,end_m=0;
			String start="",end="";
			
			try {
				Map<String,String> getTimes = getTimes(hospitalTime,dayOfweek);
				start_h = Integer.parseInt(getTimes.get("start_h"));
				start_m = Integer.parseInt(getTimes.get("start_m"));
				end_h = Integer.parseInt(getTimes.get("end_h"));
				end_m = Integer.parseInt(getTimes.get("end_m"));
				start = getTimes.get("start");
				end = getTimes.get("end");
			}catch (Exception e) {
				// 운영 안하는 경우
			}
			int TotalstartM = start_h * 60 + start_m;
			int TotalendM = end_h * 60 + end_m;
			int cnt = (TotalendM - TotalstartM)/30;
					
			// 선택한 날의 예약 개수 파악
			int reserveCnt = service.reserveCnt(paraMap);

			if(today.substring(0, 10).equals(day.substring(0, 10))) {
				String time = today.substring(11, 13) + today.substring(14, 16);
				
				String rEndTime = "";
				if("00".equals(end.substring(2))) {
					rEndTime = String.valueOf((Integer.parseInt(end) - 70)); 
				}
				else {
					rEndTime = String.valueOf((Integer.parseInt(end) - 30));
				}
				
				if(Integer.parseInt(time) >= Integer.parseInt(rEndTime)) {
					currentDate.add(Calendar.DATE, 1);
					continue;
				}
			}
			if(cnt != 0 && cnt != reserveCnt) {
				availableDayList.add(day.substring(0, 10));
			}	// end of if---------------------
			
			currentDate.add(Calendar.DATE, 1);
		}	// end of for--------------------------------
		
		if(availableDayList.size() == 0) {	// 30일간의 예약이 모두 가득 찼을 경우
			mav.addObject("message", "예약가능한 날이 존재하지 않습니다.");
            mav.addObject("loc", "javascript:history.back()");
			mav.setViewName("msg");
	        return mav;
		}
		JSONArray jsonArr = new JSONArray();
		for(String availableDay : availableDayList) {
			jsonArr.add(availableDay);
		}

		mav.addObject("hidx",hidx);
		mav.addObject("dateList",jsonArr.toString());
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}	// end of public ModelAndView choiceDay(ModelAndView mav, HttpServletRequest request) {----------

	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping("selectDay.bibo")
	public String selectDay(HttpServletRequest request) throws ParseException {
		
		String hidx = request.getParameter("hidx");
		String day = request.getParameter("date");
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String today = dateFormat.format(currentDate.getTime()).substring(0, 10);
		
		if(today.equals(day)) {
			// 만약 30분 이전이라면 현재 분 00분으로 하고 30분 이후라면 현재시간 30으로 설정
			int minutes = currentDate.get(Calendar.MINUTE);
			if (minutes < 30) {
			    currentDate.set(Calendar.MINUTE, 0);
			} else {
			    currentDate.set(Calendar.MINUTE, 30);
			}
			currentDate.set(Calendar.SECOND, 0);
		}
		today = dateFormat.format(currentDate.getTime());
		
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFmt.parse(day);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
		String dayOfweek = parseDayofWeek(calendar,day);

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("hidx", hidx);
		paraMap.put("day", day);
		
		// 병원의 오픈시간과 마감시간 파악
		HospitalDTO hospitalTime = service.hospitalTime(hidx);
		
		int start_h=0,start_m=0,end_h=0,end_m=0;
		String start="",end="";
		
		try {
			Map<String,String> getTimes = getTimes(hospitalTime,dayOfweek);
			start_h = Integer.parseInt(getTimes.get("start_h"));
			start_m = Integer.parseInt(getTimes.get("start_m"));
			end_h = Integer.parseInt(getTimes.get("end_h"));
			end_m = Integer.parseInt(getTimes.get("end_m"));
			start = getTimes.get("start");
			end = getTimes.get("end");
		}catch (Exception e) {
			// 운영 안하는 경우
		}
		
		int TotalstartM = start_h * 60 + start_m;
		int TotalendM = end_h * 60 + end_m;
		int cnt = (TotalendM - TotalstartM)/30;
				
		// 선택한 날의 예약 개수 파악
		int reserveCnt = service.reserveCnt(paraMap);
		
		List<String> availableTimeList = new ArrayList<>(); 
		
		if(cnt != 0 && cnt != reserveCnt) {	// 예약가능한 경우
			if(day.equals(today.substring(0, 10))) {	// 선택한 날짜가 오늘일 경우
				
				for(int j=0; j<cnt; j++) {
					// 현재시간 이후 그리고 오픈시간 이후 부터 시작
					int today_n = currentDate.get(Calendar.DATE);
					currentDate.add(Calendar.MINUTE, 30);
					
					if(today_n != currentDate.get(Calendar.DATE)){	// 날짜가 바뀔 경우
						break;
					}
					
					today = dateFormat.format(currentDate.getTime());
					String day_time = today.substring(11, 13) + today.substring(14, 16);
					
					if(Integer.parseInt(day_time) < Integer.parseInt(start)) {	// 현재시간이 오픈시간 이전일 경우
						j--;
						continue;
					}
					else if(Integer.parseInt(day_time) >= Integer.parseInt(end)) {
						continue;
					}

					paraMap.put("day", today);
					// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
					HospitalDTO impossibleTimeCheck = service.dayReserveImpossible(paraMap);	
					
					if(impossibleTimeCheck == null) {	// 예약가능					
						availableTimeList.add(today.substring(11, 16));
					}
				}	// end of for-------------	
			}
			else {		// 오늘일이 아닌 선택한 날짜 예약가능시간 리스트 가져오기
				 
	            for(int j=0; j<cnt; j++) {
		            int day_n = calendar.get(Calendar.DATE);
		            calendar.add(Calendar.MINUTE, 30);
					
					if(day_n != calendar.get(Calendar.DATE)){	// 날짜가 바뀔 경우
						break;
					}
					day = dateFormat.format(calendar.getTime());

					String day_time = day.substring(11, 13) + day.substring(14, 16);
					
					if(Integer.parseInt(day_time) < Integer.parseInt(start)) {	// 현재시간이 오픈시간 이전일 경우
						j--;
						continue;
					}
					else if(Integer.parseInt(day_time) >= Integer.parseInt(end)) {
						continue;
					}
					paraMap.put("day", day);
					// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
					HospitalDTO impossibleTimeCheck = service.dayReserveImpossible(paraMap);	
					
					if(impossibleTimeCheck == null) {	// 예약가능					
						availableTimeList.add(day.substring(11, 16));
					}
	            }	// end of for-------------
			}	// end of if~else---------------
		}	// end of if-----------		
		JSONArray jsonArr = new JSONArray();
		for(String availableTime : availableTimeList) {
			jsonArr.add(availableTime);
		}
		
		return jsonArr.toString();
	}

	// 공휴일인지 파악 후 요일 구분
	private String parseDayofWeek(Calendar currentDate, String day) {
		
		int n = currentDate.get(Calendar.DAY_OF_WEEK);
		
		// 날짜가 공휴일인지 확인
		int check = service.holidayCheck(day);
		
		if(check == 1) {
			n = 0;
		}
		
		System.out.println(n);
		
		String dayOfweek = "";
		
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
		
		System.out.println(dayOfweek);
		
		return dayOfweek;
	}
	
	// 요일별 시간 파악
	private Map<String, String> getTimes(HospitalDTO hospitalTime,String dayOfweek) throws Exception {
		
		Map<String,String> resultMap = new HashMap<String, String>();
		
		if("월요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime1().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime1().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime1().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime1().substring(2));
			resultMap.put("start", hospitalTime.getStarttime1());
			resultMap.put("end", hospitalTime.getEndtime1());
		}
		if("화요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime2().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime2().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime2().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime2().substring(2));
			resultMap.put("start", hospitalTime.getStarttime2());
			resultMap.put("end", hospitalTime.getEndtime2());
		}
		if("수요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime3().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime3().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime3().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime3().substring(2));
			resultMap.put("start", hospitalTime.getStarttime3());
			resultMap.put("end", hospitalTime.getEndtime3());
		}
		if("목요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime4().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime4().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime4().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime4().substring(2));
			resultMap.put("start", hospitalTime.getStarttime4());
			resultMap.put("end", hospitalTime.getEndtime4());
		}
		if("금요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime5().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime5().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime5().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime5().substring(2));
			resultMap.put("start", hospitalTime.getStarttime5());
			resultMap.put("end", hospitalTime.getEndtime5());
		}
		if("토요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime6().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime6().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime6().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime6().substring(2));
			resultMap.put("start", hospitalTime.getStarttime6());
			resultMap.put("end", hospitalTime.getEndtime6());
		}
		if("일요일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime7().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime7().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime7().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime7().substring(2));
			resultMap.put("start", hospitalTime.getStarttime7());
			resultMap.put("end", hospitalTime.getEndtime7());
		}
		if("공휴일".equals(dayOfweek)) {
			resultMap.put("start_h", hospitalTime.getStarttime8().substring(0, 2));
			resultMap.put("start_m", hospitalTime.getStarttime8().substring(2));
			resultMap.put("end_h", hospitalTime.getEndtime8().substring(0,2));
			resultMap.put("end_m", hospitalTime.getEndtime8().substring(2));
			resultMap.put("start", hospitalTime.getStarttime8());
			resultMap.put("end", hospitalTime.getEndtime8());
		}
		
		return resultMap;
	}
	
	@PostMapping("insertReserve.bibo")
	public ModelAndView insertReserve(ModelAndView mav, HttpServletRequest request) {
		
		String hidx = request.getParameter("hidx");
		String day = request.getParameter("day");
		
		HttpSession session = request.getSession();
		MemberDTO loginuser = (MemberDTO)session.getAttribute("loginuser");
		String userid = loginuser.getUserid();
		
		Map<String,String> paraMap = new HashMap<String, String>();
		paraMap.put("hidx", hidx);
		paraMap.put("day", day);
		paraMap.put("userid", userid);
		
		int n = service.insertReserve(paraMap);
		String message = "", loc = "";
		if(n==1) {
			message = "예약이 접수되었습니다.";
			loc = request.getContextPath() + "/index.bibo";
		}
		mav.addObject("message",message);
		mav.addObject("loc",loc);
		mav.setViewName("msg");
		return mav;
	}
}
