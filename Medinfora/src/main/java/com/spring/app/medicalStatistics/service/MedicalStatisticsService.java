package com.spring.app.medicalStatistics.service;

import java.util.Map;

import org.springframework.ui.Model;

public interface MedicalStatisticsService {

	// (의료서비스율 통계) 생년월일을 가지고 만나이  파악
	String getAge(String birthday);
	
	// 의료통계 자료 excel
	void statisicsExcel(Map<String, Object> paraMap, Model model);

}
