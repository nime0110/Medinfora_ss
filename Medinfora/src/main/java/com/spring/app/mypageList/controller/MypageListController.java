package com.spring.app.mypageList.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.MemberDTO;
import com.spring.app.mypageList.service.MypageListService;

@Controller
@RequestMapping(value="/mypage/")
public class MypageListController {

	@Autowired
	private MypageListService service;
	
	@GetMapping("memberList.bibo")
	public ModelAndView isAdmin_memberList(ModelAndView mav,HttpServletRequest request,HttpServletResponse response) 
	{
	
	 // 검색 조건 처리
		String userid = request.getParameter("userid");
	    String subject = request.getParameter("subject"); // 검색 기준 ("0" 전체, "1" 회원명,"2" 병원명)
	    String word = request.getParameter("word"); // 검색어
	    String str_currentPageNo = request.getParameter("pageNo"); // 현재 페이지 번호
	    
	    // 페이지 관련 변수 설정
	    int sizePerPage = 10; // 한 페이지당 항목 수
	    int currentPageNo = 1; // 기본값 설정
	   
	    if (str_currentPageNo != null && !str_currentPageNo.isEmpty()) {
	        try {
	            currentPageNo = Integer.parseInt(str_currentPageNo);
	            if (currentPageNo < 1) {
	                currentPageNo = 1;
	            }
	        } catch (NumberFormatException e) {
	            currentPageNo = 1;
	        }
	    }

	    int start = ((currentPageNo - 1) * sizePerPage) + 1;
	    int end = start + sizePerPage - 1;
	    // 검색과 페이징을 위한 파라미터 맵 설정
	    Map<String, Object> paraMap = new HashMap<>();
	    paraMap.put("userid", userid);
	    paraMap.put("subject", subject);
	    paraMap.put("word", word);
	    paraMap.put("start", start);
	    paraMap.put("end", end);
	    paraMap.put("pageNo", currentPageNo);
	    paraMap.put("sizePerPage", sizePerPage);
	    // 회원 목록과 총 게시물 수를 가져오는 서비스 호출
	    List<MemberDTO> memberList = service.getMemberList(paraMap);
	    int totalCount = service.getTotalCount(paraMap); // 검색 조건을 포함한 총 레코드 수
	    int totalPage = (int) Math.ceil((double) totalCount / sizePerPage); // 총 페이지 수 계산

		/*
		 * System.out.println("totalPage : " + totalPage);
		 * System.out.println("totalCount : " + totalCount);
		 * System.out.println("sizePerPage : " + sizePerPage);
		 */  
	    // 현재 페이지 번호가 총 페이지 수를 초과하지 않도록 조정
	    currentPageNo = Math.min(currentPageNo, totalPage);
	 // totalPage가 0이 되는 것을 방지
	    totalPage = Math.max(1, totalPage);
	    
	    // 페이지 바 생성
	    String pagebar = service.makePageBar(currentPageNo, totalPage, totalCount, subject, word);

	    

	    // 모델에 데이터 추가
	    mav.addObject("memberList", memberList);
	    mav.addObject("currentPageNo", currentPageNo);
	    mav.addObject("totalPage", totalPage);
	    mav.addObject("subject", subject);
	    mav.addObject("word", word);
	    mav.addObject("userid", userid);
	    mav.addObject("pagebar", pagebar);
	    mav.addObject("totalCount", totalCount);
	    // 뷰 이름 설정
	    mav.setViewName("mypage/memberList.info");
	    return mav;
    }

    @ResponseBody
    @GetMapping(value = "getMemberDetail.bibo", produces = "application/json;charset=UTF-8")
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
                jsonObj.put("mbr_division", member.getmIdx()); 
                /*
                 * 	0	관리자
					1	일반회원
					2	의료종사자
					3	일반회원(휴면)
					4	의료종사자(휴면)
					8	정지회원
					9	탈퇴회원
                 */
                jsonObj.put("postCount", member.getPostcount()); // qna에 글 올린 갯수
                
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
    
    // 회원 정지 
    @ResponseBody
    @GetMapping("StopMember.bibo")
    public String stopMember(@RequestParam String userid) {
        JSONObject jsonObj = new JSONObject();
        boolean success = service.StopMember(userid);
        
        if (success) {
            jsonObj.put("success", true);
            jsonObj.put("message", "회원이 성공적으로 정지되었습니다.");
        } else {
            jsonObj.put("success", false);
            jsonObj.put("message", "회원 정지에 실패했습니다.");
        }
        
        return jsonObj.toString();
    }
}