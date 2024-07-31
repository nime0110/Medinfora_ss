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


div.pagebar{
	display: grid;
    place-items: center;
	margin-top: 10px;
}

div#oneqdto:hover{
	background-color: var(--primary-background-color);
	color: var(--text-white-color);
	border: none;
	cursor: pointer;
}

</style>

<div class="container" style="margin-top: 100px;">

	<div class="py-4" align="center">
		<h2 class="nanum-eb size-n">의료 뉴스</h2>
	</div>
	<div>
		<!-- 목차  -->
		<div class="mt-4 px-3 subject">
			<div class="row text-center py-3 nanum-eb size-s">
				<span class="col-5">제목</span>
				<span class="col-2">작성일자</span>
				<span class="col-1">조회수</span>
			</div>
		</div>
		
		<!-- 여기에 리스트 띄우면 됨 -->
		<div class="mb-5 px-3" id="questionArea">
				<div class="row text-center py-3 nanum-n size-s b_border" id="oneqdto">
					<span class="col-5" align="left">
						제목&nbsp;
					</span>
					<span class="col-2" align="left">
						작성일자&nbsp;
					</span>
					<span class="col-1" align="left">
						조회수&nbsp;
					</span>
				</div>
		</div>
	
	</div>
	
	<%-- 페이지 바 --%>
	<div class="pagebar" style="text-align: center;"></div>
	
	<div class="py-5 text-center">
		<button class="write nanum-eb size-s" type="button" onclick="gowrite()">등록</button>
	</div>
	
</div>