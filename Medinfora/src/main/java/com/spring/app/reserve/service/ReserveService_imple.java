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

	// 병원의 오픈시간과 마감시간 파악
	@Override
	public HospitalDTO hospitalTime(String hidx) {
		HospitalDTO hospitalTime = dao.hospitalTime(hidx);
		return hospitalTime;
	}

	// 선택한 날의 예약 개수 파악
	@Override
	public int reserveCnt(Map<String, String> paraMap) {
		int reserveCnt = dao.reserveCnt(paraMap);
		return reserveCnt;
	}
	
	// 현재시간 이후, 선택한 날짜와 예약일이 같은 경우
	@Override
	public HospitalDTO dayReserveImpossible(Map<String, String> paraMap) {
		HospitalDTO impossibleTimeCheck  = dao.dayReserveImpossible(paraMap);
		return impossibleTimeCheck;
	}
	
}
