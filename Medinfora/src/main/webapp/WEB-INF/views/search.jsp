<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/search.css">

<div id="searchheader">
	<div id="searchdiv">
		<input id="searchinput" type="text" name="search" value="${requestScope.search}"/>
		<button id="searchbtn" type="button" name="btn_search"><i class="DH-section-searchBtni fa-solid fa-magnifying-glass"></i></button>
	</div>
</div>

<div id="searchcontainer">
	<div id="searchsidebar">
		<div class="sidebaroption selectoption">
			<span>통합검색</span>
		</div>
		<div class="sidebaroption">
			<span>병원검색</span>
		</div>
		<div class="sidebaroption">
			<span>공지검색</span>
		</div>
		<div class="sidebaroption">
			<span>질문검색</span>
		</div>
	</div>
	<div id="searchcontents">
		<c:if test='${requestScope.nosearch == 1}'>
			<div class="contenttitle">검색어를 입력해주세요.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 2}'>
			<div class="contenttitle">${requestScope.search}에 대한 검색내용은 존재하지 않습니다.</div>
		</c:if>
		
		<c:if test='${requestScope.nosearch == 0}'>
		
			<div class="contenttitle">"${requestScope.search}" 의 검색결과</div>
			
			<c:forEach var="hdto" items="${requestScope.searchlist.hdtolist}">
				<h4>${hdto.hpname}</h4>
			</c:forEach>
			
		</c:if>
	</div>
</div>

