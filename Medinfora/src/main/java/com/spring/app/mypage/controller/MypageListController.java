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

@Controller
@RequestMapping(value = "/mypage/")
public class MypageListController {

	@Autowired
	private MypageListService service;

	@Autowired
	private AES256 aES256;

	
	
	@GetMapping("memberList.bibo")
    public ModelAndView memberList(ModelAndView mav, 
                                   @RequestParam(defaultValue = "") String userid, 
                                   @RequestParam(defaultValue = "") String mbr_division) {
        Map<String, Object> paraMap = new HashMap<>();
        if (!userid.isEmpty()) {
            paraMap.put("userid", userid);
        }
        if (!mbr_division.isEmpty()) {
            paraMap.put("mbr_division", mbr_division);
        }

        List<MemberDTO> memberList = service.getMemberList(paraMap);
        mav.addObject("memberList", memberList);
        mav.setViewName("mypage/memberList.info");
        return mav;
    }

    @ResponseBody
    @GetMapping(value = "/getMemberDetail.bibo", produces = "application/json;charset=UTF-8")
    public String getMemberDetail(@RequestParam String userid) {
        JSONObject jsonObj = new JSONObject();

        if (userid == null || userid.trim().isEmpty()) {
            jsonObj.put("success", false);
            jsonObj.put("message", "유효하지 않은 사용자 ID입니다.");
            return jsonObj.toString();
        }

        try {
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
        } catch (Exception e) {
            jsonObj.put("success", false);
            jsonObj.put("message", "회원 정보 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        
        return jsonObj.toString();
    }

    @ResponseBody
    @GetMapping("/deleteMember.bibo")
    public String deleteMember(@RequestParam String userid) {
        JSONObject jsonObj = new JSONObject();
        boolean success = service.deleteMember(userid);
        
        if (success) {
            jsonObj.put("success", true);
            jsonObj.put("message", "회원이 성공적으로 탈퇴되었습니다.");
        } else {
            jsonObj.put("success", false);
            jsonObj.put("message", "회원 탈퇴에 실패했습니다.");
        }
        
        return jsonObj.toString();
    }
}
	/*
	 * // 회원이 작성한 글 조회
	 * 
	 * @GetMapping("/getMemberPosts.bibo") public ModelAndView
	 * getMemberPosts(ModelAndView mav, @RequestParam("userid") String userid) {
	 * List<PostDTO> posts = service.getMemberPosts(userid); mav.addObject("posts",
	 * posts); mav.setViewName("mypage/memberPosts"); return mav; }
	 */

	////////////////////////////////////////////////////////////////////////////////////

