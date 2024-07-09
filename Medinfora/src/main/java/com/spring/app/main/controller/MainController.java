package com.spring.app.main.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.weaver.ast.Not;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.NoticeDTO;
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
		
		List<NoticeDTO> ndtoList = service.getIdxNdtoList();
		
		mav.setViewName("index.tiles");
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getcityinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getareainfo() {
		
		List<String> citylist = service.getcityinfo();
		
		JSONArray jsonarr = new JSONArray();
		
		for(String city : citylist) {
			jsonarr.add(city);
		}
		
		return jsonarr.toString();
		
	}// end of public String getareainfo()
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getlocalinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getlocalinfo(HttpServletRequest request) {
		
		String city = request.getParameter("city");
		
		List<String> locallist = service.getlocalinfo(city);
		
		JSONArray jsonarr = new JSONArray();
		
		for(String local : locallist) {
			jsonarr.add(local);
		}
		
		return jsonarr.toString();
		
	}// end of public String getlocalinfo(HttpServletRequest request)
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getcountryinfo.bibo", produces = "text/plain;charset=UTF-8")
	public String getcountryinfo(HttpServletRequest request) {
		
		String city = request.getParameter("city");
		String local = request.getParameter("local");
		
		KoreaAreaVO inputareavo = new KoreaAreaVO(city, local);
		
		List<String> countryList = service.getcountryinfo(inputareavo);
		
		JSONArray jsonarr = new JSONArray();
		
		for(String country : countryList) {
			jsonarr.add(country);
		}
		
		return jsonarr.toString();
		
	}// end of public String getcountryinfo(HttpServletRequest request)
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getclasscode.bibo", produces="text/plain;charset=UTF-8")
	public String getclasscode() {
		
		JSONArray jsonarr = new JSONArray();
		
		List<ClasscodeDTO> clsscodeDTOList = service.getclasscode();
		
		for(ClasscodeDTO clsscodeDTO : clsscodeDTOList) {
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("classname",clsscodeDTO.getClassname());
			jsonObj.put("classcode",clsscodeDTO.getClasscode());
			
			jsonarr.add(jsonObj);
		}
		
		return jsonarr.toString();
	}// end of public String getclasscode()
	
}