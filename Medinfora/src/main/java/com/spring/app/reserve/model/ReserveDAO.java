package com.spring.app.reserve.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.HospitalDTO;

@Mapper
public interface ReserveDAO {

	int getTotalCnt(Map<String, String> paraMap);	// 회원가입된 병원 개수

	List<HospitalDTO> mbHospitalList(Map<String, String> paraMap);	// 회원가입된 병원 리스트 가져오기

}
