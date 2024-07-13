package com.spring.app.hpsearch.controller;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.*;
import com.spring.app.hpsearch.service.HpsearchService;

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
	
	//검색 메소드 
	@ResponseBody
	@GetMapping(value="/hpsearch/hpsearchAdd.bibo", produces="text/plain;charset=UTF-8")
	public String hpsearchAdd(HttpServletRequest request) {
		
		String addr = request.getParameter("addr"); //경기도 광명시
		String country = request.getParameter("country"); //동
		String classcode = request.getParameter("classcode"); //D004
		String agency = request.getParameter("agency"); //의원
		String hpname = request.getParameter("hpname"); //병원이름
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		int sizePerPage = 10;//한 페이지당 10개의 병원  
		
		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1; 
        int endRno = startRno + sizePerPage - 1; 
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("addr", addr);
		paraMap.put("country", country);
		paraMap.put("classcode", classcode);
		paraMap.put("agency", agency);
		paraMap.put("hpname", hpname);
        paraMap.put("startRno", String.valueOf(startRno));
        paraMap.put("endRno", String.valueOf(endRno));
				 
		
		if(hpname == null) {
			hpname = "";
		}
		if(hpname != null) {
			hpname = hpname.trim();
		}
		
		List<HospitalDTO> hospitalList = service.getHospitalList(paraMap);
		int totalCount = service.getHpListTotalCount(paraMap); //전체개수 
		
	
        LocalDate currentDate = LocalDate.now();
           
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();                
        String str_dayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);  
        
        LocalTime currentTime = LocalTime.now();         

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");         
   
        int int_currentTime = Integer.parseInt(currentTime.format(formatter));   
    
        int n = service.holidatCheck(currentDate);
        
        System.out.println("n : " + n);
        
        if(n > 0) {
        	str_dayOfWeek = "공휴일";
        }
        System.out.println("str_dayOfWeek2 : " + str_dayOfWeek);
        

		JSONArray jsonArr = new JSONArray(); 
		
        String endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8,
        	   starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8;
		
		String status = "";
		
		if(hospitalList != null) {
			for(HospitalDTO hpdto : hospitalList) {
		        endtime1 = hpdto.getEndtime1();
		        endtime2 = hpdto.getEndtime2();
		        endtime3 = hpdto.getEndtime3();
		        endtime4 = hpdto.getEndtime4();
		        endtime5 = hpdto.getEndtime5();
		        endtime6 = hpdto.getEndtime6();
		        endtime7 = hpdto.getEndtime7();
		        endtime8 = hpdto.getEndtime8();

		        starttime1 = hpdto.getStarttime1();
		        starttime2 = hpdto.getStarttime2();
		        starttime3 = hpdto.getStarttime3();
		        starttime4 = hpdto.getStarttime4();
		        starttime5 = hpdto.getStarttime5();
		        starttime6 = hpdto.getStarttime6();
		        starttime7 = hpdto.getStarttime7();
		        starttime8 = hpdto.getStarttime8();
		        
		        if ("공휴일".equals(str_dayOfWeek) && starttime8.equals(" ") && endtime8.equals(" ")) { 
		        		status = "휴진";
		        } else {
		            try {
		                if ("월요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime1) && !" ".equals(endtime1) && 
		                        Integer.parseInt(starttime1) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime1)) {
		                        status = "진료중";    
		                    } else if(starttime1.equals(" ") && endtime1.equals(" ")) {
		    		        	status = "휴진";		        	
		                    } else {
		                        status = "진료종료";
		                    }               
		                }
		                
		                if ("화요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime2) && !" ".equals(endtime2) && 
		                        Integer.parseInt(starttime2) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime2)) {
		                        status = "진료중";    
		                    } else if(starttime2.equals(" ") && endtime2.equals(" ")) {
		    		        	status = "휴진";		        	
		                    } else {
		                        status = "진료종료";
		                    }               
		                }
		                
		                if ("수요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime3) && !" ".equals(endtime3) && 
		                        Integer.parseInt(starttime3) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime3)) {
		                        status = "진료중";    
		                    } else if(starttime3.equals(" ") && endtime3.equals(" ")) {
		    		        	status = "휴진";		        	
		                    } else {
		                        status = "진료종료";
		                    }               
		                }
		                
		                if ("목요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime4) && !" ".equals(endtime4) && 
		                        Integer.parseInt(starttime4) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime4)) {
		                        status = "진료중";    
		                    }  else if(starttime4.equals(" ") && endtime4.equals(" ")) {
		    		        	status = "휴진";		        	
		                    }  else {
		                        status = "진료종료";
		                    }               
		                }
		                
		                if ("금요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime5) && !" ".equals(endtime5) && 
		                        Integer.parseInt(starttime5) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime5)) {
		                        status = "진료중";    
		                    } else if(starttime5.equals(" ") && endtime5.equals(" ")) {
		    		        	status = "휴진";		        	
		                    } else {
		                        status = "진료종료";
		                    }               
		                }
		                if ("토요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime6) && !" ".equals(endtime6) && 
		                        Integer.parseInt(starttime6) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime6)) {
		                        status = "진료중";    
		                    } else if(starttime6.equals(" ") && endtime6.equals(" ")) {
		    		        	status = "휴진";		        	
		                    } else {
		                        status = "진료종료";
		                    }               
		                }
		                if ("일요일".equals(str_dayOfWeek)) {
		                    if (!" ".equals(starttime7) && !" ".equals(endtime7) && 
		                        Integer.parseInt(starttime7) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime7)) {
		                        status = "진료중";    
		                    } else if(starttime7.equals(" ") && endtime7.equals(" ")) {
		    		        	status = "휴진";		        	
		                    }  else {
		                        status = "진료종료";
		                    }               
		                }
		                if ("공휴일".equals(str_dayOfWeek) && 
		                    (!" ".equals(starttime8)) && (!" ".equals(endtime8))) {                     
		                    if (Integer.parseInt(starttime8) < int_currentTime && 
		                        int_currentTime < Integer.parseInt(endtime8)) {
		                        status = "진료중";    
		                    } else {
		                        status = "진료종료";
		                    }               
		                }       
		            } catch(NumberFormatException e) {
		                e.printStackTrace();
		            }
		        }

				JSONObject jsonObj = new JSONObject(); //{}
								
				jsonObj.put("hidx", hpdto.getHidx()); 
				jsonObj.put("hptel", hpdto.getHptel());				
				jsonObj.put("hpname", hpdto.getHpname()); 
				jsonObj.put("hpaddr", hpdto.getHpaddr());
				jsonObj.put("classname", hpdto.getClassname());				
				jsonObj.put("wgs84lon", hpdto.getWgs84lon());
				jsonObj.put("wgs84lat", hpdto.getWgs84lat());
				jsonObj.put("status", status);
				jsonObj.put("totalCount", totalCount);
				jsonObj.put("currentShowPageNo", currentShowPageNo);
				jsonObj.put("sizePerPage", sizePerPage);
				
				jsonArr.put(jsonObj);
			}//end of for---------------------------
		}
		
		return jsonArr.toString();

	}
	
	//상세검색 메소드
	@ResponseBody
	@GetMapping(value="/hpsearch/hpsearchDetail.bibo", produces="text/plain;charset=UTF-8")
	public String hpsearchDetail(HttpServletRequest request) {
		
		String hidx = request.getParameter("hidx");
		//System.out.println("~~~ hidx:" + hidx );
		HospitalDTO hpdetail = null;
		if(hidx != null) {
			hpdetail = service.getHpDetail(hidx);			
		}

		
		JSONObject jsonObj = new JSONObject(); //{}
		if(hpdetail != null) {
			jsonObj.put("hidx", hpdetail.getHidx()); 
			jsonObj.put("hpname", hpdetail.getHpname()); 
			jsonObj.put("hpaddr", hpdetail.getHpaddr());
			jsonObj.put("hptel", hpdetail.getHptel());				
			jsonObj.put("classname", hpdetail.getClassname());
			jsonObj.put("agency", hpdetail.getAgency());
			jsonObj.put("starttime1", hpdetail.getStarttime1());
			jsonObj.put("starttime2", hpdetail.getStarttime2());
			jsonObj.put("starttime3", hpdetail.getStarttime3());
			jsonObj.put("starttime4", hpdetail.getStarttime4());
			jsonObj.put("starttime5", hpdetail.getStarttime5());
			jsonObj.put("starttime6", hpdetail.getStarttime6());
			jsonObj.put("starttime7", hpdetail.getStarttime7());
			jsonObj.put("starttime8", hpdetail.getStarttime8());
			jsonObj.put("endtime1", hpdetail.getEndtime1());
			jsonObj.put("endtime2", hpdetail.getEndtime2());
			jsonObj.put("endtime3", hpdetail.getEndtime3());
			jsonObj.put("endtime4", hpdetail.getEndtime4());
			jsonObj.put("endtime5", hpdetail.getEndtime5());
			jsonObj.put("endtime6", hpdetail.getEndtime6());
			jsonObj.put("endtime7", hpdetail.getEndtime7());
			jsonObj.put("endtime8", hpdetail.getEndtime8());
		}
		System.out.println(jsonObj.toString());
		return jsonObj.toString();
	}
	
	//도를 넣으면 시를 반환하는 메소드
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="putSiGetdo.bibo", produces="text/plain;charset=UTF-8")
	public String putSiGetdo(HttpServletRequest request) {


	    String local = request.getParameter("local");

	    List<String> sidoList = service.putSiGetdo(local);

	    if (sidoList == null) {
	        sidoList = new ArrayList<>(); // 만약 null이면 빈 리스트로 초기화
	    }
	    JSONArray jsonArr = new JSONArray();

	    for (String sido : sidoList) {
	    	JSONObject jsonObj = new JSONObject(); //{}
	        jsonObj.put("sido", sido);
	        jsonArr.put(jsonObj);
	    }

	    return jsonArr.toString();
		
	}// end of public String getlocalinfo(HttpServletRequest request)
	
	
}
