package com.spring.app.notice.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.NoticeDTO;

@Repository  
public class NoticeDAO_imple implements NoticeDAO {

	
	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;
	
	@Override
	public int noticeWrite(NoticeDTO noticedto) {
		
		return 0;
	}

	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int totalCount = sqlsession.selectOne("medinfora.getTotalCount", paraMap);
		return totalCount;
	}

	@Override
	public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
