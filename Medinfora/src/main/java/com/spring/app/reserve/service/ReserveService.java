package com.spring.app.reserve.service;

import java.util.List;
import java.util.Map;

import com.spring.app.main.domain.HospitalDTO;

public interface ReserveService {

	int getmbHospitalCnt(Map<String, String> paraMap);	// 회원가입된 병원 개수

	List<HospitalDTO> mbHospitalList(Map<String, String> paraMap);	// 회원가입된 병원 리스트 가져오기

}
