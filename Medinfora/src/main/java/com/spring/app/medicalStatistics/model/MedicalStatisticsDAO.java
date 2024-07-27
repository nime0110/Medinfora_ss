package com.spring.app.medicalStatistics.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicalStatisticsDAO {

	// (의료서비스율 통계) 생년월일을 가지고 만나이 파악
	String getAge(String userid);

}
