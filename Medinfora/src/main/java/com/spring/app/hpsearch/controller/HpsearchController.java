package com.spring.app.hpsearch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class HpsearchController {
	
	@GetMapping("/hpsearch/hospitalSearch.bibo")
	public ModelAndView medistatistic(ModelAndView mav) {
		mav.setViewName("hospitalSearch/hospitalSearch.tiles");
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="/hpsearch/hpsearchAdd.bibo", produces="text/plain;charset=UTF-8")
	public String hpsearchAdd(HttpServletRequest request) {
		
		String sido = request.getParameter("sido"); //경기도
		String sigungu = request.getParameter("sigungu"); //광명시
		String classcode = request.getParameter("classcode"); //D004
		String agency = request.getParameter("agency"); //의원
		System.out.println("~~sido:" + sido);
		System.out.println("~~sigungu:" + sigungu);
		System.out.println("~~classcode:" + classcode);
		System.out.println("~~agency:" + agency);
		
		
		
		
		return "";

	}
	
	
}
