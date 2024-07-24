package com.spring.app.medicalStatistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
