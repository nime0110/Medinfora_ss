<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String ctxPath = request.getContextPath(); %>

<meta charset="utf-8" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/echarts/5.5.0/echarts.min.js"></script>
<script src="<%=ctxPath%>/resources/js/mypage/searchloglist.js"></script>
<link rel="stylesheet" href="<%=ctxPath%>/resources/css/mypage/searchloglist.css">

<div class="tools">
	<div class="selboxSection">
		<div class="selTitle">통계종류
		</div>
		<select name="opt" class="selectbox">
			<option value="t0">통합검색이용량</option>
			<option value="t1">인기검색어</option>
		</select>
	</div>
	<div class="selboxSection">
		<div class="selTitle">사용자
		</div>
		<select name="opu" class="selectbox">
			<option value="u0">모든사용자</option>
			<option value="u1">회원만</option>
		</select>
	</div>
	<div class="selboxSection">
		<div class="selTitle">기간
		</div>
		<select name="opr" class="selectbox">
			<option value="r0">1주일</option>
			<option value="r1">1달</option>
		</select>
	</div>
</div>

<div class="statusSection">
	<div id="chartin"></div>
</div>