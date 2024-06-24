package com.spring.app.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@GetMapping("/")
	public ModelAndView index(ModelAndView mav) {
		
		mav.setViewName("home");
		
		return mav;
	}
	
}
