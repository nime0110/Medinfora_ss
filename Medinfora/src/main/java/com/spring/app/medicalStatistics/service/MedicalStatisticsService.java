package com.spring.app.medicalStatistics.service;

public interface MedicalStatisticsService {

	// (의료서비스율 통계) 생년월일을 가지고 만나이  파악
	String getAge(String birthday);

}
