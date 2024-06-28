package com.spring.app.main.controller;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.KakaoApi;
import com.spring.app.main.domain.MemberDTO;
//import com.spring.app.common.Myutil;
//import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@Autowired
	private KakaoApi KakaoApi;
	
	
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
	@RequestMapping(value="/login/login.bibo")
	public ModelAndView login(ModelAndView mav) {
		
		mav.addObject("kakaoApiKey", KakaoApi.getKakaoApiKey());
		mav.addObject("RedirectUri", KakaoApi.getRedirectUri());
		
		mav.setViewName("/login/login");
		
		return mav;
	}
	
	
	
	// 로그인
	@ResponseBody
	@PostMapping("/login/loginEnd.bibo")
	public String loginEnd(HttpServletRequest request) {
		
		JSONObject jsonObj = new JSONObject();
		String isExistUser = "";
		String message = "로그인을 실패하였습니다.";
		
		try {
			
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			
			String clientip = request.getRemoteAddr();
			String loginmethod = "0";
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("pwd", pwd);
			paraMap.put("clientip", clientip);
			paraMap.put("loginmethod", loginmethod);
			
			MemberDTO loginuser = service.loginEnd(paraMap, request);
			
			if(loginuser != null) {
				
				// 휴면 회원인 경우
				if(loginuser.getmIdx() == 3 || loginuser.getmIdx() == 4) {
					message = "로그인을 한지 1년이 경과하여 휴면 처리되었습니다. \\n휴면해제 페이지로 이동합니다.";
					isExistUser = "freeze";
				}
				// 정지 회원인 경우
				else if(loginuser.getmIdx() == 8) {
					message = "이용 정지된 회원입니다. \\n관리자에게 문의 바랍니다.";
					isExistUser = "suspended";
					
				}
				else {
					message = "로그인 성공하였습니다.";
					isExistUser = "true";
					jsonObj.put("pwdchangegap", loginuser.getPwdchangegap());
				}
				
			}
			
			jsonObj.put("message", message);
			jsonObj.put("isExistUser", isExistUser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj.toString();
	}
	
	// 로그아웃
	@PostMapping("/login/logout.bibo")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
		
		String url = request.getParameter("url");
		
		mav = service.logout(mav, request,url);
		return mav;
	}
	
	// 카카오 로그인
	@RequestMapping(value="/login/kakaoLogin.bibo")
	public void kakaoLogin(HttpServletRequest request) {
		
		String code = request.getParameter("code");
		// System.out.println(code);
		
		String accessToken = KakaoApi.getAccessToken(code);
		// System.out.println("확인용 accessToken"+accessToken);
		
		Map<String, Object> userInfo = KakaoApi.getUserInfo(accessToken);
		
		String name = (String)userInfo.get("name");
		String email = (String)userInfo.get("email");
		String is_email_verified = (String)userInfo.get("is_email_verified");
		String birth_year = (String)userInfo.get("birthyear");
		String birth_day = (String)userInfo.get("birthday");
		String phone_number = (String)userInfo.get("phone_number");
		
		String userid = email.substring(0, email.indexOf("@"))+"_kakao";
		
		// System.out.println(userid);
	
		
		// 가정은 다음과 같다
		/*
			카카오로 로그인 시 카카오계정(이메일형식)에서 @ 기준으로 id 추출해서 _kakao 추가해서 아이디를 인서트한다.으로 가입한 적이 있는지 확인(loginmethod로 select 조건 추가) 검증한다음
			없으면, 회원가입 폼으로 넘긴다.
		*/
		
		String birthday = birth_year+birth_day;
		// System.out.println("확인용 birthday : "+birthday);
		// 확인용 birthday : 19950406
		

		// System.out.println("확인용 카카오 연락처 정보 : "+phone_number);
		// +82 10-xxxx-xxxx
		
		phone_number = phone_number.substring(4);
		
		StringBuilder sb = new StringBuilder();
		
		String[] mobile_arr = phone_number.split("");
		
		for(int i=0; i<mobile_arr.length; i++) {
			
			if(!"-".equals(mobile_arr[i])) {
				sb.append(mobile_arr[i]);
			}
			
		}// end of for -----
		
		String mobile = "0"+sb.toString();
		
		// System.out.println("확인용 mobile: "+mobile);
		// 확인용 mobile: 010xxxxxxxx
		
		
		// service 에 넘겨줄 데이터 loginEnd에 mapper 에서 where 문 사용
		String clientip = request.getRemoteAddr();
		String loginmethod = "1";
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("clientip", clientip);
		paraMap.put("loginmethod", loginmethod);

		
		// 유저가 가입되어 잇는지 있는지 확인한다.
		MemberDTO loginuser = service.loginEnd(paraMap, request);
		
		
		if(loginuser != null) {
			// 가입된 적이 있는 경우 로그인 처리를 해줘야한다.
		}
		else {
			// 가입된 적이 없으므로 회원가입 폼으로 넘겨준다.
		}
		
		
		// 이제 넘겨주기만 하면 됨
		return;
	}
	
	@GetMapping("/notice.bibo")
	public String notice() {
		
		return "notice";
	}
	
}