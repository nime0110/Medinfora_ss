package com.spring.app.reserve.controller;

import java.io.IOException;
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
		System.out.println("확인용 today : " + today);
		
		for(int i=0; i< 30; i++) {
			String day = dateFormat.format(currentDate.getTime());

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
			
			System.out.println("확인용 hidx : " + paraMap.get("hidx"));
			System.out.println("확인용 day : " + paraMap.get("day"));
			
			// 병원의 오픈시간과 마감시간 파악
			HospitalDTO hospitalTime = service.hospitalTime(hidx);
			
			int start_h = 0, start_m = 0, end_h = 0, end_m = 0;
			String start = "", end ="";
			try {
				if("월요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime1().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime1().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime1().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime1().substring(2));
					start = hospitalTime.getStarttime1();
					end = hospitalTime.getEndtime1();
					System.out.println("월요일");
				}
				if("화요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime2().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime2().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime2().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime2().substring(2));
					start = hospitalTime.getStarttime2();
					end = hospitalTime.getEndtime2();
					System.out.println("화요일");
				}
				if("수요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime3().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime3().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime3().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime3().substring(2));
					start = hospitalTime.getStarttime3();
					end = hospitalTime.getEndtime3();
					System.out.println("수요일");
				}
				if("목요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime4().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime4().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime4().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime4().substring(2));
					start = hospitalTime.getStarttime4();
					end = hospitalTime.getEndtime4();
					System.out.println("목요일");
				}
				if("금요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime5().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime5().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime5().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime5().substring(2));
					start = hospitalTime.getStarttime5();
					end = hospitalTime.getEndtime5();
					System.out.println("금요일");
				}
				if("토요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime6().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime6().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime6().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime6().substring(2));
					start = hospitalTime.getStarttime6();
					end = hospitalTime.getEndtime6();
					System.out.println("토요일");
				}
				if("일요일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime7().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime7().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime7().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime7().substring(2));
					start = hospitalTime.getStarttime7();
					end = hospitalTime.getEndtime7();
					System.out.println("일요일");
				}
				if("공휴일".equals(dayOfweek)) {
					start_h = Integer.parseInt(hospitalTime.getStarttime8().substring(0, 2));
					start_m = Integer.parseInt(hospitalTime.getStarttime8().substring(2));
					end_h = Integer.parseInt(hospitalTime.getEndtime8().substring(0,2));
					end_m = Integer.parseInt(hospitalTime.getEndtime8().substring(2));
					start = hospitalTime.getStarttime8();
					end = hospitalTime.getEndtime8();
					System.out.println("공휴일");
				}
			}catch (Exception e) {
				// 운영안하는 경우
			}
			
			int TotalstartM = start_h * 60 + start_m;
			int TotalendM = end_h * 60 + end_m;
			int cnt = (TotalendM - TotalstartM)/30;
			
			// 선택한 날의 예약 개수 파악
			int reserveCnt = service.reserveCnt(paraMap);
			
			if(cnt == 0) {
				System.out.println("운영안함");
				availableDayList.add("휴무");
			}
			else if(cnt != 0 && cnt == reserveCnt) {
				System.out.println("예약 가득참");
				availableDayList.add("예약불가능");
			}
			else {
				System.out.println("예약가능");
				availableDayList.add("예약가능");
				
				if(day.equals(today)) {
					
					List<String> availableTimeList = new ArrayList<>(); 
					for(int j=0; j<cnt; j++) {
						// 현재시간 이후 그리고 오픈시간 이후 부터 시작
						int today_n = currentDate.get(Calendar.DATE);
						currentDate.add(Calendar.MINUTE, 30);
						
						if(today_n != currentDate.get(Calendar.DATE)){	// 날짜가 바뀔 경우
							break;
						}
						
						day = dateFormat.format(currentDate.getTime());
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
							System.out.println("예약가능 시간대" + day.substring(11, 16));
						}
					}	// end of for-------------
					// 오늘 예약가능 시간대 표출
					mav.addObject("availableTimeList", availableTimeList);
				}	// end of if---------
			}	// end of for---------------------
			currentDate.add(Calendar.DATE, 1);
		}	// end of for--------------------------------
		
//		String dayOfweek = "";
//		int n = currentDate.get(Calendar.DAY_OF_WEEK);
//		
//		// 날짜가 공휴일인지 확인
//		int check = service.holidayCheck(day);
//		if(check == 1) {
//			n = 0;
//		}
//		
//		switch (n) {
//		case 1:
//			dayOfweek = "일요일";
//			break;
//		case 2:
//			dayOfweek = "월요일";
//			break;
//		case 3:
//			dayOfweek = "화요일";
//			break;
//		case 4:
//			dayOfweek = "수요일";
//			break;
//		case 5:
//			dayOfweek = "목요일";
//			break;
//		case 6:
//			dayOfweek = "금요일";
//			break;
//		case 7:
//			dayOfweek = "토요일";
//			break;
//		case 0:
//			dayOfweek = "공휴일";
//			break;
//		}
//		
//		Map<String, String> paraMap = new HashMap<>();
//		paraMap.put("hidx", hidx);
//		paraMap.put("day", day);
//		
//		System.out.println("확인용 hidx : " + paraMap.get("hidx"));
//		System.out.println("확인용 day : " + paraMap.get("day"));
		
//		// 병원의 오픈시간과 마감시간 파악
//		HospitalDTO hospitalTime = service.hospitalTime(hidx);
//		
//		int start_h = 0, start_m = 0, end_h = 0, end_m = 0;
//		String start = "", end ="";
//		try {
//			if("월요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime1().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime1().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime1().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime1().substring(2));
//				start = hospitalTime.getStarttime1();
//				end = hospitalTime.getEndtime1();
//			}
//			if("화요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime2().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime2().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime2().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime2().substring(2));
//				start = hospitalTime.getStarttime2();
//				end = hospitalTime.getEndtime2();
//			}
//			if("수요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime3().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime3().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime3().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime3().substring(2));
//				start = hospitalTime.getStarttime3();
//				end = hospitalTime.getEndtime3();
//			}
//			if("목요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime4().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime4().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime4().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime4().substring(2));
//				start = hospitalTime.getStarttime4();
//				end = hospitalTime.getEndtime4();
//			}
//			if("금요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime5().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime5().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime5().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime5().substring(2));
//				start = hospitalTime.getStarttime5();
//				end = hospitalTime.getEndtime5();
//			}
//			if("토요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime6().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime6().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime6().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime6().substring(2));
//				start = hospitalTime.getStarttime6();
//				end = hospitalTime.getEndtime6();
//			}
//			if("일요일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime7().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime7().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime7().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime7().substring(2));
//				start = hospitalTime.getStarttime7();
//				end = hospitalTime.getEndtime7();
//			}
//			if("공휴일".equals(dayOfweek)) {
//				start_h = Integer.parseInt(hospitalTime.getStarttime8().substring(0, 2));
//				start_m = Integer.parseInt(hospitalTime.getStarttime8().substring(2));
//				end_h = Integer.parseInt(hospitalTime.getEndtime8().substring(0,2));
//				end_m = Integer.parseInt(hospitalTime.getEndtime8().substring(2));
//				start = hospitalTime.getStarttime8();
//				end = hospitalTime.getEndtime8();
//			}
//		}catch (Exception e) {
//			// 운영안하는 경우
//		}
		
//		int TotalstartM = start_h * 60 + start_m;
//		int TotalendM = end_h * 60 + end_m;
//		int cnt = (TotalendM - TotalstartM)/30;
		
//		// 선택한 날의 예약 개수 파악
//		int reserveCnt = service.reserveCnt(paraMap);
//		if(cnt == 0) {
//			System.out.println("운영안함");
//		}
//		else if(cnt != 0 && cnt == reserveCnt) {
//			System.out.println("예약 가득참");
//		}
//		else {
//			System.out.println("예약 가능한 날 존재");
			
//			List<String> availableTimeList = new ArrayList<>(); 
//			for(int i=0; i<cnt; i++) {
//				// 현재시간 이후 그리고 오픈시간 이후 부터 시작
//				int today = currentDate.get(Calendar.DATE);
//				currentDate.add(Calendar.MINUTE, 30);
//				
//				if(today != currentDate.get(Calendar.DATE)){	// 날짜가 바뀔 경우
//					break;
//				}
//				
//				day = dateFormat.format(currentDate.getTime());
//				String day_time = day.substring(11, 13) + day.substring(14, 16);
//				
//				if(Integer.parseInt(day_time) < Integer.parseInt(start)) {	// 현재시간이 오픈시간 이전일 경우
//					i--;
//					continue;
//				}
//				paraMap.put("day", day);
//				
//				// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
//				HospitalDTO impossibleTimeCheck = service.dayReserveImpossible(paraMap);	
//				
//				if(impossibleTimeCheck == null) {	// 예약가능					
//					availableTimeList.add(day.substring(11, 16));
//					System.out.println(day.substring(11, 16));
//				}
//			}	// end of for-------------
			
//			// 선택날짜의 예약가능 시간대 표출
//			mav.addObject("availableTimeList", availableTimeList);
//		}
		
		String today_str = today.substring(0, 11);
		today_str = today_str.replaceAll("-", ".");
		
		mav.addObject("today_str",today_str);
		mav.addObject("availableDayList",availableDayList);		// 30일 예약가능 여부
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}
	
}
