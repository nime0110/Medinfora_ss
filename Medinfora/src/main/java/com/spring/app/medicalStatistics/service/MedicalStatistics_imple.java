package com.spring.app.medicalStatistics.service;

import java.util.Locale;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.app.medicalStatistics.model.MedicalStatisticsDAO;

@Service
public class MedicalStatistics_imple implements MedicalStatisticsService {

	@Autowired
	private MedicalStatisticsDAO dao;
	
	// (의료서비스율 통계) 생년월일을 가지고 만나이 파악
	@Override
	public String getAge(String userid) {
		String age = dao.getAge(userid);
		return age;
	}
	
	
	// 의료통계(암발생자 현황) excel
	@Override
	public void statisicsExcel(Map<String, Object> paraMap, Model model) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		SXSSFSheet sheet = workbook.createSheet("데이터");
		
		// 시트 열 너비 설정
		sheet.setColumnWidth(0, 7000);	// 암종류
	    sheet.setColumnWidth(1, 4000);	// 성별
	    sheet.setColumnWidth(2, 7000);	// 연령별
	    sheet.setColumnWidth(3, 5000);	// 발생자수
	    sheet.setColumnWidth(4, 7000);	// 조발생률
	    
	    int rowLocation = 0;
	    
	    CellStyle mergeRowStyle = workbook.createCellStyle();
	    mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);			// 가로기준 가운데
	    mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);	// 세로기준 가운데
	    
	    mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);		// 테두리
	    
	    
	    
	    CellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);		// 가로기준 가운데
	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);	// 세로기준 가운데
	    
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());	// 배경색
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	 // CellStyle 천단위 쉼표, 금액
        CellStyle countStyle = workbook.createCellStyle();
        countStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        countStyle.setAlignment(HorizontalAlignment.RIGHT);		// 가로기준 가운데
        countStyle.setVerticalAlignment(VerticalAlignment.CENTER);	// 세로기준 가운데
        
        Font mergeRowFont = workbook.createFont();
        mergeRowFont.setFontName("맑은 고딕");
        mergeRowFont.setFontHeight((short)500);
        mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
        mergeRowFont.setBold(true);
        
        mergeRowStyle.setFont(mergeRowFont);
        
        // CellStyle 테두리 Border
        Font headerRowFont = workbook.createFont();
        headerRowFont.setFontName("맑은 고딕");
        headerRowFont.setBold(true);
        
        headerStyle.setFont(headerRowFont);
	    
        // 병합할 행 만들기
        Row mergeRow = sheet.createRow(rowLocation);
        
        // 병합할 행에 "발생자현황" 로 셀을 만들어 셀에 스타일을 주기
        for(int i=0; i<5; i++) {
           Cell cell = mergeRow.createCell(i);
           cell.setCellStyle(mergeRowStyle);
           cell.setCellValue(String.valueOf(paraMap.get("year"))+"년 "+String.valueOf(paraMap.get("cancer"))+"암 발생자 현황");
        }// end of for-------------------------
        
        String[] arr_man = String.valueOf(paraMap.get("man")).split("\\,");
		String[] arr_man_i = String.valueOf(paraMap.get("man_i")).split("\\,");
		String[] arr_woman = String.valueOf(paraMap.get("woman")).split("\\,");
		String[] arr_woman_i = String.valueOf(paraMap.get("woman_i")).split("\\,");
		String[] arr_age = String.valueOf(paraMap.get("age")).split("\\,");
        
        
        // 셀 병합하기
        sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation+1, 0, 4)); // 시작 행, 끝 행, 시작 열, 끝 열
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 4));
        
        sheet.addMergedRegion(new CellRangeAddress(5, arr_age.length*2+5,  0, 0));
        sheet.addMergedRegion(new CellRangeAddress(5, arr_age.length+4,  1, 1));
        sheet.addMergedRegion(new CellRangeAddress(arr_age.length+5, arr_age.length*2+4,  1, 1));
        
        
        // 헤더 행 생성
        Row headerRow = sheet.createRow(3);
        
        Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0 부터 시작한다.
        headerCell.setCellValue("암종류");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("성별");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("연령별");
        headerCell.setCellStyle(headerStyle);

        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue(paraMap.get("year")+"년");
        headerCell.setCellStyle(headerStyle);
        
        
        Row headerRow_sec = sheet.createRow(4);
        headerCell = headerRow_sec.createCell(3);
        headerCell.setCellValue("발생자수(명)");
        headerCell.setCellStyle(headerStyle);
        
        headerCell = headerRow_sec.createCell(4);
        headerCell.setCellValue("조발생률(명/10만명)");
        headerCell.setCellStyle(headerStyle);
        
        Row bodyRow = null;
        Cell bodyCell = null;
        
        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);		// 가로기준 가운데
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);	// 세로기준 가운데
        
        Font contentRowFont = workbook.createFont();
        contentRowFont.setFontName("맑은 고딕");
        
        contentStyle.setFont(contentRowFont);
        
        
        
        for(int i=0; i<arr_age.length; i++) {
        	// 행생성
            bodyRow = sheet.createRow(i+5);
            
            // 암종류
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(String.valueOf(paraMap.get("cancer")));
            bodyCell.setCellStyle(contentStyle);
            
            // 성별
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue("남자");
            bodyCell.setCellStyle(contentStyle);
                       
            // 연령대
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(arr_age[i]);
            bodyCell.setCellStyle(contentStyle);
            
            // 발생자수
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(Float.parseFloat(arr_man[i]));
            bodyCell.setCellStyle(countStyle);
            
            // 조발생률
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(String.format("%.1f", Double.parseDouble(arr_man_i[i]))); 
            bodyCell.setCellStyle(contentStyle);
            bodyCell.setCellStyle(countStyle);

        }	// end of for------------
        
        for(int i=0; i<arr_age.length; i++) {
        	// 행생성
            bodyRow = sheet.createRow(i+5+arr_age.length);
            
            // 암종류
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(String.valueOf(paraMap.get("cancer")));
            bodyCell.setCellStyle(contentStyle);
            
            // 성별
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue("여자");
            bodyCell.setCellStyle(contentStyle);
                       
            // 연령대
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(arr_age[i]);
            bodyCell.setCellStyle(contentStyle);
            
            // 발생자수
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(Float.parseFloat(arr_woman[i]));
            bodyCell.setCellStyle(countStyle);
            
            // 조발생률
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(String.format("%.1f", Double.parseDouble(arr_woman_i[i])) );
            bodyCell.setCellStyle(countStyle);

        }	// end of for------------
        
        model.addAttribute("locale", Locale.KOREA);		// import java.util.Locale
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", String.valueOf(paraMap.get("year"))+"년 "+String.valueOf(paraMap.get("cancer"))+"암 발생자 현황");
		
		
	}

}
