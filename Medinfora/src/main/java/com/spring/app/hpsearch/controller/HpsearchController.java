package com.spring.app.hpsearch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.hpsearch.service.HpsearchService;
import com.spring.app.main.domain.*;

@Controller
public class HpsearchController {
	
	//의존객체주입
	@Autowired
	private HpsearchService service;
	
	
	@GetMapping("/hpsearch/hospitalSearch.bibo")
	public ModelAndView medistatistic(ModelAndView mav) {
		mav.setViewName("hospitalSearch/hospitalSearch.tiles");
		return mav;
	}
	
	@ResponseBody
	@GetMapping(value="/hpsearch/hpsearchAdd.bibo", produces="text/plain;charset=UTF-8")
	public String hpsearchAdd(HttpServletRequest request) {
		
		String addr = request.getParameter("addr"); //경기도 광명시
		String classcode = request.getParameter("classcode"); //D004
		String agency = request.getParameter("agency"); //의원
		String hpname = request.getParameter("hpname"); //병원이름
		
		
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		int sizePerPage = 15;//한 페이지당 15개의 병원  
		
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; // 시작 행번호 
        int endRno = startRno + sizePerPage - 1; // 끝 행번호
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("addr", addr);
		paraMap.put("classcode", classcode);
		paraMap.put("agency", agency);
		paraMap.put("hpname", hpname);
        paraMap.put("startRno", String.valueOf(startRno));
        paraMap.put("endRno", String.valueOf(endRno));
				 
		System.out.println("~~~~~hpname:" + hpname);
		System.out.println("~~~~~addr:" + addr);
		System.out.println("~~classcode:" + classcode);
		System.out.println("~~agency:" + agency);
		
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		List<HospitalDTO> hospitalList = service.getHospitalList(paraMap);
		int totalCount = service.getHpListTotalCount(paraMap); //전체개수 
		

		JSONArray jsonArr = new JSONArray(); //[]
		
		if(hospitalList != null) {
			for(HospitalDTO hpdto : hospitalList) {
				
				JSONObject jsonObj = new JSONObject(); //{}
								
				jsonObj.put("hidx", hpdto.getHidx()); 
				jsonObj.put("hptel", hpdto.getHptel());				
				jsonObj.put("hpname", hpdto.getHpname()); 
				jsonObj.put("hpaddr", hpdto.getHpaddr());
				jsonObj.put("classname", hpdto.getClassname());
				jsonObj.put("wgs84lon", hpdto.getWgs84lon());
				jsonObj.put("wgs84lat", hpdto.getWgs84lat());
				jsonObj.put("totalCount", totalCount);
				jsonObj.put("currentShowPageNo", currentShowPageNo);
				
				jsonArr.put(jsonObj);
			}//end of for---------------------------
		}
		
		return jsonArr.toString();

	}
	
	
}