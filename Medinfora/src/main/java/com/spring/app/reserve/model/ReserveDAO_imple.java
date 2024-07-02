package com.spring.app.reserve.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.main.domain.HospitalDTO;

@Repository
public class ReserveDAO_imple implements ReserveDAO {

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	// === 회원가입된 병원 개수  === //
	@Override
	public int getTotalCnt() {
		int totalCnt = sqlsession.selectOne("mediinfora.getTotalCnt");
		return totalCnt;
	}

	// === 회원가입된 병원 리스트 가져오기 === //
	@Override
	public List<HospitalDTO> mbHospitalList(Map<String, String> paraMap) {
		List<HospitalDTO> mbHospitalList = sqlsession.selectList("mediinfora.mbHospitalList",paraMap);
		return mbHospitalList;
	}

}