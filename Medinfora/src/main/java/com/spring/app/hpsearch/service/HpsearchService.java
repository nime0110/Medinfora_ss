package com.spring.app.hpsearch.service;

import java.util.List;
import java.util.Map;

import com.spring.app.main.domain.HospitalDTO;

public interface HpsearchService {

	List<HospitalDTO> getHospitalList(Map<String, String> paraMap);

	int getHpListTotalCount(Map<String, String> paraMap);

}