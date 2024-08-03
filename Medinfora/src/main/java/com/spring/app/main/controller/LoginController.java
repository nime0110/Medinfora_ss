package com.spring.app.main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.GoogleMail;
import com.spring.app.common.KakaoApi;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.main.service.MainService;

@Controller
public class LoginController {
	
	@Autowired
	private MainService service;
	
	@Autowired
	private KakaoApi KakaoApi;
	
	

	// ==== #243. 빈으로 등록 되어진 GoogleMail 클래스 DI 하기
		@Autowired
		private GoogleMail mail;
	
	// 회원가입 선택 페이지
	@RequestMapping("/register/registerchoice.bibo")
	public ModelAndView registerChoice(ModelAndView mav) {
		
		mav.setViewName("login/registerChoice.tiles");

		return mav;
	}
	
	// 회원가입 페이지 이동
	@GetMapping("/register/register.bibo")
	public ModelAndView register(ModelAndView mav, HttpServletRequest request) {
		
		String join = request.getParameter("join");

		
		if(join != null && ("1".equals(join) || "2".equals(join) || "3".equals(join))) {
			// 1:일반, 2:의료, 3:카카오(일반)
			mav.addObject("join", join);
			mav.setViewName("login/register.tiles");
		}
		else {
			mav.setViewName("redirect:/register/registerchoice.bibo");
		}
		
		return mav;
	}
	
	
	// 아이디, 이메일  중복검사
	@ResponseBody
	@PostMapping("/register/isexistcheckjson.bibo")
	public String isExistCheckJSON(HttpServletRequest request) {
		
		String result = "false";
		JSONObject jsonObj = new JSONObject();
		
		try {
			String value = request.getParameter("value");
			String type = request.getParameter("type");
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("value", value);
			paraMap.put("type", type);
			
			MemberDTO isExist = service.isExistCheck(paraMap);
			
			if(isExist != null) {
				result = "true";
			}
			
			jsonObj.put("result", result);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonObj.toString();
	}
	
	// 병원 찾기 창띄우기
	@RequestMapping(value="/register/searchmedical.bibo")
	public ModelAndView searchmedical(ModelAndView mav) {
		
		mav.setViewName("/login/searchmedical");
		return mav;
	}
	
	
	// 병원찾기(검색 자동완성 json)
	@ResponseBody
	@GetMapping(value="/register/autoWord.bibo", produces="text/plain;charset=UTF-8")
	public String autoWord(HttpServletRequest request) {
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		
		JSONArray jsonArr = new JSONArray();
		try {
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		List<String> autoWordList = service.autoWord(paraMap);
		
		if(autoWordList != null) {
			for(String autoWord : autoWordList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("autoWord", autoWord);
				
				jsonArr.put(jsonObj);
			}// end of for-----------------
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArr.toString();
	}
	
	
	// 병원검색하기
	@ResponseBody
	@GetMapping(value="/register/searchMedicalShow.bibo", produces="text/plain;charset=UTF-8")
	public String searchMedicalShow(HttpServletRequest request) {
		
		List<HospitalDTO> hpList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentPageNo = request.getParameter("currentPageNo");
		
		if(searchType == null) {
			searchType = "";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		
		// 총 게시물 건수(totalCount)를 구해와야 한다.
		int totalCount = 0;        // 총 게시물 건수
		int sizePerPage = 10;      // 한 페이지당 보여줄 게시물 건수 
		int currentPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함. 
		int totalPage = 0;         // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
		
		JSONObject jsonObj = new JSONObject();
			
			
		try {
			
			totalCount = service.totalhospital(paraMap);
			System.out.println("확인용 totalCount "+totalCount);
			
			totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
			
			if(str_currentPageNo == null) {
				currentPageNo = 1;
			}
			else {
				currentPageNo = Integer.parseInt(str_currentPageNo);
				
				if(currentPageNo < 1 || currentPageNo > totalPage) {
					currentPageNo = 1;
				}
				
				int startRno = ((currentPageNo - 1) * sizePerPage) + 1; // 시작 행번호 
				int endRno = startRno + sizePerPage - 1; // 끝 행번호
				
				paraMap.put("startRno", String.valueOf(startRno));
				paraMap.put("endRno", String.valueOf(endRno));
				
				hpList = service.hpSearch(paraMap);
				
				if(hpList != null) {
					
					Map<String, Object> jsonMap = new HashMap<>();
					jsonMap.put("hpList", hpList);
					jsonMap.put("totalCount", totalCount);
					
					
					// 페이지바 관련
					int blockSize = 10; // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수이다.
					int loop = 1;
					int pageNo = ((currentPageNo - 1)/blockSize) * blockSize + 1;
					
					jsonMap.put("blockSize", blockSize);
					jsonMap.put("loop", loop);
					jsonMap.put("pageNo", pageNo);
					jsonMap.put("totalPage", totalPage);
					
					
					jsonObj.put("jsonMap", jsonMap);
					
				}

			}

		}catch(Exception e) {
			e.printStackTrace();
			currentPageNo = 1; 
		}
		
		// return json_arr.toString();
		return jsonObj.toString();
	}
	
	
	// 회원가입(병원찾기 hidx 및 값 입력하기)
	@ResponseBody
	@GetMapping(value="/register/searchMedicalEnd.bibo", produces="text/plain;charset=UTF-8")
	public String searchMedicalEnd(HttpServletRequest request) {
		
		String hpname = request.getParameter("hpname");
		String hpaddr = request.getParameter("hpaddr");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("hpname", hpname);
		paraMap.put("hpaddr", hpaddr);
		
		HospitalDTO hpdto = service.searchMedicalEnd(paraMap);
		
		String addr = "";
		String detailAddr = "";
		if(hpdto.getHpaddr().contains(",")) {
			addr = hpdto.getHpaddr().substring(0, hpdto.getHpaddr().indexOf(","));
			detailAddr = hpdto.getHpaddr().substring(hpdto.getHpaddr().indexOf(",")+1).trim();
		}
		else {
			addr = hpdto.getHpaddr();
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("hidx", hpdto.getHidx());
		jsonObj.put("hpname", hpdto.getHpname());
		jsonObj.put("hpaddr", hpdto.getHpaddr());
		jsonObj.put("hptel", hpdto.getHptel());
		
		jsonObj.put("addr", addr);
		jsonObj.put("detailAddr", detailAddr);
		
		return jsonObj.toString();
	}
	
	// 회원가입 완료
	@PostMapping("/register/registerEnd.bibo")
	public ModelAndView registerEnd(ModelAndView mav, HttpServletRequest request) {
		
		// 초기값 설정
		String userid = "";
		String pwd = "";

		String address = "";
		String detailAddress = "";
		String midx = "";
		String loginmethod = "";
		
		HttpSession session = request.getSession();
		Map<String, String> kakaoInfo = (Map<String, String>)session.getAttribute("kakaoInfo");
		
		String join = request.getParameter("join");
		
		
		
		Map<String, String> paraMap = new HashMap<>();
		
		
		// System.out.println(join);
		// System.out.println(kakaoInfo.get("name"));
		
		paraMap.put("join", join);
		
		
		
		if("1".equals(join) || "2".equals(join)) {
			userid = request.getParameter("userid");
			pwd = request.getParameter("pwd");
			loginmethod = "0";
			
		}
		else {	// 카카오
			userid = kakaoInfo.get("userid");
			pwd = userid+"medinfora";
			loginmethod = "1";
			
		}
		
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		
		paraMap.put("userid", userid);
		paraMap.put("pwd", pwd);
		paraMap.put("loginmethod", loginmethod);
		paraMap.put("email", email);
		paraMap.put("name", name);
		paraMap.put("mobile", mobile);
		
		boolean ismetinsert = true;
		
		if("1".equals(join) || "3".equals(join)) {
			midx = "1";
			address = request.getParameter("address");
			detailAddress = request.getParameter("detailAddress");
			String gender = request.getParameter("gender");
			
			String birthday = request.getParameter("birthday");
			birthday = birthday.replace("-", "");
			
			paraMap.put("midx", midx);
			paraMap.put("address", address);
			paraMap.put("detailAddress", detailAddress);
			paraMap.put("gender", gender);
			paraMap.put("birthday", birthday);
			
			
		}
		else { // 의료
			midx = "2";
			address = request.getParameter("hpaddr");
			String hidx = request.getParameter("hidx");
			
			paraMap.put("midx", midx);
			paraMap.put("address", address);
			paraMap.put("hidx", hidx);
			
		}
		
		int n = service.registerEnd(paraMap);
		
		// 저장했던 카카오 정보 삭제
		session.removeAttribute("kakaoInfo");
		
		String message = "";
		String loc = request.getContextPath()+"/index.bibo";
		
		if(n==1&&ismetinsert) {
			message = "회원가입이 완료되었습니다.";
		}
		else {
			message = "회원가입을 실패하였습니다. 다시 진행해주세요.";
		}
		
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		
		mav.setViewName("msg");
		
		return mav;
	}
	
	
	// 로그인 창 띄우기
	@RequestMapping(value="/login/login.bibo")
	public ModelAndView login(ModelAndView mav, HttpServletRequest request) {
		
		String isFail = request.getParameter("isfail");
		
		if(isFail == null) {
			isFail = "n";
		}
		
		mav.addObject("kakaoApiKey", KakaoApi.getKakaoApiKey());
		mav.addObject("RedirectUri", KakaoApi.getRedirectUri());
		mav.addObject("isFail",isFail);
		
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
		
		String isExistUser = "false";
		String message = "카카오로 회원가입된 계정이 없습니다.\\n회원가입 페이지로 이동하시겠습니까?";
		
		try {
		
			String code = request.getParameter("code");
			
			String accessToken = KakaoApi.getAccessToken(code);
			
			Map<String, Object> userInfo = KakaoApi.getUserInfo(accessToken);
			
			String name = (String)userInfo.get("name");
			String email = (String)userInfo.get("email");
			// String is_email_verified = (String)userInfo.get("is_email_verified"); // 이메일 유효성
			String birth_year = (String)userInfo.get("birthyear");
			String birth_day = (String)userInfo.get("birthday");
			String phone_number = (String)userInfo.get("phone_number");
			String gender = (String)userInfo.get("gender");
			
			String userid = email.substring(0, email.indexOf("@"))+"_kakao";
			
			String birthday = birth_year+"-"+birth_day.substring(0, 2)+"-"+birth_day.substring(2);
			
			phone_number = phone_number.substring(4);
			
			StringBuilder sb = new StringBuilder();
			
			String[] mobile_arr = phone_number.split("");
			
			for(int i=0; i<mobile_arr.length; i++) {
				
				if(!"-".equals(mobile_arr[i])) {
					sb.append(mobile_arr[i]);
				}
				
			}// end of for
			String mobile = "0"+sb.toString();
			
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
					
					if(loginuser.getPwdchangegap() >= 3) {
						message = "비밀번호를 변경하신지 3개월이 지났습니다. \\n 비밀번호 변경을 권유드립니다.";
					}
					
				}
				
				out = response.getWriter();
				// out 은 웹브라우저에 기술하는 대상체라고 생각하자.
				
				StringBuilder sb_script = new StringBuilder();
				
				sb_script.append("<script type='text/javascript'>");
				sb_script.append("const iskakao ='"+isExistUser+"';");
				sb_script.append("const message ='"+message+"';");
				// sb_script.append("alert("+message+");");
				sb_script.append("window.opener.loginWithKakaoEnd(iskakao, message);");
				sb_script.append("window.close();</script>");

				String script = sb_script.toString();
				out.println(script);
				
			}
			else {
				// 가입된 적이 없으므로 세션에 정보를 저장한뒤 회원가입 페이지에서 꺼내온 후 삭제
				// 팝업창을 닫고, 회원가입 폼으로 넘겨준다.
				System.out.println("가입한적 없음");
				
				Map<String, String> kakaoInfo = new HashMap<>();
				kakaoInfo.put("userid", userid);
				kakaoInfo.put("name", name);
				kakaoInfo.put("email", email);
				kakaoInfo.put("birthday", birthday);
				kakaoInfo.put("mobile", mobile);
				kakaoInfo.put("gender", gender);
				
				// 비밀번호는 암호화로 넣어주기만할거임
				
				// 회원가입시 필요한 정보 전달 사용후 삭제할거임
				HttpSession session = request.getSession();
				session.setAttribute("kakaoInfo", kakaoInfo);
				
				
				out = response.getWriter();

				out.println("<script type='text/javascript'>const iskakao = '"+isExistUser+"'; const message ='nouser'; if(confirm('"+message+"')){window.opener.loginWithKakaoEnd(iskakao, message);}else{window.opener.nokakaoregister();}; window.close();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out = response.getWriter();
				out.println("<script type='text/javascript'>alert('로그인 실패하였습니다.'); window.close();</script>");
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// 이제 넘겨주기만 하면 됨
		return;
	}// end of public void kakaoLogin(HttpServletRequest request)
	
	@ResponseBody
	@GetMapping("/register/checkhidx.bibo")
	public String checkhidx(HttpServletRequest request) {
		
		String hidx = request.getParameter("hidx");
		
		JSONObject jsonObj = new JSONObject();
		
		if(service.checkhidx(hidx)) {
			jsonObj.put("checkHidx", false);
		}else {
			jsonObj.put("checkHidx", true);
		}
				
		return jsonObj.toString();
	}
	

	// 아이디 찾기 및 페이지 이동
	
	@GetMapping("/findId.bibo")
	public ModelAndView gofindId(ModelAndView mav, HttpServletRequest request) {
		mav.setViewName("login/findId.tiles");
		return mav;
	}
	

}
