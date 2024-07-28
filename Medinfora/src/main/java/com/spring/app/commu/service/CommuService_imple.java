package com.spring.app.commu.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.commu.model.CommuDAO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.commu.CommuBoardDTO;
import com.spring.app.domain.commu.CommuFilesDTO;
import com.spring.app.hpsearch.model.HpsearchDAO;
import com.spring.app.main.model.MainDAO;

@Service
public class CommuService_imple implements CommuService {
	
	//의존객체 주입 DI ----- 
	@Autowired
	private CommuDAO cmdao;

	@Override
	public String getSeqCommu() {
		return cmdao.getSeqCommu();
	}

	@Override
	public int add(CommuBoardDTO cbdto) {
		return cmdao.add(cbdto);
	}

	@Override
	public void add_File(CommuFilesDTO cfdto) {
		cmdao.add_File(cfdto);
	}

	@Override
	public List<CommuBoardDTO> getCommuBoardList(Map<String, String> paraMap) {
		return cmdao.getCommuBoardList(paraMap);
	}

	@Override
	public int getCBListTotalCount(Map<String, String> paraMap) {
		return cmdao.getCBListTotalCount(paraMap);
	}

	@Override
	public List<String> getfileSeqList() {
		return cmdao.getfileSeqList();
	}


}
