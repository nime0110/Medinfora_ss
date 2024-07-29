<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String ctxPath = request.getContextPath(); %>

<div>
	<div>회원님의 검색내역</div>
	<c:forEach var="searchlog" items="${requestScope.searchlogList}">
		<div>${searchlog.searchword} / ${searchlog.registerday}</div>
	</c:forEach>
</div>