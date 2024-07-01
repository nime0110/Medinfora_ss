package com.spring.app.reserve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.reserve.model.ReserveDAO;
import com.spring.app.reserve.service.ReserveService;

@Service
public class ReserveService_imple implements ReserveService {

	@Autowired
	private ReserveDAO dao;
	
	// === 회원가입된 병원 개수 === //
	@Override
	public int getmbHospitalCnt() {
		int totalCnt = dao.getTotalCnt();
		return totalCnt;
	}

	// === 회원가입된 병원 리스트 가져오기 === //
	@Override
	public List<HospitalDTO> mbHospitalList() {
		List<HospitalDTO> mbHospitalList = dao.mbHospitalList();
		return mbHospitalList;
	}

}
