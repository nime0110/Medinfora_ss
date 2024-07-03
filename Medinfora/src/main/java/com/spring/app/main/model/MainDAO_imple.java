package com.spring.app.main.model;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;
=======
import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
>>>>>>> parent of 5e6244a ([feat.sh] notice update)

@Repository
public class MainDAO_imple implements MainDAO {

	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;

	@Override // 테스트용
	public String daotest() {
		return "pass";
	}

	// 회원가입(중복체크)
	@Override
	public MemberDTO isExistCheck(Map<String, String> paraMap) {
		MemberDTO isExist = sqlsession.selectOne("medinfora.isExistCheck", paraMap);
		return isExist;
	}

	// 로그인 유저 정보
	@Override
	public MemberDTO getLoginuser(Map<String, String> paraMap) {
		MemberDTO loginuser = sqlsession.selectOne("medinfora.getLoginuser", paraMap);
		return loginuser;
	}

	// 회원코드 변경 (휴먼처리)
	@Override
	public void updatemIdx(String userid, String idx) {

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("medinfora.userid", userid);
		paraMap.put("medinfora.idx", idx);

		sqlsession.update("medinfora.updatemIdx", paraMap);
	}

	// 로그인 유저 ip 기록
	@Override
	public void insert_log(Map<String, String> paraMap) {
		sqlsession.insert("medinfora.insert_log", paraMap);

	}

	// 병원정보 API 입력용 메소드
	@Override
	public int hpApiInputer(HospitalDTO hospitalDTO) {
		return sqlsession.insert("medinfora.hpApiInputer", hospitalDTO);
	}
<<<<<<< HEAD

	// 대한민국 행정구역정보 입력용
	@Override
	public int areaInputer(KoreaAreaVO koreaAreaVO) {
		return sqlsession.insert("medinfora.areaInputer", koreaAreaVO);
	}

	// 행정구역 리스트 추출
	@Override
	public List<String> getcityinfo() {

		List<String> getcityinfo = sqlsession.selectList("medinfora.getcityinfo");

		return getcityinfo;
	}

	// 시/군/구 리스트 추출
	@Override
	public List<String> getlocalinfo(String city) {

		return sqlsession.selectList("medinfora.getlocalinfo", city);
	}

	// 공지사항
	@Override
	public int noticeWrite(NoticeDTO noticedto) {
		int n = sqlsession.insert("mediinfora.noticeWrite", noticedto);
		return n;
	}

	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int totalCount = sqlsession.selectOne("mediinfora.getTotalCount", paraMap);
		return totalCount;
	}

	// 글목록 가져오기(페이징 처리 했으며, 검색어가 있는 것 또는 검색어 없는것 모두 포함 한 것
	@Override
	public List<NoticeDTO> noticeListSearch_withPaging(Map<String, String> paraMap) {
		List<NoticeDTO> noticeList = sqlsession.selectList("mediinfora.noticeListSearch_withPaging", paraMap);
		return noticeList;
	}

	// 읍/면/동 리스트 추출
	@Override
	public List<String> getcountryinfo(KoreaAreaVO inputareavo) {
		return sqlsession.selectList("medinfora.getcountryinfo", inputareavo);
	}

	// 병원 진료과 리스트 추출
	@Override
	public List<ClasscodeDTO> getclasscode() {
		return sqlsession.selectList("medinfora.getclasscode");
	}
=======
	
	
	
>>>>>>> parent of 5e6244a ([feat.sh] notice update)

}
