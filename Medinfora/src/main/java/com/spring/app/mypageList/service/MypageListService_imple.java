package com.spring.app.mypageList.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/*import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
*/
import com.spring.app.common.AES256;
import com.spring.app.common.Sha256;
import com.spring.app.domain.MemberDTO;
import com.spring.app.mypageList.model.MypageListDAO;

@Service
public class MypageListService_imple implements MypageListService {
	//////////////////////////////////////////////////
	@Autowired
	private AES256 aES256;
	
	@Autowired
	private MypageListDAO mdao;
	// 회원 목록 가져오기
    @Override
    public List<MemberDTO> getMemberList(Map<String, Object> paraMap) {
		/*
		 * // 페이지 번호와 페이지 크기 가져오기 int pageNo = (int)paraMap.get("pageNo"); int pageSize
		 * = (int) paraMap.get("pageSize");
		 * 
		 * // 검색 조건 처리 String searchType = (String) paraMap.get("searchType"); String
		 * searchKeyword = (String) paraMap.get("searchKeyword");
		 */	
    	return mdao.getMemberList(paraMap);
    }
    
    // 회원 정보 상세 정보 가져오기
    @Override
    public MemberDTO getMemberDetail(String userid) {
        if (userid == null || userid.trim().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다.");
        }
        
        MemberDTO member = mdao.getMemberDetails(userid);
        
        if (member != null) {
            try {
                member.setMobile(aES256.decrypt(member.getMobile()));
                member.setEmail(aES256.decrypt(member.getEmail()));
            } catch (Exception e) {
                // 로깅 추가
                throw new RuntimeException("회원 정보 복호화 중 오류가 발생했습니다.", e);
            }
        }
        
        return member;
    }
    
   // 회원 정지 
	@Override
	public boolean StopMember(String userid) {
	
		 try {
	            int result = mdao.updateMemberStatusToStopped(userid);
	            return result > 0;
	        } catch (Exception e) {
	            // 로그 추가
	            e.printStackTrace();
	            return false;
	        }
	    }

	// 페이지 보여줄 레코드 수 
	@Override
	public int getTotalPage(Map<String, Object> paraMap) {
		int totalCount = getTotalCount(paraMap);
	    int pageSize = 10; // 페이지당 보여줄 레코드 수
	    return (int) Math.ceil((double) totalCount / pageSize);
		
	}
// 페이지바 
	@Override
	public String makePageBar(int currentShowPageNo, int totalPage, int totalCount, String subject, String word) {
		    
		   if (totalCount == 0 || totalPage <= 1) {
		        return "1"; // 결과가 없거나 1페이지뿐일 때는 페이지 바를 생성하지 않음
		    }
		
			int pageNo = 1;
		    int blockSize = 10;
		    int loop = 1;
		    
		    String pageBar = "<ul class='pagination hj_pagebar nanum-n size-s'>";
		    
		    if(pageNo != 1) {
		        pageBar += "<li class='page-item'>" 
		                 + "    <a class='page-link' href='javascript:Page("+(pageNo-1)+")'>" 
		                 + "          <span aria-hidden='true'>&laquo;</span>" 
		                 + "       </a>" 
		                 + "</li>";
		    }
		    
		    while(!(loop>blockSize || pageNo > totalPage)) {
		        if(pageNo == currentShowPageNo) {
		            pageBar += "<li class='page-item'>"
		                    + "      <a class='page-link nowPage'>"+pageNo+"</a>" 
		                    + "</li>";
		        }
		        else{
		            pageBar += "<li class='page-item'>"
		                    + "      <a class='page-link' href='javascript:Page("+pageNo+")'>" +pageNo+"</a>" 
		                    + "</li>";
		        }
		        loop++;
		        pageNo++;
		    }
		    
		    if(pageNo <= totalPage) {
		        pageBar += "<li class='page-item'>"
		                 + "      <a class='page-link' href='javascript:Page("+pageNo+")'>"
		                 + "          <span aria-hidden='true'>&raquo;</span>"
		                 + "       </a>"
		                 + "</li>";
		    }
		    
		    pageBar += "</ul>";
		    
		    return pageBar;
		}

	@Override
	public int getTotalCount(Map<String, Object> paraMap) {
	   /* int totalCount = 0;
	    try {
	        totalCount = mdao.getTotalPage(paraMap);
	       
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalCount;	
	    }*/

		return mdao.getTotalCount(paraMap);

	}

	 // 엑셀 파일 다운로드 
    @Override
    public void userid_to_Excel(Map<String, Object> paraMap, Model model) {
    	// 엑셀 워크북 생성
    			SXSSFWorkbook workbook = new SXSSFWorkbook();
    			// 임시파일을 만드는 것임 
    			/*
    			 * SXSSFWorkbook
					XSSFWorkbook
					HSSFWorkbook 
    			 */
    			// 시트 생성
    			SXSSFSheet sheet = workbook.createSheet("회원 목록");
    			
    			// 열 너비 설정
    	        sheet.setColumnWidth(0, 4000);
    	        sheet.setColumnWidth(1, 4000);
    	        sheet.setColumnWidth(2, 4000);
    	        sheet.setColumnWidth(3, 4000);
    	        sheet.setColumnWidth(4, 4000);
    	      
    			
    			// 행의 위치를 나타내는 변수
    			int rowLocation = 0;
    			
    	        // 헤더 스타일 설정
    	        CellStyle headerStyle = workbook.createCellStyle();
    	        headerStyle.setAlignment(HorizontalAlignment.CENTER);
    	        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    	        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
    	        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    	        // 헤더 행 생성
    	        Row headerRow = sheet.createRow(rowLocation++);
    	        String[] headers = {"아이디", "회원 유형", "성명", "성별", "가입일자"};
    	        
    	        for (int i = 0; i < headers.length; i++) {
    	            Cell headerCell = headerRow.createCell(i);
    	            headerCell.setCellValue(headers[i]);
    	            headerCell.setCellStyle(headerStyle);
    	        }

    	        // 회원 목록 가져오기
    	        List<MemberDTO> getMemberList = getMemberList(paraMap);
    	     
    	        // 회원 목록을 엑셀 파일에 추가
    	        for (int i = 0; i < getMemberList.size(); i++) {
    	            MemberDTO member = getMemberList.get(i);
    	            Row bodyRow = sheet.createRow(rowLocation++);
    	            
    	            Cell bodyCell = bodyRow.createCell(0);
    	            bodyCell.setCellValue(member.getUserid());
    	            
    	            bodyCell = bodyRow.createCell(1);
    	            bodyCell.setCellValue(getMemberType(member.getmIdx()));
    	            
    	            bodyCell = bodyRow.createCell(2);
    	            bodyCell.setCellValue(member.getName());
    	            
    	            bodyCell = bodyRow.createCell(3);
    	            bodyCell.setCellValue(member.getGender() == 1 ? "남" : "여");
    	            
    	            bodyCell = bodyRow.createCell(4);
    	            bodyCell.setCellValue(member.getRegisterday());
    	            
    	         
    	        }

    	        // 모델에 엑셀 워크북 추가
    	        model.addAttribute("locale", Locale.KOREA);
    	        model.addAttribute("workbook", workbook);
    	        model.addAttribute("workbookName", "회원 목록");
    	    }
    	    
    	    // 회원 유형을 문자열로 변환
    	    private String getMemberType(int mIdx) {
    	        switch (mIdx) {
    	            case 0: return "관리자";
    	            case 1: return "일반회원";
    	            case 2: return "의료종사자";
    	            case 3: return "일반회원(휴면)";
    	            case 4: return "의료종사자(휴면)";
    	            case 8: return "정지회원";
    	            case 9: return "탈퇴회원";
    	            default: return "일반회원";
    	        }
    	        
   } // end of 	public void userid_to_Excel(Map<String, Object> paraMap, Model model)

} // end of public class MypageListService_imple implements MypageListService
