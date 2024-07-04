package com.spring.app.aop;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.spring.app.domain.MemberDTO;

@Aspect
@Component
public class CommonAop {

	//=== BEFORE ☆ ADVICE === //
	@Before("isLogin()")
	public void isLogin(JoinPoint joinpoint) {
		
		HttpServletRequest request = (HttpServletRequest)joinpoint.getArgs()[1];
		HttpServletResponse response = (HttpServletResponse)joinpoint.getArgs()[2];
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginuser")==null) {
			
			String message = "로그인 후 이용가능합니다";
	 		String loc = request.getContextPath()+"/index.bibo";
	 		
	 		request.setAttribute("message", message);
	 		request.setAttribute("loc", loc);
	 		
	 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	 		
	 		try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
	 		
		}
		
	}// end of public void isLogin(JoinPoint joinpoint)
	
	@Before("isAdmin()")
	public void isAdmin(JoinPoint joinpoint) {
		
		HttpServletRequest request = (HttpServletRequest)joinpoint.getArgs()[1];
		HttpServletResponse response = (HttpServletResponse)joinpoint.getArgs()[2];
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginuser")==null) {
			
			String message = "로그인 후 이용가능합니다";
	 		String loc = request.getContextPath()+"/index.bibo";
	 		
	 		request.setAttribute("message", message);
	 		request.setAttribute("loc", loc);
	 		
	 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	 		
	 		try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
	 		
		}else {
			
			MemberDTO loginuser = (MemberDTO) session.getAttribute("loginuser");
			
			if(loginuser.getmIdx()!=0) {
				
				String message = "관리자외 접근할수 없습니다.";
		 		String loc = request.getContextPath()+"/index.bibo";
		 		
		 		request.setAttribute("message", message);
		 		request.setAttribute("loc", loc);
		 		
		 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
		 		
		 		try {
					dispatcher.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	} // end of public void isAdmin(JoinPoint joinpoint)
	
	//=== AFTER ☆ ADVICE === //

	//=== POINT ☆  CUT === //
	@Pointcut("execution(public * com.spring.app..*Controller.isLogin_*(..) )")
	public void isLogin() {}

	@Pointcut("execution(public * com.spring.app..*Controller.isAdmin_*(..) )")
	public void isAdmin() {}
	
}
