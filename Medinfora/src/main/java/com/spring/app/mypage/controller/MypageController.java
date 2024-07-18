package com.spring.app.mypage.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.mypage.service.MypageService;

@Controller
@RequestMapping(value = "/mypage/")
public class MypageController {

	@Autowired
	private MypageService service;

	@Autowired
	private AES256 aES256;

	@GetMapping("myinfo.bibo")
	public ModelAndView isLogin_myinfo(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {

		mav.setViewName("mypage/myinfo.info");

		return mav;
	}

	@PostMapping("updatemember.bibo")
	public ModelAndView isLogin_updatemember(ModelAndView mav, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> paraMap = new HashMap<>();

		String message = "내 정보 변경에 실패하였습니다.";
		String loc = "myinfo.bibo";

		String userid = request.getParameter("userid");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String detailaddress = request.getParameter("detailaddress");

		if (address == null) {
			address = "";
			detailaddress = "";
		}

		paraMap.put("userid", userid);
		paraMap.put("mobile", mobile);
		paraMap.put("address", address);
		paraMap.put("detailaddress", detailaddress);

		if (service.updateinfo(paraMap)) {
			message = "내 정보를 변경하였습니다.";
			HttpSession session = request.getSession();
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			loginuser.setMobile(mobile);
			if (address != "") {
				loginuser.setAddress(address);
				loginuser.setDetailAddress(detailaddress);
			}
		}

		mav.addObject("message", message);
		mav.addObject("loc", loc);

		mav.setViewName("msg");

		return mav;
	}

	@ResponseBody
	@PostMapping("nowpwdCheck.bibo")
	public String nowpwdCheck(HttpServletRequest request) {

		JSONObject jsonObj = new JSONObject();

		Map<String, String> paraMap = new HashMap<String, String>();

		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");

		paraMap.put("userid", userid);
		paraMap.put("pwd", pwd);

		if (service.nowpwdCheck(paraMap)) {
			jsonObj.put("isPwd", true);
		} else {
			jsonObj.put("isPwd", false);
		}

		return jsonObj.toString();
	}

	@PostMapping("updatepwd.bibo")
	public ModelAndView isLogin_updatepwd(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> paraMap = new HashMap<String, String>();

		String message = "비밀번호 변경을 실패하였습니다.";
		String loc = "myinfo.bibo";

		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");

		paraMap.put("userid", userid);
		paraMap.put("pwd", pwd);

		if (service.updatepwd(paraMap) == 1) {
			message = "비밀번호를 변경하였습니다.";
		}

		mav.addObject("message", message);
		mav.addObject("loc", loc);
		mav.setViewName("msg");

		return mav;
	}

	// === (일반회원) 진료예약 열람 === //
	@GetMapping("myreserve.bibo")
	public ModelAndView isLogin_myreserve(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/myreserve.info");
		return mav;
	}

	// === (의료인) 진료예약 열람 === //
	@GetMapping("mdreserve.bibo")
	public ModelAndView isDr_mdreserve(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		mav.setViewName("mypage/mdreserve.info");
		return mav;
	} // end of public ModelAndView isLogin_mdreserve(ModelAndView
		// mav,HttpServletRequest request, HttpServletResponse response) {-----

	// === (의료인) 진료예약열람(페이징, 검색 처리) === //
	@ResponseBody
	@GetMapping(value = "mdreserveList.bibo", produces = "text/plain;charset=UTF-8")
	public String mdreserveList(HttpServletRequest request) {

		HttpSession session = request.getSession();

		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

		String userid = loginuser.getUserid();

		String hidx = "";

		// 아이디를 통해 병원인덱스 값 찾기
		hidx = service.Searchhospital(userid);

		String currentShowPageNo = request.getParameter("currentShowPageNo");

		String sclist = request.getParameter("sclist"); // 검색 구분
		String inputsc = request.getParameter("inputsc"); // 검색한 값

		if (inputsc != null) {
			inputsc = inputsc.trim();
		} else {
			inputsc = "";
		}

		int sizePerPage = 10; // 한 페이지당 보여줄 개수

		if (currentShowPageNo == null) { // 처음 접속한 경우
			currentShowPageNo = "1";
		}

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호
		int endRno = startRno + sizePerPage - 1; // 끝 행번호

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		paraMap.put("sclist", sclist);
		paraMap.put("inputsc", inputsc);

		paraMap.put("hidx", hidx);

		List<ReserveDTO> reserveList = null;

		// hidx 의 현재 예약리스트 가져오기(검색포함)
		reserveList = service.reserveList(paraMap);

		int totalCnt = service.reserveListCnt(paraMap); // 리스트 총 결과 개수
		int totalPage = (int) Math.ceil((double) totalCnt / sizePerPage);

		JSONArray jsonArr = new JSONArray();
		List<MemberDTO> memberList = null;

		if (reserveList != null) { // 예약리스트가 존재하는 경우
			for (ReserveDTO rsdto : reserveList) {
				String patient_id = rsdto.getUserid();

				// 예약된 환자의 아이디 값을 가지고 이름과 전화번호 알아오기
				memberList = service.GetPatientInfo(patient_id);
				if (memberList != null) {
					for (MemberDTO mdto : memberList) {
						try {
							// select 용으로 사용되는 값에 담기
							rsdto.setName(mdto.getName());
							rsdto.setMobile(aES256.decrypt(mdto.getMobile()));
						} catch (UnsupportedEncodingException | GeneralSecurityException e) {
							e.printStackTrace();
						}
					} // end of for---------------
				}

				JSONObject jsonObj = new JSONObject();
				jsonObj.put("checkin", rsdto.getCheckin());
				jsonObj.put("name", rsdto.getName());
				jsonObj.put("mobile", rsdto.getMobile());
				jsonObj.put("reportday", rsdto.getReportday());
				jsonObj.put("rcode", rsdto.getRcode());
				jsonObj.put("ridx", rsdto.getRidx());

				jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);

				jsonArr.put(jsonObj);

			} // end of for------------------
		}

		return jsonArr.toString();
	} // end of public String mdreserveList(HttpServletRequest request) {-----

	// === (의료인) 진료현황 변경 모달창 정보 === //
	@ResponseBody
	@GetMapping(value = "getRdto.bibo", produces = "text/plain;charset=UTF-8")
	public String getRdto(HttpServletRequest request) {
		String ridx = request.getParameter("ridx");

		ReserveDTO rsdto = null;
		if (ridx != null) {
			// ridx 를 통해 예약 정보 가져오기
			rsdto = service.getRdto(ridx);
		}

		JSONObject jsonObj = new JSONObject(); // {}
		if (rsdto != null) {
			try {
				rsdto.setMobile(aES256.decrypt(rsdto.getMobile()));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}
			jsonObj.put("ridx", rsdto.getRidx());
			jsonObj.put("name", rsdto.getName());
			jsonObj.put("mobile", rsdto.getMobile());
			jsonObj.put("rStatus", rsdto.getrStatus());
			jsonObj.put("reportday", rsdto.getReportday());
			jsonObj.put("checkin", rsdto.getCheckin());
		}
		return jsonObj.toString();
	} // end of public String getRdto(HttpServletRequest request) {---------------

	// === 진료현황 변경 === //
	@PostMapping("ChangeRstatus.bibo")
	public ModelAndView ChangeRstatus(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		String rStatus = request.getParameter("rStatus");
		String ridx = request.getParameter("ridx");

		// 선택한 진료현황의 예약코드 가져오기
		String rcode = service.GetRcode(rStatus);

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("ridx", ridx);
		paraMap.put("rcode", rcode);

		// 진료현황 변경해주기
		int n = service.ChangeRstatus(paraMap);

		String message = "", loc = "";
		if (n == 1) {
			message = "진료현황이 " + rStatus + " 으(로) 변경되었습니다.";
			loc = request.getContextPath() + "/mypage/mdreserve.bibo";
		}
		mav.addObject("message", message);
		mav.addObject("loc", loc);
		mav.setViewName("msg");
		return mav;
	} // end of public ModelAndView ChangeRstatus(ModelAndView mav, HttpServletRequest
		// request, HttpServletResponse response) {----------

	// === (일반) 진료예약열람(페이징, 검색 처리) === //
	@ResponseBody
	@GetMapping(value = "myreserveList.bibo", produces = "text/plain;charset=UTF-8")
	public String myreserveList(HttpServletRequest request) {
		// 예얄리스트(페이징, 검색처리)
		return reserveList(request);
	} // end of public String mdreserveList(HttpServletRequest request) {-----

	// === (일반회원) 진료접수 취소 정보 === //
	@ResponseBody
	@GetMapping(value = "cancleRdto.bibo", produces = "text/plain;charset=UTF-8")
	public String cancleRdto(HttpServletRequest request) {
		String ridx = request.getParameter("ridx");

		ReserveDTO rsdto = null;
		JSONObject jsonObj = new JSONObject();
		if (ridx != null) {
			// ridx 를 통해 진료접수 취소하기
			int n = service.cancleRdto(ridx);
			if (n == 1) {
				// 예얄리스트(페이징, 검색처리)
				return reserveList(request);
			}
		}

		return jsonObj.toString();
	} // end of public String getRdto(HttpServletRequest request) {---------------

	// 예약리스트(페이징, 검색처리)
	private String reserveList(HttpServletRequest request) {

		HttpSession session = request.getSession();

		MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");

		String userid = loginuser.getUserid();

		String currentShowPageNo = request.getParameter("currentShowPageNo");

		String sclist = request.getParameter("sclist"); // 검색 구분
		String inputsc = request.getParameter("inputsc"); // 검색한 값

		if (inputsc != null) {
			inputsc = inputsc.trim();
		} else {
			inputsc = "";
		}

		int sizePerPage = 10; // 한 페이지당 보여줄 개수

		if (currentShowPageNo == null) { // 처음 접속한 경우
			currentShowPageNo = "1";
		}

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호
		int endRno = startRno + sizePerPage - 1; // 끝 행번호

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		paraMap.put("sclist", sclist);
		paraMap.put("inputsc", inputsc);

		paraMap.put("userid", userid);

		List<ReserveDTO> reserveList = null;

		// userid 의 현재 예약리스트 가져오기(검색포함)
		reserveList = service.UserReserveList(paraMap);

		int totalCnt = service.UserReserveListCnt(paraMap); // 리스트 총 결과 개수
		int totalPage = (int) Math.ceil((double) totalCnt / sizePerPage);

		JSONArray jsonArr = new JSONArray();
		List<MemberDTO> memberList = null;

		if (reserveList != null) { // 예약리스트가 존재하는 경우
			for (ReserveDTO rsdto : reserveList) {
				String hidx = rsdto.getHidx();

				// 예약된 병원의 아이디 값을 가지고 이름과 전화번호 알아오기
				memberList = service.GetHidxInfo(hidx);
				if (memberList != null) {
					for (MemberDTO mdto : memberList) {
						try {
							// select 용으로 사용되는 값에 담기
							rsdto.setName(mdto.getName());
							rsdto.setMobile(aES256.decrypt(mdto.getMobile()));
						} catch (UnsupportedEncodingException | GeneralSecurityException e) {
							e.printStackTrace();
						}
					} // end of for---------------
				}

				JSONObject jsonObj = new JSONObject();
				jsonObj.put("checkin", rsdto.getCheckin());
				jsonObj.put("name", rsdto.getName());
				jsonObj.put("mobile", rsdto.getMobile());
				jsonObj.put("reportday", rsdto.getReportday());
				jsonObj.put("rcode", rsdto.getRcode());
				jsonObj.put("ridx", rsdto.getRidx());

				jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);

				jsonArr.put(jsonObj);

			} // end of for------------------
		}
		return jsonArr.toString();
	} // end of private String reserveList(HttpServletRequest request)
		// {---------------------

	////////////////////////////////// 승혜 작업 영역
	////////////////////////////////// ///////////////////////////////////////////
	@GetMapping("memberList.bibo")
	public ModelAndView isAdmin_memberList(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String userid,
			@RequestParam(defaultValue = "") String mbr_division) {
		// requestParam 사용하여 매개변수 사용하기

		Map<String, Object> paraMap = new HashMap<>();
		if (!userid.isEmpty()) { // userid가 비어있지 않으면
			paraMap.put("userid", userid); // 파라맵으로 추가
			// 회원명
		}
		if (!mbr_division.isEmpty()) { // mbr_division이 비어있지 않으면
			paraMap.put("mbr_division", mbr_division);
			// 회원 구분
		}

		// 회원 목록 가져오기
		List<MemberDTO> memberList = service.getMemberList(paraMap);
		mav.addObject("memberList", memberList);

		mav.setViewName("mypage/memberList.info");
		return mav;
	}

	// 모달창 만들기
	@ResponseBody
	@GetMapping(value = "/getMemberDetail.bibo", produces = "text/plain;charset=UTF-8")
	public String getMemberDetail(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		JSONObject jsonObj = new JSONObject();

		if (userid == null || userid.trim().isEmpty()) {
			jsonObj.put("success", false);
			jsonObj.put("message", "유효하지 않은 사용자 ID입니다.");
			return jsonObj.toString();
		}

		MemberDTO member = service.getMemberDetail(userid);

		if (member != null) {
			jsonObj.put("success", true);
			jsonObj.put("userid", member.getUserid());
			jsonObj.put("name", member.getName());
			jsonObj.put("email", member.getEmail());
			jsonObj.put("mobile", member.getMobile());
			jsonObj.put("address", member.getAddress());
			jsonObj.put("detailAddress", member.getDetailAddress());
			jsonObj.put("mbr_division", member.getLoginmethod());
		} else {
			jsonObj.put("success", false);
			jsonObj.put("message", "회원 정보를 찾을 수 없습니다.");
		}

		return jsonObj.toString();
	}
	/*
	 * // 회원 탈퇴 처리
	 * 
	 * @GetMapping("/deleteMember.bibo") public ModelAndView
	 * deleteMember(ModelAndView mav, @RequestParam("userid") String userid) {
	 * boolean success = service.deleteMember(userid);
	 * 
	 * if (success) { mav.addObject("message", "회원이 성공적으로 탈퇴되었습니다."); } else {
	 * mav.addObject("message", "회원 탈퇴에 실패했습니다."); }
	 * 
	 * mav.setViewName("jsonView"); return mav; }
	 * 
	 * // 회원이 작성한 글 조회
	 * 
	 * @GetMapping("/getMemberPosts.bibo") public ModelAndView
	 * getMemberPosts(ModelAndView mav, @RequestParam("userid") String userid) {
	 * List<PostDTO> posts = service.getMemberPosts(userid); mav.addObject("posts",
	 * posts); mav.setViewName("mypage/memberPosts"); return mav; }
	 */

	////////////////////////////////////////////////////////////////////////////////////

}
