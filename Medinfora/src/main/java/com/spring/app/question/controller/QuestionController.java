package com.spring.app.question.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.question.service.QuestionService;

@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionservice;
	
	
	@RequestMapping(value="/questionList.bibo")
	public ModelAndView questionList(ModelAndView mav) {
		
		
		mav.setViewName("question/questionList.tiles");
		
		return mav;
	}
	
	
	
	
	
	
	
	
}
