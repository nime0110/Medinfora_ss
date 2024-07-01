package com.spring.app.reserve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.reserve.service.ReserveService;

@Controller
public class ReserveController {

	@Autowired
	private ReserveService service;
	
	@GetMapping("/reserve/choiceDr.bibo")
	public ModelAndView isLogin_choiceDr(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		List<HospitalDTO> mbHospitalList = null;
		
		String Area = request.getParameter("city");
		String local = request.getParameter("loc");
		String classcode = request.getParameter("dept");
		String hpname = request.getParameter("hpname");
		
		if("시/도 선택".equals(Area)) {
			Area = "";
		}
		if("시/군구 선택".equals(local)) {
			local = "";
		}
		if("진료과목 선택".equals(classcode)) {
			classcode = "";
		}
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("Area", Area);
		paraMap.put("local", local);
		paraMap.put("classcode", classcode);
		paraMap.put("hpname", hpname);
		
		/*
		int totalCnt = 0;	// 총 개수
		int sizePerPage = 12;	// 한 페이지당 보여줄 개수
		int currentShowPageNo = 0;	// 현재 페이지
		int totalPage = 0;
		
		totalCnt = service.getmbHospitalCnt();	// 회원가입된 병원 개수
		
		totalPage = (int)Math.ceil((double)totalCnt/sizePerPage);
		*/
		
		mbHospitalList = service.mbHospitalList(paraMap);	// 회원가입된 병원 리스트 가져오기
		
		mav.addObject("mbHospitalList",mbHospitalList);
		
		mav.setViewName("reserve/choiceDr.tiles");
		
		return mav;
	}
	
	@GetMapping("/reserve/choiceDay.bibo")	// 나중에 POST 로 변경할 예정
	public ModelAndView choiceDay(ModelAndView mav) {
		
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}
	
}
