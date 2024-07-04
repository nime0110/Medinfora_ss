package com.spring.app.hpsearch.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.HospitalDTO;


@Mapper
public interface HpsearchDAO {

	List<HospitalDTO> getHospitalList(Map<String, String> paraMap);

	int getHpListTotalCount(Map<String, String> paraMap);

}
