<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% String ctxPath = request.getContextPath(); %>

<link rel="stylesheet" href="<%=ctxPath%>/">

<h1 class="infotitle">내 정보수정</h1>
<div class="infoitem">

	<div class="inneritem">
		<div class="eachitem">이름 : ${sessionScope.loginuser.name}</div>
		<div class="eachitem">아이디 : ${sessionScope.loginuser.userid}</div>
		<div class="eachitem">회원등급 : 
			<c:if test="${sessionScope.loginuser.mIdx==1}">일반회원</c:if>
			<c:if test="${sessionScope.loginuser.mIdx==2}">의료인회원</c:if>
			<c:if test="${sessionScope.loginuser.mIdx==0}">관리자</c:if>
		</div>
		만들예정임..
	</div>

</div>