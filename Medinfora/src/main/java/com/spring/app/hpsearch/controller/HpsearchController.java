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
		
		String region = request.getParameter("region"); //서초구
		System.out.println("~~region:" + region);
		
		
		
		
		return "";

	}
	
	
}
