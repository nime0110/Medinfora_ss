package com.spring.app.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.Myutil;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.NewsDTO;
import com.spring.app.main.service.MainService;


@Controller
public class TestController {

	@Autowired
	private MainService service;
	
	@RequestMapping(value="/test.bibo")
	public ModelAndView commom(ModelAndView mav) {
		
		
		if(true) {
			try {
				
				String localAddr = "";
				
				List<HospitalDTO> hpdtoList = Myutil.hpApiInputer(localAddr);
				
				System.out.println("데이터 입력시작");
				int totalSize = hpdtoList.size();
				for(int i=0;i<hpdtoList.size();i++) {
					
					System.out.print("진행상황 ["+(i+1)+"/"+totalSize+"]");
					if(service.hpApiInputer(hpdtoList.get(i))==1) {
						System.out.println("...성공");
					}else {
						System.out.println("...실패");
					}
					
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(true) {
			try {
				
				String localAddr = "C:\\NCS\\Medinfora\\Medinfora\\src\\main\\webapp\\resources\\json\\koreaarea.json";
				
				List<KoreaAreaVO> areaList = Myutil.areaInputer(localAddr);
				
				int totalSize = areaList.size();
				
				for(int i=0;i<areaList.size();i++) {
					
					System.out.print("진행상황 ["+(i+1)+"/"+totalSize+"]");
					if(service.areaInputer(areaList.get(i))==1) {
						System.out.println("...성공");
					}else {
						System.out.println("...실패");
					}
					
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(true) {
			try {
				String holidayAdr = "";
				
				List<HolidayVO> holidayList = Myutil.holidayApiInputer(holidayAdr);
				System.out.println("데이터 입력시작");
				int totalSize = holidayList.size();
				
				for(int i=0;i<holidayList.size();i++) {
					
					System.out.print("진행상황 ["+(i+1)+"/"+totalSize+"]");
					if(service.holidayInputer(holidayList.get(i))==1) {
						System.out.println("...성공");
					}else {
						System.out.println("...실패");
					}
					
				}	// end of for---------------
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			String localAddr = "";
			
			List<NewsDTO> ndtoList = Myutil.newsInputer(localAddr);
			
			System.out.println("데이터 입력시작");
			int totalSize = ndtoList.size();
			for(int i=0;i<ndtoList.size();i++) {
				
				System.out.print("진행상황 ["+(i+1)+"/"+totalSize+"]");
				if(service.newsInputer(ndtoList.get(i))==1) {
					System.out.println("...성공");
				}else {
					System.out.println("...실패");
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.setViewName("redirect:index.bibo");
		
		return mav;
	}
	
}
