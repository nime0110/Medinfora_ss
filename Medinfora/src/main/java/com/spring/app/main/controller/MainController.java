package com.spring.app.main.controller;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.spring.app.common.Myutil;
//import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping(value="/")
	public ModelAndView commom(ModelAndView mav) {
		
		/* API 입력 영역
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
		*/
		
		mav.setViewName("redirect:index.bibo");
		
		return mav;
	}
	
	@RequestMapping(value="/index.bibo")
	public ModelAndView index(ModelAndView mav) {
		
		mav.setViewName("index.tiles");
		
		return mav;
	}
	
	// 로그인 창 띄우기
	@RequestMapping(value="/login.bibo")
	public ModelAndView login(ModelAndView mav) {
		
		mav.setViewName("login");
		
		return mav;
	}
	
	
	@PostMapping("/loginEnd.bibo")
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {
		
		try {
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			
			String clientip = request.getRemoteAddr();	// 클라이언트 IP 주소 알아옴
			
			// System.out.println(userid);
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			paraMap.put("clientip", clientip);

			mav = service.loginEnd(paraMap, mav, request);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mav;
	}
	
	@GetMapping("/notice.bibo")
	public String notice() {
		
		return "notice";
	}
	
}