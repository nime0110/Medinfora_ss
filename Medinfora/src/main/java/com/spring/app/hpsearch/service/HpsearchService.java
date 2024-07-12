package com.spring.app.hpsearch.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.HospitalDTO;

public interface HpsearchService {

	List<HospitalDTO> getHospitalList(Map<String, String> paraMap);

	int getHpListTotalCount(Map<String, String> paraMap);
	// 상세정보 불러오기
	HospitalDTO getHpDetail(String hidx);

	//시를 넣어서 도 불러오기
	List<String> putSiGetdo(String local);

}
