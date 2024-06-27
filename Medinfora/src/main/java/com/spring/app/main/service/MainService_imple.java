package com.spring.app.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.common.AES256;
import com.spring.app.main.domain.HospitalDTO;
import com.spring.app.main.domain.MemberDTO;
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
	
	
	// 로그인 처리
	@Override
	public ModelAndView loginEnd(Map<String, String> paraMap, ModelAndView mav, HttpServletRequest request) {
		
		MemberDTO loginuser = dao.getLoginuser(paraMap);
		
		// == 로그인 경우의수  == //
		/*
			-- 기본 전제 휴먼, 정지 또는 탈퇴 회원이 아님  회원코드 0~2 까지만 불러옴 --
			1. 로그인12x 비번3 x
			2. 로그인12x 비번3 o
			3. 로그인12o 휴먼 x
			
			
		*/
		try {
			
			if(loginuser != null) {
				
				// 비밀번호 변경 3개월 지남
				if(loginuser.getPwdchangegap() >=3 ) {
					loginuser.setRequirePwdChange(true);
				}
				
				// 로그인한지 1년 이상되었는데 휴먼처리 안됨
				if(loginuser.getLastlogingap() >= 12) {
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
				
				// 로그인 정상 유저
				if(loginuser.getmIdx()==0 || loginuser.getmIdx()==1 || loginuser.getmIdx()==2) {
					String email = aES256.decrypt(loginuser.getEmail());
					String mobile = aES256.decrypt(loginuser.getMobile());
					
					loginuser.setEmail(email);
					loginuser.setMobile(mobile);
				}
				
				// 로그인유저  ip 저장
				dao.insert_log(paraMap);
	
			}
			
			
			// 로그인 실패(휴면, 정지 포함)
			if(loginuser == null) {
				
				String message = "아이디 또는 비밀번호가 틀립니다.";
				
				
				
				
			}
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		// 로그인한지 1년 이상 됨
		
		
		
		
		return mav;
		
	}


	@Override
	public int hpApiInputer(HospitalDTO hospitalDTO) {
		
		System.out.println(hospitalDTO.getHpname());
		
		return 1;
	}
	
	
	

	
	
}
