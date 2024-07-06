package com.spring.app.reserve.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.reserve.model.ReserveDAO;

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

	// 날짜가 공휴일인지 체크
	@Override
	public int holidayCheck(String day) {
		int check = dao.holidayCheck(day);
		return check;
	}

	// 병원과 요일 파악하여, 오늘(현재시간 이후) 진료예약 가능한 업무시간 파악하기
	@Override
	public List<HospitalDTO> todayReserveAvailable(Map<String, String> paraMap) {
		List<HospitalDTO> availableTimeList = dao.todayReserveAvailable(paraMap);
		return availableTimeList;
	}

}
