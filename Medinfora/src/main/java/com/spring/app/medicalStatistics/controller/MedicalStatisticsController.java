package com.spring.app.medicalStatistics.controller;

import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.MemberDTO;
import com.spring.app.medicalStatistics.service.MedicalStatisticsService;

@Controller
@RequestMapping(value = "/status/")
public class MedicalStatisticsController {

	@Autowired
	private MedicalStatisticsService service;

	@GetMapping("view.bibo")
	public ModelAndView status(ModelAndView mav) {
		mav.setViewName("status/view.tiles");
		return mav;
	}

	// 의료서비스율 통계
	@GetMapping("serviceUtilization.bibo")
	public ModelAndView serviceUtilization(ModelAndView mav) {
		mav.setViewName("status/serviceUtilization.status");
		return mav;
	}
	
	// 만나이 파악
	@ResponseBody
	@GetMapping("getAge.bibo")
	public String getAge(HttpServletRequest request) {

		HttpSession session = request.getSession();

		JSONObject jsonObj = new JSONObject();
		try {
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

			String userid = loginuser.getUserid();

			// 일반회원의 생년월일을 가지고 만나이 파악
			String age = service.getAge(userid);

			int age_n = Integer.parseInt(age);

			if (age_n >= 15 && age_n < 20) {
				jsonObj.put("age", "연령__15~19세");
			} else if (age_n >= 20 && age_n < 30) {
				jsonObj.put("age", "연령__20~29세");
			} else if (age_n >= 30 && age_n < 40) {
				jsonObj.put("age", "연령__30~39세");
			} else if (age_n >= 40 && age_n < 50) {
				jsonObj.put("age", "연령__40~49세");
			} else if (age_n >= 50 && age_n < 60) {
				jsonObj.put("age", "연령__50~59세");
			} else if (age_n >= 60) {
				jsonObj.put("age", "연령__60세 이상");
			} else {
				jsonObj.put("age", "0");
			} // end of if~else----------------------

		} catch (NullPointerException e) {
			jsonObj.put("age", "0");
		}
		return jsonObj.toString();

	} // end of public String getAge(HttpServletRequest request) {-------------------

	// 성별 파악
	@ResponseBody
	@GetMapping("getGender.bibo")
	public String getGender(HttpServletRequest request) {

		HttpSession session = request.getSession();

		JSONObject jsonObj = new JSONObject();
		try {
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

			int gender = loginuser.getGender();

			if (gender == 1) {
				jsonObj.put("gender", "성별__남성");
			} else {
				jsonObj.put("gender", "성별__여성");
			}

		} catch (NullPointerException e) { // 병원인 경우
			jsonObj.put("gender", "0");
		}

		return jsonObj.toString();
	}

	// 시도별 주요시설 현황
	@GetMapping("facilities.bibo") 
	public ModelAndView facilities(ModelAndView mav) {
		mav.setViewName("status/facilities.status");
		return mav;
	}
	
	@ResponseBody
	@GetMapping("getFacilityData.bibo")
	public String getFacilityData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 상대 주소 가져오기 
		String useridAdress = (String) session.getAttribute("address");

		JSONObject jsonObj = new JSONObject();
		
	    jsonObj.put("categories", new String[]{"Category1", "Category2", "Category3"});
        jsonObj.put("values", new int[]{100, 200, 300});
		
		
		return jsonObj.toString();
	}
	
	
	
	
	// 년도별 24개 암종 발생률(kosis api 참고)
	@GetMapping("cancerincidence.bibo")
	public ModelAndView cancerincidence(ModelAndView mav) {
		
		List<String> yearList = new ArrayList<>();
		
		int year = 2021;
		for(int i=0; i<22; i++) {
			yearList.add(String.valueOf(year-i));
		}
		
		// System.out.println(yearList);
		
		mav.addObject("yearList", yearList);
		mav.setViewName("status/cancerincidence.status");
		return mav;
	}
	
	
	// CrossDomain 문제발생으로 우회해서 받아온다. 직접 연결x
	@ResponseBody
	@GetMapping("roundaboutway.bibo")
	public String roundaboutway(HttpServletRequest request) {
		
		String key = request.getParameter("key");
		String cancer = request.getParameter("cancer");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String year = request.getParameter("year");
		
		// System.out.println(cancer);
		
		String url = "https://kosis.kr/openapi/Param/statisticsParameterData.do?method=getList&apiKey="+key+"&itmId=16117ac000101+16117AC000107+&objL1="+cancer+"&objL2="+gender+"&objL3="+age+"&objL4=&objL5=&objL6=&objL7=&objL8=&format=json&jsonVD=Y&prdSe=Y&startPrdDe="+year+"&endPrdDe="+year+"&outputFields=NM+ITM_NM+PRD_DE+&orgId=117&tblId=DT_117N_A00023";
		
		RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        //[{"DT":"540.6","PRD_DE":"2021","C1_NM":"모든 암(C00-C96)","ITM_NM":"조발생률","C2_NM":"계","C3_NM":"계"},{"DT":"277523","PRD_DE":"2021","C1_NM":"모든 암(C00-C96)","ITM_NM":"발생자수","C2_NM":"계","C3_NM":"계"}]
		
		// System.out.println(response);
		return response;
	}
	
	
	
	@PostMapping("downloadStatisics.bibo")
	public String downloadStatisicsExcel(HttpServletRequest request, Model model,
										 @RequestParam(required = false) String man
										,@RequestParam(required = false) String man_i
										,@RequestParam(required = false) String woman
										,@RequestParam(required = false) String woman_i
										,@RequestParam(required = false) String age
										,@RequestParam(required = false) String year
										,@RequestParam(required = false) String cancer) {
		 
		System.out.println(man);
		
		return "";
	}
	
	
	
	
	
	
	
	
	
	
}
