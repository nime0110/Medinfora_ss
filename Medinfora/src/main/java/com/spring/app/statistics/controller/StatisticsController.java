package com.spring.app.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class StatisticsController {
	
	@GetMapping("/hospitalSearch/hospitalSearch.bibo")
	public ModelAndView medistatistic(ModelAndView mav) {
		mav.setViewName("hospitalSearch/hospitalSearch.tiles");
		return mav;
	}
	
	
}
