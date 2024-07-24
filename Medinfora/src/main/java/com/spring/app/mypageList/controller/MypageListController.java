package com.spring.app.mypageList.controller;

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

import com.spring.app.domain.MemberDTO;
import com.spring.app.mypageList.service.MypageListService;

@Controller
@RequestMapping(value="/mypage/")
public class MypageListController {

	@Autowired
	private MypageListService service;
	
	@GetMapping("memberList.bibo")
	public ModelAndView isAdmin_memberList(ModelAndView mav,HttpServletRequest request,HttpServletResponse response, 
                                   @RequestParam(defaultValue = "") String userid,   
                                   @RequestParam(defaultValue = "1") int pageNo ) 
	{
        Map<String, Object> paraMap = new HashMap<>();
       
        int pageSize = 10;// 페이지당 보여줄 회원 수
        
        paraMap.put("pageNo", pageNo);
        paraMap.put("pageSize", pageSize);  
        
        
        int start = (pageNo - 1) * pageSize + 1;
        int end = pageNo * pageSize;
        paraMap.put("start", start);
        paraMap.put("end", end);

        
        if (!userid.isEmpty()) {
            paraMap.put("userid", userid);
        }
        
        // 검색 조건 처리
        String subject = request.getParameter("subject"); // 검색 기준 ("0" 전체, "1" 회원명,"2" 병원명)
        String word = request.getParameter("word"); // 검색어
        
        if(subject != null && !subject.isEmpty()) {
            paraMap.put("subject", subject);
        }
        if(word != null && !word.isEmpty()) {
            paraMap.put("word", word);
        }
        
        
        // 페이징 처리
      /*  String pageNo = request.getParameter("pageNo");
        if(pageNo == null || pageNo.isEmpty()) {
        	System.out.println("PageNo is not set or empty");
        	pageNo = "1";
        }
        paraMap.put("pageNo", Integer.parseInt(pageNo));
        paraMap.put("pageSize", 10); // 페이지당 보여줄 회원 수 
*/
        // 서비스단에 회원 목록가져와서 mav에 추가 
        List<MemberDTO> memberList = service.getMemberList(paraMap);
        int totalCount = service.getTotalCount(paraMap);
		/* int totalPage = service.getTotalPage(paraMap); */
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        
        
        
        // 페이지바 만들기
        String pagebar = service.makePageBar(pageNo, totalPage, totalCount, subject, word);  
      
        mav.addObject("memberList", memberList);
        mav.addObject("totalPage", totalPage);
        mav.addObject("pagebar", pagebar);
        mav.addObject("subject", subject);
        mav.addObject("word", word);
        mav.addObject("currentPageNo", pageNo);
     
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