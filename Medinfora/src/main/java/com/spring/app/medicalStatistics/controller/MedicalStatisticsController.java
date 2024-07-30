package com.spring.app.medicalStatistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
}
