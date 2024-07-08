package com.spring.app.main.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.main.model.MainDAO;

@Service
public class MainService_imple implements MainService {

	@Autowired
	private MainDAO dao;
	
	@Autowired
    private AES256 aES256;

	@Override
	public String test() {
		return dao.daotest();
	}
	
	// 회원가입(중복체크)
	@Override
	public MemberDTO isExistCheck(Map<String, String> paraMap) {
		MemberDTO isExist = dao.isExistCheck(paraMap);
		return isExist;
	}
	
	// 회원가입(병원찾기 자동검색)
	@Override
	public List<String> autoWord(Map<String, String> paraMap) {
		List<String> autoWordList = dao.autoWord(paraMap);
		return autoWordList;
	}
	
	// 회원가입(병원찾기 병원리스트(전체개수))
	@Override
	public int totalhospital(Map<String, String> paraMap) {
		int totalCount = dao.totalhospital(paraMap);
		return totalCount;
	}
	
	// 회원가입(병원찾기 병원리스트(페이징))
	@Override
	public List<HospitalDTO> hpSearch(Map<String, String> paraMap) {
		List<HospitalDTO> hpList = dao.hpSearch(paraMap);
		return hpList;
	}
	
	// 회원가입(병원찾기 병원정보 입력)
	@Override
	public HospitalDTO searchMedicalEnd(Map<String, String> paraMap) {
		HospitalDTO hpdto = dao.searchMedicalEnd(paraMap);
		return hpdto;
	}
	
	
	// 로그인 처리
	@Override
	public MemberDTO loginEnd(Map<String, String> paraMap, HttpServletRequest request) {
		
		MemberDTO loginuser = dao.getLoginuser(paraMap);
		
		// == 로그인 경우의수  == //
		/*
			-- 기본 전제 휴먼, 정지 또는 탈퇴 회원이 아님  회원코드 0~2 까지만 불러옴 --
			1. 로그인12x 비번3 x
			2. 로그인12x 비번3 o
			3. 로그인12o 휴먼 x
		*/
		try {
			// 관리자 및  회원만(활동중으로 표시는 되어있지만 휴먼처리 안되있는 유저 포함)
			if(loginuser != null && loginuser.getmIdx() <= 2) {
				// 로그인한지 1년 이상되었는데 휴먼처리 안됨
				if(loginuser.getLastlogingap() >= 12 && (loginuser.getmIdx() == 1 || loginuser.getmIdx() == 2)) {
					String idx = "0";
					if(loginuser.getmIdx() == 1) {	// 일반
						idx = "3";
						loginuser.setmIdx(3);
						dao.updatemIdx(paraMap.get("userid"), idx);
					}
					else if(loginuser.getmIdx() == 2) {	// 의료
						idx = "4";
						loginuser.setmIdx(4);
						dao.updatemIdx(paraMap.get("userid"), idx);
					}
				}
				else { // 정상 활동중인 회원
					// 비밀번호 변경 3개월 지남  >> 비밀번호 알림은 프론트에서 해줌
					dao.insert_log(paraMap);
					/*
					나중에 회원가입 후 다시 품 ------------수정사항
					// 로그인 정상 유저
					if(loginuser.getmIdx()==0 || loginuser.getmIdx()==1 || loginuser.getmIdx()==2) {
						String email = aES256.decrypt(loginuser.getEmail());
						String mobile = aES256.decrypt(loginuser.getMobile());
						
						loginuser.setEmail(email);
						loginuser.setMobile(mobile);
					}
					*/
					HttpSession sesstion =  request.getSession();
					sesstion.setAttribute("loginuser", loginuser);
				}
			}
			// 휴먼 회원 및 정지 회원은 프론트에서 처리할 거임
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginuser;
	}

	// 병원API 입력용
	@Override
	public int hpApiInputer(HospitalDTO hospitalDTO) {

		System.out.print(" "+hospitalDTO.getHpname());

		return dao.hpApiInputer(hospitalDTO);
	}

	
	// 로그아웃 처리
	@Override
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request, String url) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		String renameurl = url.substring(1);
		renameurl = url.substring(renameurl.indexOf("/")+1);
		
		mav.setViewName("redirect:"+renameurl);
		
		return mav;
	}

	// 대한민국 행정구역정보 입력용
	@Override
	public int areaInputer(KoreaAreaVO koreaAreaVO) {
		
		System.out.print(" "+koreaAreaVO.getLocal());
		
		return dao.areaInputer(koreaAreaVO);
	}


	// 행정구역 리스트 추출
	@Override
	public List<String> getcityinfo() {
		return dao.getcityinfo();
	}


	// 시/군/구 리스트 추출
	@Override
	public List<String> getlocalinfo(String city) {
		return dao.getlocalinfo(city);
	}
	
	// 읍/면/동 리스트 추출
	@Override
	public List<String> getcountryinfo(KoreaAreaVO inputareavo) {
		return dao.getcountryinfo(inputareavo);
	}

	// 병원 진료과 리스트 추출
	@Override
	public List<ClasscodeDTO> getclasscode() {
		return dao.getclasscode();
	}

	// 공휴일 입력용
	@Override
	public int holidayInputer(HolidayVO holidayVO) {
		System.out.print("확인용 summary : "+holidayVO.getSummary());
		System.out.print(" | 확인용 holiday_date : "+holidayVO.getHoliday_date());
		return dao.holidayInputer(holidayVO);
	}

	

	

	

	
}
