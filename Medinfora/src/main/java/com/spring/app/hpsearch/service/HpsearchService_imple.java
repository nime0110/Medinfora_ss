package com.spring.app.hpsearch.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.hpsearch.model.HpsearchDAO;
import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.model.MainDAO;

@Service
public class HpsearchService_imple implements HpsearchService {
	
	//의존객체 주입 DI ----- 
	@Autowired
	private HpsearchDAO hsdao;

	@Override
	public List<HospitalDTO> getHospitalList(Map<String, String> paraMap) {
		return hsdao.getHospitalList(paraMap);
	}

	@Override
	public int getHpListTotalCount(Map<String, String> paraMap) {
		return hsdao.getHpListTotalCount(paraMap);
	}

}