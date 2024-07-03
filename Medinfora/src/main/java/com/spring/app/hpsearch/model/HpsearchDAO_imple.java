package com.spring.app.hpsearch.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.HospitalDTO;

@Repository
public class HpsearchDAO_imple implements  HpsearchDAO {
	
	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override
	public List<HospitalDTO> getHospitalList(Map<String, String> paraMap) {
		//이때 없는경우 데이터바인딩이 안되서 CommentVO 필드가 null이된다. 
		return sqlsession.selectList("medinfora.getHospitalList", paraMap);
	}

	@Override
	public int getHpListTotalCount(Map<String, String> paraMap) {
		return sqlsession.selectOne("medinfora.getHpListTotalCount", paraMap);
	}

}
