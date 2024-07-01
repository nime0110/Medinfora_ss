package com.spring.app.reserve.service;

import java.util.List;

import com.spring.app.main.domain.HospitalDTO;

public interface ReserveService {

	int getmbHospitalCnt();	// 회원가입된 병원 개수

	List<HospitalDTO> mbHospitalList();	// 회원가입된 병원 리스트 가져오기

}
