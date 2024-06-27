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
</head>
<style>
:root {
    --primary-background-color : #0066cc; /* 주 메인 색상 - 넓은 색상 부분에 작업해주시면 됩니다. */
    --sub-background-color : #e6f7ff; /* 서브 색상 - 옅은 색상 부분에 적용(옅은 하늘색) */

     /* div background 색상 등 object 컬럼 색상입니다 */
    --object-lightpurple-color : #e5e2ff;
    --object-lightyello-color : #fff6d8;
    --object-lightgreen-color : #DFF9F2;
    --object-clearblue-color : #f2f4f8;
    --object-skyblue-color : #d8eaff;

    /* object 컬럼 색상별 text 진한 컬러 - 오브젝트색상 계열의 진한 fontcolor 로 매칭시켜서 사용하시면됨 */
    --object-lightgreen-font-color: #23C197;
    --object-lightyello-font-color: #f46d19;
    --object-lightpurple-font-color: #2614F3;

    /* 포인트 컬러 */
    
    /* 기본 텍스트 컬러 */
    --text-black-color: #0e0e0e; 
    --text-white-color: #fff; 
    --text-gray-color: #222; 

}

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
</style>

<body>
	<tiles:insertAttribute name="header" />
	<div style="margin-top: 75px;"></div>
	<tiles:insertAttribute name="content" />
	<tiles:insertAttribute name="footer" />
</body>
</html>