package com.spring.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping(value="/")
	public ModelAndView commom(ModelAndView mav) {
		
		mav.setViewName("redirect:index.bibo");
		
		return mav;
	}
	
	@RequestMapping(value="/index.bibo")
	public ModelAndView index(ModelAndView mav) {
		
		mav.setViewName("index.tiles");
		
		return mav;
	}
	
}