package com.spring.app.hpsearch.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.HospitalDTO;


@Mapper
public interface HpsearchDAO {

	List<HospitalDTO> getHospitalList(Map<String, String> paraMap);

	int getHpListTotalCount(Map<String, String> paraMap);
	// 상세정보 불러오기
	HospitalDTO getHpDetail(String hidx);
	
	//시를 넣어서 도 불러오기
	List<String> putSiGetdo(String local);


}
