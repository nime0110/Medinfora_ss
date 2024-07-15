<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<c:if test='${requestScope.nosearch == 1}'>
	<h1>검색어를 입력해주세요.</h1>
</c:if>

<c:if test='${requestScope.nosearch == 0}'>
	<h1>${requestScope.search}</h1>
</c:if>