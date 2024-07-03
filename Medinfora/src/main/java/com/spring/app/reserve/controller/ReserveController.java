package com.spring.app.reserve.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.reserve.service.ReserveService;

@Controller
public class ReserveController {

	@Autowired
	private ReserveService service;
	
	@GetMapping("/reserve/choiceDr.bibo")
	public ModelAndView isLogin_choiceDr(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) {
		List<HospitalDTO> mbHospitalList = null;
		
		String city = request.getParameter("city");
		String local = request.getParameter("loc");
		String classcode = request.getParameter("dept");
		String hpname = request.getParameter("hpname");
		
		if(city == null || "시/도 선택".equals(city)) {
			city = "";
		}
		if(local == null || "시/군구 선택".equals(local)) {
			local = "";
		}
		if(classcode == null || "진료과목 선택".equals(classcode)) {
			classcode = "";
		}
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("city", city);
		paraMap.put("local", local);
		paraMap.put("classcode", classcode);
		paraMap.put("hpname", hpname);
		mav.addObject("paraMap",paraMap);
		
		mbHospitalList = service.mbHospitalList(paraMap);	// 회원가입된 병원 리스트 가져오기
		
		mav.addObject("mbHospitalList",mbHospitalList);
		
		mav.setViewName("reserve/choiceDr.tiles");
		
		return mav;
	}
	
	@GetMapping("/reserve/choiceDay.bibo")	// 나중에 POST 로 변경할 예정
	public ModelAndView choiceDay(ModelAndView mav) {
		
		mav.setViewName("reserve/choiceDay.tiles");
		
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="/reserve/choiceDrList.bibo", produces="text/plain;charset=UTF-8")
	public String choiceDrList(HttpServletRequest request) {
		
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		String city = request.getParameter("city");
		String local = request.getParameter("local");
		String classcode = request.getParameter("classcode");
		String hpname = request.getParameter("hpname");
		
		if(city == null || "시/도 선택".equals(city)) {
			city = "";
		}
		if(local == null || "시/군구 선택".equals(local)) {
			local = "";
		}
		if(classcode == null || "진료과목 선택".equals(classcode)) {
			classcode = "";
		}
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		int sizePerPage = 9;	// 한 페이지당 보여줄 개수
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호 
		int endRno = startRno + sizePerPage - 1; // 끝 행번호
		
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		
		paraMap.put("city", city);
		paraMap.put("local", local);
		paraMap.put("classcode", classcode);
		paraMap.put("hpname", hpname);

        List<HospitalDTO> mbHospitalList = service.mbHospitalList(paraMap);
        
		int totalCnt = service.getmbHospitalCnt(paraMap);	// 회원가입된 병원 개수
		
		int totalPage = (int)Math.ceil((double)totalCnt/sizePerPage);
		
		JSONArray jsonArr = new JSONArray();
		
		if(mbHospitalList != null) {
			for(HospitalDTO hpdto : mbHospitalList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("classcode",hpdto.getClasscode());
				jsonObj.put("hidx",hpdto.getHidx());
				jsonObj.put("hpname",hpdto.getHpname());
				jsonObj.put("hpaddr",hpdto.getHpaddr());
				jsonObj.put("hptel",hpdto.getHptel());

				jsonObj.put("totalCnt", totalCnt);
				jsonObj.put("sizePerPage", sizePerPage);
				jsonObj.put("totalPage", totalPage);
				jsonObj.put("paraMap", paraMap);
				
				jsonArr.put(jsonObj);
			}	// end of for---------
		}
		
		return jsonArr.toString();
		
	}
	
}
