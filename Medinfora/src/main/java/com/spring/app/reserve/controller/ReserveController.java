package com.spring.app.reserve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReserveController {

	@GetMapping("/reserve/choiceDr.bibo")
	public ModelAndView choiceDr(ModelAndView mav) {
		
		mav.setViewName("reserve/choiceDr.tiles");
		
		return mav;
	}
	
	@GetMapping("/reserve/choiceDay.bibo")	// 나중에 POST 로 변경할 예정
	public ModelAndView choiceDay(ModelAndView mav) {
		
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}
	
}
