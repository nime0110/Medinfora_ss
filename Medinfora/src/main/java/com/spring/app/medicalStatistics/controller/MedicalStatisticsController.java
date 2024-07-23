package com.spring.app.medicalStatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/status/")
public class MedicalStatisticsController {

	@GetMapping("view.bibo")
	public ModelAndView status(ModelAndView mav) {
		mav.setViewName("status/view.tiles");
		return mav;
	}
	
	@GetMapping("serviceUtilization.bibo")
	public ModelAndView serviceUtilization(ModelAndView mav) {
		mav.setViewName("status/serviceUtilization.status");
		return mav;
	}
	
}
