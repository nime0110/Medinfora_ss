<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctxPath = request.getContextPath();
%>



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

.listGuide {
	display: table;
}

.b_border {
	border-bottom: solid 1px rgb(163, 163, 163);
}

.subject {
	border-bottom: none;
}

.search_ch {
	height: 40px;
	border: none;
	border-radius: 0.5rem;
}

.sel_0 {
	width: 200px;
	margin-right: 2rem;
	padding: 0 40px 0 20px;
}

.sel_1 {
	width: 150px;
	margin-right: 2rem;
	padding: 0 40px 0 20px;
}

.sel_2 {
	width: 25%;
	padding-left: 20px;
}

button.search {
	margin: 0;
	width: 100px;
}

input:focus, select {
	outline: none;
}

.subject {
	border-top: 3px solid black;
	border-bottom: 1px solid black;
}

button.write {
	width: 120px;
	height: 50px;
	background-color: white;
	border: solid 2px var(--text-black-color);
	font-size: 1rem;
	border-radius: 0.5rem;
	
}

button.write:hover{
	background-color: var(--primary-background-color);
	color: var(--text-white-color);
	border: none;
	
}

</style>

<script type="text/javascript">

$(document).ready(function(){
	
	// 페이지바 출력
	const blockSize = ${requestScope.blockSize};
	let loop = ${requestScope.loop};
	let pageNo = ${requestScope.pageNo};
	const totalPage = ${requestScope.totalPage};
	
	let pageBar = `<ul class='pagination hj_pagebar nanum-n size-s'>`;
	
	if(pageNo != 1) {
		pageBar += "<li class='page-item'>" 
				+ " 	<a class='page-link' onclick='goSearch("+(pageNo-1)+")>" 
				+ "	    	<span aria-hidden='true'>&laquo;</span>" 
				+ "	    </a>" 
				+ "</li>";
	}
	
	while(!(loop>blockSize || pageNo > totalPage)) {
		if(pageNo == currentPageNo) {
			pageBar += "<li class='page-item'>"
					+ "		<a class='page-link nowPage'>"+pageNo+"</a>" 
					+ "</li>";
		}
		else{
			pageBar += "<li class='page-item'>"
					+ "		<a class='page-link' onclick='goSearch("+pageNo+")'>" +pageNo+"</a>" 
					+ "</li>";
		}
		loop++;
		pageNo++;
	}
	
	if(pageNo <= totalPage) {
		pageBar += "<li class='page-item'>"
				+ "		<a class='page-link' onclick='goSearch("+pageNo+")'>"
				+ "	    	<span aria-hidden='true'>&raquo;</span>"
				+ "	    </a>"
				+ "</li>";
	}
	
	pageBar += "</ul>";
	
	$("div.pagebar").html(pageBar);
	

});

function gowrite(){
	location.href="<%=ctxPath%>/questionWrite.bibo";
}


</script>


<div class="container" style="margin-top: 100px;">

	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">묻고 답하기</h2>
	</div>
	<form>
		<!-- <legend>ㅎㅇ</legend> -->
		<fieldset>
			<div class="p-4" align="center" style="background-color: var(--object-skyblue-color); ">
				<span>
					<select class="search_ch sel_0 nanum-b">
						<option value='0'>구분</option>
						<option value='1'>건강상담</option>
						<option value='2'>식생활,식습관</option>
						<option value='3'>의약정보</option>
					</select>
				</span>
				
				<span class="">
					<select class="search_ch sel_1 nanum-b">
						<option value='z'>전체</option>
						<option value='Q.title'>질문제목</option>
						<option value='Q.content'>질문내용</option>
						<option value='A.content'>답변내용</option>
					</select>
				</span>
				
				<span>
					<input class="search_ch sel_2 nanum-b" name="search" type="text" placeholder="검색어를 입력해주세요." />
				</span>
				<span>
					<button class="jh_btn_design search nanum-eb size-s" type="button">검색</button>
				</span>
			      
			      
			</div>
		
		</fieldset>
	
	</form>
	
	<!-- 질문 구역 곳 -->
	<div>
		<!-- 목차 ? -->
		<div class="mt-4 px-3 subject">
			<div class="row text-center py-3 nanum-eb size-s">
				<span class="col-2">구분</span>
				<span class="col-5">질문제목</span>
				<span class="col-2">진행상태</span>
				<span class="col-2">작성일자</span>
				<span class="col-1">조회수</span>
			</div>
		</div>
		
		<!-- 여기에 리스트 띄우면 됨 -->
		<div class="mb-5 px-3">
			<c:forEach var="qdto" items="${requestScope.qdtoMap.qList}"  varStatus="status">
				<div class="row text-center py-3 nanum-n size-s b_border">
					<input type="hidden" value="${qdto.qidx}" name="no${status.index}"/>
					<span class="col-2">
						<c:if test="${qdto.subject eq '1'}">건강상담</c:if>
						<c:if test="${qdto.subject eq '2'}">식생활,식습관</c:if>
						<c:if test="${qdto.subject eq '3'}">의약정보</c:if>
					</span>
					<span class="col-5" align="left">
						${qdto.title}&nbsp;&nbsp;<c:if test="${qdto.newwrite eq '0'}"><i class="fa-solid fa-face-angry" style="color: #ff8800;"></i></c:if>
					
					</span>
					<span class="col-2">
						<c:if test="${qdto.acount eq '0'}">
							<span class="p-1 nanum-b" style="background-color: #f1bd81; border-radius: 10%; color: white;">
								답변&nbsp;중
							</span>
						</c:if>
						<c:if test="${qdto.acount ne '0'}">
							<span class="p-1 nanum-b" style="background-color: blue; border-radius: 10%; color: white;">
								답변&nbsp;완료
							</span>
						</c:if>
					</span>
					<span class="col-2">${qdto.writeday}</span>
					<span class="col-1">${qdto.viewcount}</span>
			
				</div>
			</c:forEach>
			
		</div>
	
	</div>
	
	<%-- 페이지 바 --%>
	<div class="pagebar mb-5 px-3"></div>
	
	<div class="py-5 text-center">
		<button class="write nanum-eb size-s" type="button" onclick="gowrite()">등록</button>
	</div>
</div>