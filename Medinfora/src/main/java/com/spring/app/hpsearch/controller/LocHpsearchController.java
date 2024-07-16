package com.spring.app.hpsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.hpsearch.service.HpsearchService;
import com.spring.app.hpsearch.service.LocHpsearchService;

@Controller
public class LocHpsearchController {
	
	//의존객체주입
	@Autowired
	private LocHpsearchService service;
	
	@GetMapping("/hpsearch/locHospitalSearch.bibo")
	public ModelAndView medistatistic(ModelAndView mav) {
		mav.setViewName("hospitalSearch/locHospitalSearch.tiles");
		return mav;
	}
}
