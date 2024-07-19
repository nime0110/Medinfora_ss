package com.spring.app.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.mypage.service.MypageListService;
import com.spring.app.mypage.service.MypageService;
@Controller
@RequestMapping(value = "/mypage/")
public class MypageListController {

	@Autowired
	private MypageListService service;

	@Autowired
	private AES256 aES256;

	@GetMapping("myinfo.bibo")
	public ModelAndView isLogin_myinfo(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {

		mav.setViewName("mypage/myinfo.info");

		return mav;
	}
	
	////////////////////////////////// 승혜 작업 영역
	////////////////////////////////// ///////////////////////////////////////////
	@GetMapping("memberList.bibo")
	public ModelAndView isAdmin_memberList(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String userid, @RequestParam(defaultValue = "") String mbr_division) {
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
	
	  // 회원 탈퇴 처리
	  
	  @GetMapping("/deleteMember.bibo") public ModelAndView
	  deleteMember(ModelAndView mav, @RequestParam("userid") String userid) {
	  boolean success = service.deleteMember(userid);
	  
	  if (success) { mav.addObject("message", "회원이 성공적으로 탈퇴되었습니다."); } else {
	  mav.addObject("message", "회원 탈퇴에 실패했습니다."); }
	  
	  mav.setViewName("jsonView"); return mav; }
	  
	/*
	 * // 회원이 작성한 글 조회
	 * 
	 * @GetMapping("/getMemberPosts.bibo") public ModelAndView
	 * getMemberPosts(ModelAndView mav, @RequestParam("userid") String userid) {
	 * List<PostDTO> posts = service.getMemberPosts(userid); mav.addObject("posts",
	 * posts); mav.setViewName("mypage/memberPosts"); return mav; }
	 */

	////////////////////////////////////////////////////////////////////////////////////

}
