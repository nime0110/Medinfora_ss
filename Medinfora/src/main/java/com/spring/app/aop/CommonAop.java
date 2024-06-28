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
	 		
	 		System.out.println(loc);
	 		
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
	
	//=== AFTER ☆ ADVICE === //

	//=== POINT ☆  CUT === //
	@Pointcut("execution(public * com.spring.app..*Controller.isLogin_*(..) )")
	public void isLogin() {}
	
}
