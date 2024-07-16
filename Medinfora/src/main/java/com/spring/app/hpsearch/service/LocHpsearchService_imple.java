package com.spring.app.hpsearch.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.HospitalDTO;
import com.spring.app.hpsearch.model.HpsearchDAO;
import com.spring.app.hpsearch.model.LocHpsearchDAO;
import com.spring.app.main.model.MainDAO;

@Service
public class LocHpsearchService_imple implements LocHpsearchService {
	
	//의존객체 주입 DI ----- 
	@Autowired
	private LocHpsearchDAO lhsdao;

}
