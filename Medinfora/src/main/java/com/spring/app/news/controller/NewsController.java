package com.spring.app.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.NewsDTO;
import com.spring.app.news.service.NewsService;

@Controller
@RequestMapping(value="/news/")
public class NewsController {
	
	@Autowired
	private NewsService service;

	@GetMapping("main.bibo")
	public ModelAndView newsmain(ModelAndView mav, HttpServletRequest request,
			@RequestParam(value="str_currentPageNo",defaultValue = "1")String str_currentPageNo) {
		
		int totalCount = 0;
		int sizePerPage = 0;
		int currentPageNo = 0;
		int totalPage = 0;
		
		String pagebarStr = "";
		
		try {
			
			totalCount = service.totalcount();
			sizePerPage = 10;
			currentPageNo = Integer.valueOf(str_currentPageNo);
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
			
			if(currentPageNo < 1 || currentPageNo > totalPage) {
				currentPageNo = 1;
			}
			
			int startRno = ((currentPageNo - 1) * sizePerPage) + 1;
			int endRno = startRno + sizePerPage - 1;
			
			Map<String,Integer> listparaMap = new HashMap<>();
			listparaMap.put("startRno",startRno);
			listparaMap.put("endRno",endRno);
			
			List<NewsDTO> ndtolist = service.getndtolist(listparaMap);
			
			StringBuilder pageBar = new StringBuilder("<ul class='pagination hj_pagebar nanum-n size-s'>");
			
			int blockSize = 10; // Page block size
			int startPage = ((currentPageNo - 1) / blockSize) * blockSize + 1;
			int endPage = startPage + blockSize - 1;
			
			if (endPage > totalPage) {
			    endPage = totalPage;
			}
			
			if (startPage > 1) {
			    pageBar.append("<li class='page-item'><a class='page-link' href='/Medinfora/news/main.bibo").append("?str_currentPageNo=").append(startPage - 1).append("'>Previous</a></li>");
			}
			
			for (int i = startPage; i <= endPage; i++) {
			    if (i == currentPageNo) {
			        pageBar.append("<li class='page-item active'><span class='page-link'>").append(i).append("</span></li>");
			    } else {
			        pageBar.append("<li class='page-item'><a class='page-link' href='/Medinfora/news/main.bibo").append("?str_currentPageNo=").append(i).append("'>").append(i).append("</a></li>");
			    }
			}
			
			if (endPage < totalPage) {
			    pageBar.append("<li class='page-item'><a class='page-link' href='/Medinfora/news/main.bibo").append("?str_currentPageNo=").append(endPage + 1).append("'>Next</a></li>");
			}
			
			pageBar.append("</ul>");

			pagebarStr = pageBar.toString();
			
			mav.addObject("ndtolist",ndtolist);
			
		}catch (Exception e) {
			mav.setViewName("redirect:/index.bibo");
			return mav;
		}

		mav.addObject("pageBar",pagebarStr);
		mav.setViewName("news/main.tiles");
		
		return mav;
	}
	
	@GetMapping("view.bibo")
	public ModelAndView view(ModelAndView mav, HttpServletRequest request ,
			@RequestParam(value="nidx",defaultValue = "1") String strNidx) {
		
		try {
			int nidx = Integer.valueOf(strNidx);
			
			NewsDTO ndto = service.getndto(nidx);
			
			if(ndto==null) {
				mav.setViewName("redirect:/index.bibo");
				return mav;
			}
		
			mav.addObject("ndto",ndto);
				
		}catch (Exception e) {
			mav.setViewName("redirect:/index.bibo");
		}
		
		mav.setViewName("news/view.tiles");
		
		return mav;
	}
	
	@GetMapping("del.bibo")
	public ModelAndView isAdmin_del(ModelAndView mav, HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="nidx",defaultValue = "0") String nidxStr) {
		
		try {
			
			int nidx = Integer.valueOf(nidxStr);
			
			int sus = service.del(nidx);
			
			if(sus!=1) {
				mav.setViewName("redirect:/index.bibo");
			}else {
				mav.setViewName("redirect:/news/main.bibo");
			}
			
		}catch (Exception e) {
			mav.setViewName("redirect:/index.bibo");
		}
		
		return mav;
	}
	
}