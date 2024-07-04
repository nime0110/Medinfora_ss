<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- Jquery --%>
<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>

<%-- JS --%>
<script type="text/javascript" src="<%= ctxPath%>/resources/js/index.js"></script>

<%-- BootStrap --%>
<script src="<%= ctxPath%>/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%= ctxPath%>/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<%-- stylesheet --%>
<style type="text/css">

.searchType{
	width: 100px;

}

</style>


<%-- Google Font --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link rel="stylesheet" href="<%= ctxPath%>/resources/css/fontcss.css">

<%-- Font Awesome 6 Icons --%>
<script src="https://kit.fontawesome.com/f1e9f47e08.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

</head>
<body>
<div class="container" style="border: solid 1px red;">

<form class="mt-3 d-flex" name="searchFrm" style="border: solid 1px blue;">
	<select class="searchType" name="searchType">
		<option value="subject">글제목</option>
		<option value="content">글내용</option>
		<option value="subject_content">글제목+글내용</option>
		<option value="name">글쓴이</option>
	</select>
	<input class="form-control" type="text" name="searchWord" size="70" autocomplete="off" /> 
	<input type="text" style="display: none;"/> <%-- form 태그내에 input 태그가 오로지 1개 뿐일경우에는 엔터를 했을 경우 검색이 되어지므로 이것을 방지하고자 만든것이다. --%> 
	<button type="button" class="btn btn-secondary btn-sm" onclick="goSearch()">검색</button> 
</form>
<div class="info_body" style="border: solid 1px black;">
	<p class="py-3 px-2"><span class="nanum-eb size-n">tip</span><br><span class="px-2 py-3">병원이름 검색 시 주소가 맞는지 확인하시어 선택바랍니다.</span></p>
</div>
	
<%-- === #114. 검색어 입력시 자동글 완성하기 1 === 
<div id="displayList" style="border:solid 1px gray; border-top:0px; height:100px; margin-left:13.2%; margin-top:-1px; margin-bottom:30px; overflow:auto;"></div>
--%>
</div>

















</body>
</html>