package com.spring.app.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {

	@GetMapping("/mypage/myinfo.bibo")
	public ModelAndView myinfo(ModelAndView mav) {
		
		mav.setViewName("mypage/myinfo.info");
		
		return mav;
	}
	
}
