package com.spring.app.customerService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerServiceController {

	@GetMapping("/customerService.bibo")
	public ModelAndView customerService(ModelAndView mav) {
		mav.setViewName("customerService/customerService.tiles");
		return mav;
	}
	
}
