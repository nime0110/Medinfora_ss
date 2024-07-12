<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 

<%String ctxPath = request.getContextPath();%>

<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>MEDINFORA</title>
	
	<%-- Jquery --%>
	<script src="<%= ctxPath%>/resources/node_modules/jquery/dist/jquery.min.js"></script>
	
	<%-- JS --%>
	<script type="text/javascript" src="<%= ctxPath%>/resources/js/index.js"></script>
	
	<%-- BootStrap --%>
	<script src="<%= ctxPath%>/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="<%= ctxPath%>/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	
	
	<%-- Google Font --%>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="<%= ctxPath%>/resources/css/fontcss.css">
	
	<%-- Font Awesome 6 Icons --%>
	<script src="https://kit.fontawesome.com/f1e9f47e08.js" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
	
	
	<%-- 스마트 에디터 관련 --%>
	<script type="text/javascript" src="<%= ctxPath %>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
	

</head>
<style>

	/* 스타일 clear 공통 코드 start */

	body {
	    font-family: 'Nanum Gothic', sans-serif;
	    margin: 0;
	    padding: 0;
	}
	
	li {
	    list-style: none;
	}
	
	a {
	    text-decoration: none;
	    color: black;
	}
	
	#wrap_container{
		height: auto;
		min-height: 100%;
		padding-bottom: 120px;
	}
	
</style>

<body>
	
	<tiles:insertAttribute name="header" />
	<div id="wrap_container">
		<div style="margin-top: 75px;"></div>
		<tiles:insertAttribute name="content" />
	</div>
	<tiles:insertAttribute name="footer" />
</body>
</html>