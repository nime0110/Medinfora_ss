package com.spring.app.main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
//import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.KakaoApi;
import com.spring.app.main.domain.ClasscodeDTO;
import com.spring.app.main.domain.MemberDTO;
//import com.spring.app.common.Myutil;
//import com.spring.app.main.domain.HospitalDTO;
//import com.spring.app.main.domain.KoreaAreaVO;
import com.spring.app.main.service.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;
	
	@Autowired
	private KakaoApi KakaoApi;
	
	
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
	public void kakaoLogin(HttpServletRequest request, HttpServletResponse response) {
		
		// **** 웹브라우저에 출력하기 시작 **** //
		// HttpServletResponse response 객체는 전송되어져온 데이터를 조작해서 결과물을 나타내고자 할때 쓰인다.
	      
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = null;
		
		try {
		
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
			
			String isExistUser = "";
			String message = "로그인을 실패하였습니다.";
			
					
			if(loginuser != null) {
				// 가입된 적이 있는 경우
				// 로그인 정보는 sevice 에서 session 에 정보를 저장하였다.
				// 팝업창을 닫고, 새로고침 해줘야한다.
				System.out.println("로그인 성공");
				
				// 휴면 회원인 경우
				if(loginuser.getmIdx() == 3) {
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
					String pwdchangegap = String.valueOf(loginuser.getPwdchangegap());
				}
				
				out = response.getWriter();
				// out 은 웹브라우저에 기술하는 대상체라고 생각하자.
				
				StringBuilder sb_script = new StringBuilder();
				
				sb_script.append("<script type='text/javascript'>");
				sb_script.append("const kakaouser = {} ");
				
				
				String script = "";
				
				
				out.println("<script type='text/javascript'> const iskakao = \"false\"; alert('로그인 성공하였습니다.'); window.opener.loginWithKakaoEnd(iskakao); window.close();</script>");
				
			}
			else {
				// 가입된 적이 없으므로 
				// 팝업창을 닫고, 회원가입 폼으로 넘겨준다.
				System.out.println("로그인 실패");
				
				out = response.getWriter();

				out.println("<script type='text/javascript'>const iskakao = \"false\"; alert('로그인 실패하였습니다.'); window.opener.loginWithKakaoEnd(iskakao); window.close();</script>");
			}
		

		} catch (Exception e) {
			e.printStackTrace();
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>const iskakao = \"false\"; alert('로그인 실패하였습니다.'); window.opener.loginWithKakaoEnd(iskakao); window.close();</script>");
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
		
		
		// 이제 넘겨주기만 하면 됨
		return;
	}// end of public void kakaoLogin(HttpServletRequest request)
	
	@GetMapping("/notice.bibo")
	public String notice() {
		
		return "notice";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getareainfo.bibo", produces="text/plain;charset=UTF-8")
	public String getareainfo() {
		
		List<String> arealist = service.getareainfo();
		
		JSONArray jsonarr = new JSONArray();
		
		for(String area : arealist) {
			jsonarr.add(area);
		}
		
		return jsonarr.toString();
		
	}// end of public String getareainfo()
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getlocalinfo.bibo", produces="text/plain;charset=UTF-8")
	public String getlocalinfo(HttpServletRequest request) {
		
		String area = request.getParameter("area");
		
		List<String> locallist = service.getlocalinfo(area);
		
		JSONArray jsonarr = new JSONArray();
		
		for(String local : locallist) {
			jsonarr.add(local);
		}
		
		return jsonarr.toString();
		
	}// end of public String getlocalinfo(HttpServletRequest request)
	
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