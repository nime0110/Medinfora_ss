package com.spring.app.reserve.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.reserve.model.ReserveDAO;
import com.spring.app.reserve.service.ReserveService;

@Service
public class ReserveService_imple implements ReserveService {

	@Autowired
	private ReserveDAO dao;
	
	// === 회원가입된 병원 개수 === //
	@Override
	public int getmbHospitalCnt(Map<String, String> paraMap) {
		int totalCnt = dao.getTotalCnt(paraMap);
		return totalCnt;
	}

	// === 회원가입된 병원 리스트 가져오기 === //
	@Override
	public List<HospitalDTO> mbHospitalList(Map<String, String> paraMap) {
		List<HospitalDTO> mbHospitalList = dao.mbHospitalList(paraMap);
		return mbHospitalList;
	}

}
