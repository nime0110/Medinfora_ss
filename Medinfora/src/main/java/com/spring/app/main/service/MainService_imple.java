package com.spring.app.main.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.common.GoogleMail;
import com.spring.app.common.Myutil;
import com.spring.app.common.Sha256;
import com.spring.app.domain.ClasscodeDTO;
import com.spring.app.domain.HolidayVO;
import com.spring.app.domain.HospitalDTO;
import com.spring.app.domain.KoreaAreaVO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
import com.spring.app.domain.NewsDTO;
import com.spring.app.domain.NoticeDTO;
import com.spring.app.domain.ReserveDTO;
import com.spring.app.main.model.MainDAO;

import oracle.net.aso.h;
import oracle.net.aso.n;

@Service
public class MainService_imple implements MainService {

	@Autowired
	private MainDAO dao;
	
	@Autowired
    private AES256 aES256;
	
	
		
	// 회원가입(중복체크)
	@Override
	public MemberDTO isExistCheck(Map<String, String> paraMap) {
		
		if("email".equalsIgnoreCase(paraMap.get("type"))) {
			try {
				paraMap.put("value",aES256.encrypt(paraMap.get("value")));
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		
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
	
	// 회원가입하기
	@Override
	public int registerEnd(Map<String, String> paraMap) {
		
		String pwd = paraMap.get("pwd");
		String email = paraMap.get("email");
		String mobile = paraMap.get("mobile");
		try {
			paraMap.put("pwd", Sha256.encrypt(pwd));
			paraMap.put("email", aES256.encrypt(email));
			paraMap.put("mobile", aES256.encrypt(mobile));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		int n = dao.registerEnd(paraMap);
		
		if(paraMap.get("midx").equals("2")) {
			Map<String,String> classCodeListParaMap = new HashMap<String, String>();
			
			classCodeListParaMap.put("hpname",paraMap.get("name"));
			classCodeListParaMap.put("hpaddr",paraMap.get("address"));
			
			List<String> classCodeList = dao.getclassCodeList(classCodeListParaMap);
			
			for(String classcode : classCodeList) {
				Map<String,String> inputparaMap = new HashMap<String, String>();
				
				inputparaMap.put("userid",paraMap.get("userid"));
				inputparaMap.put("classcode",classcode);
				inputparaMap.put("hidx",paraMap.get("hidx"));
				
				dao.classcodeMetInput(inputparaMap);
			}
		}
		
		return n;
	}
	
	
	// 로그인 처리
	@Override
	public MemberDTO loginEnd(Map<String, String> paraMap, HttpServletRequest request) {
		
		if(paraMap.get("loginmethod") == "0") {
			paraMap.put("pwd", Sha256.encrypt(paraMap.get("pwd")));
		}
		
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
					
					loginuser.setEmail(aES256.decrypt(loginuser.getEmail()));
					loginuser.setMobile(aES256.decrypt(loginuser.getMobile()));
					
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

	// 인덱스 화면 공지 불러오기
	@Override
	public List<NoticeDTO> getIdxNdtoList() {
		
		List<NoticeDTO> orignDTO = dao.getIdxNdtoList();
		
		for(NoticeDTO ndto : orignDTO) {
			ndto.setContent(Myutil.removeHTMLtag(ndto.getContent()));
			ndto.setWriteday(ndto.getWriteday().substring(0,10));
		}
		
		return orignDTO;
	}

	// 병원 중복가입 체크
	@Override
	public boolean checkhidx(String hidx) {
		return dao.checkhidx(hidx);
	}

	// (검색) 리스트 불러오기
	@Override
	public Map<String,List<Object>> searach(String search) {
		
		Map<String,List<Object>> result = new HashMap<>();
		
		int counthospital,countmediq,countmedia,countnotice,totalcount;
		
		List<HospitalDTO> hdtoList = dao.gethdtoOurlist(search);
		
		
		if(hdtoList.size()!=5) {
			
			int size = hdtoList.size();
			int rno = 1;
			
			dept:
			for(int i=0;i<5-size;i++) {
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("search",search);
				paraMap.put("rno",String.valueOf(rno++));
				
				HospitalDTO hdto = dao.gethdto(paraMap);
				
				try {
					hdto.getHpname();
				}catch (Exception e) {
					break;
				}
				
				for(int j=0;j<5-size;j++) {
					try {
						if(hdto.getHpname().equals(hdtoList.get(j).getHpname()) &&
							hdto.getHpaddr().equals(hdtoList.get(j).getHpaddr())) {
							continue dept;
						}
					}catch (Exception e) {
						break;
					}
				}
				paraMap.put("hpname",hdto.getHpname());
				paraMap.put("hpaddr",hdto.getHpaddr());
				int index = dao.gethidx(paraMap);
				
				hdto.setHidx(index);
				
				hdtoList.add(hdto);
			}
			
		}else {
			for(HospitalDTO hdto : hdtoList) {
				hdtoList.add(hdto);
			}
		}
		
		counthospital = hdtoList.size();
		
		if(counthospital != 0) {
			List<Object> inputer = new ArrayList<Object>();
			for(HospitalDTO hdto : hdtoList) {
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("hpname",hdto.getHpname());
				paraMap.put("hpaddr",hdto.getHpaddr());
				
				HospitalDTO finaldto = dao.searchMedicalEnd(paraMap);
				finaldto.setAgency(hdto.getAgency());
				finaldto.setMember(dao.isMediMember(finaldto.getHidx()));
				
				inputer.add(finaldto);
			}

			result.put("hdtolist",inputer);
		}
	
		List<MediQDTO> mqdtoList = dao.getmqList(search);
		countmediq = mqdtoList.size();
		
		if(countmediq != 0) {
			List<Object> inputer = new ArrayList<Object>();
			for(MediQDTO mqdto : mqdtoList) {
				String content = Myutil.removeHTMLtag(mqdto.getContent());
				mqdto.setContent(content);
				inputer.add(mqdto);
			}
			
			result.put("mqdtolist",inputer);
		}
	
		List<MediQDTO> madtoList = dao.getmaList(search);
		// 주의 : MEDIA지만 형식은 MEDIQ로 받는다
		countmedia = madtoList.size();
		
		if(countmedia != 0) {
			List<Object> inputer = new ArrayList<Object>();
			start:
			for(MediQDTO madto : madtoList) {
				String content = Myutil.removeHTMLtag(madto.getContent());
				madto.setContent(content);
				
				for(int i=0;i<mqdtoList.size();i++) {
					if(mqdtoList.get(i).getTitle().equals(madto.getTitle())) {
						continue start;
					}
					if(i==mqdtoList.size()-1) {
						inputer.add(madto);
					}
				}// 질문 답변 중복 제거
				
			}
			result.put("madtolist",inputer);
		}
	
		List<NoticeDTO> ndtoList = dao.getndtoList(search);
		countnotice = ndtoList.size();
		
		if(countnotice != 0) {
			List<Object> inputer = new ArrayList<Object>();
			for(NoticeDTO ndto : ndtoList) {
				String content = Myutil.removeHTMLtag(ndto.getContent());
				ndto.setContent(content);
				inputer.add(ndto);
			}
			
			result.put("ndtolist",inputer);
		}
		
		totalcount = counthospital + countmediq + countmedia + countnotice;
		
		List<Object> cntInputer = new ArrayList<Object>();
		Map<String,Integer> countmap = new HashMap<String, Integer>();
		countmap.put("totalcount",totalcount);
		cntInputer.add(countmap);
		result.put("countmap",cntInputer);
		
		return result;
	}

	// (검색) 검색 로그 작성하기
	@Override
	public void writeSearchlog(Map<String, String> paraMap) {
		dao.writeSearchlog(paraMap);
	}

	// 인덱스 인기 검색어 불러오기
	@Override
	public List<String> getPopwordList() {
		return dao.getPopwordList();
	}

	// (검색) 총 병원 검색수
	@Override
	public int hcnt(String search) {
		return dao.hcnt(search);
	}

	// (검색) 병원 추가검색
	@Override
	public List<HospitalDTO> getmorehinfo(Map<String, String> paraMap) {
		
		List<HospitalDTO> hdtoList = dao.gethdtoList(paraMap);
		
		for(HospitalDTO hdto : hdtoList) {
			
			paraMap.put("hpname",hdto.getHpname());
			paraMap.put("hpaddr",hdto.getHpaddr());
			int index = dao.gethidx(paraMap);
			
			hdto.setHidx(index);
		}
		
		return hdtoList;
	}

	@Override
	public int newsInputer(NewsDTO newsDTO) {
		
		System.out.print(" "+newsDTO.getTitle());
		
		return dao.newsInputer(newsDTO);
	}

	// 최신 예약 정보(일반)
	@Override
	public ReserveDTO getRdto_p(String userid) {
		ReserveDTO rdto = dao.getRdto_p(userid);
		return rdto;
	}

	// 최신 예약된 병원 이름
	@Override
	public String gethpname(String hidx) {
		String hpname = dao.gethpname(hidx);
		return hpname;
	}
	
	// 최신 예약 정보(의료)
	@Override
	public ReserveDTO getRdto_m(String userid) {
		ReserveDTO rdto = dao.getRdto_m(userid);
		return rdto;
	}

	// 최신 예약된 환자 이름
	@Override
	public String getname(String patientID) {
		String patient = dao.getname(patientID);
		return patient;
	}

	// 최신 예약된 환자의 나이
	@Override
	public String getAge(String patientID) {
		String age = dao.getAge(patientID);
		return age;
	}
	
	
	// 아이디 비밀번호 찾기
	@Override
	public MemberDTO finduserinfo(Map<String, String> paraMap) {

		try {
			paraMap.put("email", aES256.encrypt(paraMap.get("email")));
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		MemberDTO mdto = null;
		mdto = dao.findinfo(paraMap);

		
		return mdto;
	}
	
	// 비밀번호 초기화
	@Override
	public int changepassword(Map<String, String> paraMap) {
		String pwd = paraMap.get("pwd");
		paraMap.put("pwd", Sha256.encrypt(pwd));

		int n = dao.changepassword(paraMap);
		return n;
	}
	
}
