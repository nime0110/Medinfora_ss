package com.spring.app.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class NoticeController {
	
	@GetMapping(value = "/notice/noticeList.bibo")
	public ModelAndView notice(ModelAndView mav) {
		
		mav.setViewName("notice/noticeList.tiles");
		//mav.setViewName("notice/noticeView.tiles");
		// /WEB-INF/views/notice/notice.jsp
		return mav;
	
	}
}
