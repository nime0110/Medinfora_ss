package com.spring.app.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/news/")
public class NewsController {

	@GetMapping("main.bibo")
	public ModelAndView newsmain(ModelAndView mav) {
		
		mav.setViewName("news/main.tiles");
		
		return mav;
	}
	
	
}
