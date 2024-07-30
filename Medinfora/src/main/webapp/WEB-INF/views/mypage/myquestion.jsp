<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/mypage/myQnA.css">
<script type="text/javascript" src="<%= ctxPath%>/resources/js/mypage/myQnA.js"></script>

<style>
.b_red {
	border: solid 1px red;
}

.b_blue {
	border: solid 1px blue;
}

.b_black {
	border: solid 1px black;
}
</style>

<script type="text/javascript">

function goView(qidx){
	location.href="<%=ctxPath%>/questionView.bibo?qidx="+qidx;
}

</script>




<div class="jh_container">
	<div class="py-4" align="center">
		<c:if test='${sessionScope.loginuser.mIdx == 1}'>
			<h2 class="nanum-eb size-n">나의 질문목록</h2>
		</c:if>
		<c:if test='${sessionScope.loginuser.mIdx == 2}'>
			<h2 class="nanum-eb size-n">나의 답변목록</h2>
		</c:if>
	</div>
	<%-- 검색 구역 --%>
	<div>
		<input type="hidden" name="PageNo"/>
		<div class="p-4 searchBar">
			<span>
				<select class="search_ch sel_0 nanum-b" name="subject">
					<option value='0'>구분</option>
					<option value='1'>건강상담</option>
					<option value='2'>식생활,식습관</option>
					<option value='3'>의약정보</option>
				</select>
			</span>
			
			<span class="">
				<select class="search_ch sel_1 nanum-b" name="type">
					<option value='z'>전체</option>
					<option value='Q.title'>질문제목</option>
					<option value='Q.content'>질문내용</option>
					<option value='A.content'>답변내용</option>
				</select>
			</span>
			
			<span>
				<input class="search_ch sel_2 nanum-b" name="word" type="text" placeholder="검색어를 입력해주세요." autocomplete="none"/>
			</span>
			<span>
				<button class="jh_btn_design search nanum-eb size-s" type="button" onclick="page(1)">검색</button>
			</span>
		      
		</div>
	</div>
	
	<%-- 질문 구역 --%>
	<div>
		<%-- 목차? --%>
		<div class="mt-4 px-3 subject">
			<div class="row text-center py-3 nanum-eb size-s">
				<span class="col-2">구분</span>
				<span class="col-6">질문제목</span>
				<span class="col-2">진행상태</span>
				<span class="col-2">작성일자</span>
			</div>
		</div>
		
		<%-- 리스트 보여지는 곳 --%>
		<div class="mb-5 px-3" id="questionArea"></div>
		
		<%-- 페이지 바 --%>
		<div class="pagebar" style="text-align: center;"></div>
	
	
	</div>

</div>