package com.spring.app.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class NoticeController {
	
	  @GetMapping(value = "/notice/noticeList.bibo")
	    public ModelAndView noticeList(ModelAndView mav) {
	        mav.setViewName("notice/noticeList.tiles");
	        return mav;
	    }

	    @GetMapping(value = "/notice/noticeWrite.bibo")
	    public ModelAndView noticeWrite(ModelAndView mav) {
	        mav.setViewName("notice/noticeWrite.tiles");
	        return mav;
	    }
	    
	    @GetMapping(value = "/notice/noticeView.bibo")
	    public ModelAndView noticeView(ModelAndView mav) {
	        mav.setViewName("notice/noticeView.tiles");
	        return mav;
	    }
}
