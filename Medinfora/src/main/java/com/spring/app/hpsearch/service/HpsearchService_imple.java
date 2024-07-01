package com.spring.app.hpsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.spring.app.main.model.MainDAO;

public class HpsearchService_imple implements HpsearchService {
	
	//의존객체 주입 DI ----- 
	@Autowired
	private MainDAO mdao;

}
